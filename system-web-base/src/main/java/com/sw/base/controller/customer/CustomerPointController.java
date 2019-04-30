package com.sw.base.controller.customer;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sw.common.controller.BaseController;
import com.sw.base.mapper.customer.CustomerPointDetailMapper;
import com.sw.base.service.impl.customer.CustomerPointDetailServiceImpl;
import com.sw.base.service.impl.customer.CustomerPointServiceImpl;
import com.sw.base.service.impl.customer.CustomerServiceImpl;
import com.sw.common.entity.customer.Customer;
import com.sw.common.entity.customer.CustomerPoint;
import com.sw.common.entity.customer.CustomerPointDetail;
import com.sw.cache.util.DataResponse;
import com.sw.common.util.MapUtil;
import com.sw.common.util.StringUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yu.leilei
 * @since 2019-01-09
 */
@Controller
@RequestMapping("/system-web/customerPoint")
@Api(value = "用户积分相关接口")
public class CustomerPointController extends BaseController<CustomerPointServiceImpl, CustomerPoint> {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerPointController.class);

    @Autowired
    CustomerPointServiceImpl customerPointService;

    @Autowired
    CustomerPointDetailServiceImpl customerPointDetailService;

    @Autowired
    CustomerPointDetailMapper customerPointDetailMapper;

    @Autowired
    CustomerServiceImpl customerService;

    @Override
    @ResponseBody
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public DataResponse page(@RequestParam  Map<String, Object> params) {
        String customerAccount = MapUtil.getMapValue(params, "customerAccount");
        if(StringUtil.isNotEmpty(customerAccount)){
            EntityWrapper<Customer> wrapper = new EntityWrapper<>();
            wrapper.eq("CUSTOMER_ACCOUNT", customerAccount);
            Customer customer = customerService.selectOne(wrapper);
            if(customer != null){
                params.put("eq_fk_customer_id", customer.getPkCustomerId());
            }else{
                params.put("eq_fk_customer_id", "undefined");
            }
        }

        DataResponse response = super.page(params);

        List<CustomerPoint> list = (List<CustomerPoint>) response.get("list");
        if(!CollectionUtils.isEmpty(list)){
            for(CustomerPoint customerPoint:list){
                Customer customer = customerService.selectById(customerPoint.getFkCustomerId());
                if(customer != null){
                    customerPoint.setCustomerName(customer.getCustomerName());
                    customerPoint.setCustomerAccount(customer.getCustomerAccount());
                }
            }
        }

        response.put("list", list);

        return response;
    }

    /**
     * 获取积分
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPoint", method = RequestMethod.POST)
    public DataResponse getPoint(@RequestBody Map<String, Object> param){
        LOG.info("==============开始调用getPoint================");

        Map<String, Object> result = new HashMap<>();

        String customerId = MapUtil.getMapValue(param, "customerId");

        EntityWrapper<CustomerPoint> wrapper = new EntityWrapper<>();
        wrapper.eq("FK_CUSTOMER_ID", customerId);

        CustomerPoint customerPoint = customerPointService.selectOne(wrapper);

        result.put("customerPoint", customerPoint);

        return DataResponse.success(result);
    }

    /**
     * 获取积分详情
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPointDetail", method = RequestMethod.POST)
    public DataResponse getPointDetail(@RequestBody Map<String, Object> param){
        LOG.info("==============开始调用 getPointDetail ================");

        Map<String, Object> result = new HashMap<>();

        String customerId = MapUtil.getMapValue(param, "customerId");
        String action = MapUtil.getMapValue(param, "action");
        String pageStr = MapUtil.getMapValue(param, "page");

        Integer page = Integer.parseInt(pageStr);

        EntityWrapper<CustomerPointDetail> wrapper = new EntityWrapper<>();
        wrapper.eq("FK_CUSTOMER_ID", customerId);
        wrapper.eq("TYPE", action);
        wrapper.eq("IS_DELETE", 0);
        Page<CustomerPointDetail> pageList = new Page<>(page, 10);
        List<CustomerPointDetail> list = customerPointDetailMapper.selectPage(pageList, wrapper);

        Integer isMore = list.size();

        if(isMore < 10){
            result.put("is_more", 0);
        }else {
            result.put("is_more", 1);
        }
        result.put("current_page", page);

        result.put("list", list);

        return DataResponse.success(result);
    }

	
}
