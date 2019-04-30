package com.sw.base.controller.customer;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sw.base.mapper.customer.CustomerBalanceDetailMapper;
import com.sw.cache.util.DataResponse;
import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.customer.CustomerBalanceDetailServiceImpl;
import com.sw.common.entity.customer.CustomerBalanceDetail;
import com.sw.common.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system-web/customerBalanceDetail")
public class CustomerBalanceDetailController extends BaseController<CustomerBalanceDetailServiceImpl,CustomerBalanceDetail> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerBalanceDetailController.class);

    @Autowired
    CustomerBalanceDetailMapper customerBalanceDetailMapper;

    /**
     * 获取积分详情
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getBalanceDetail", method = RequestMethod.POST)
    public DataResponse getDetail(@RequestBody Map<String, Object> param){
        LOGGER.info("==============开始调用 getBalanceDetail ================");

        Map<String, Object> result = new HashMap<>();

        String customerId = MapUtil.getMapValue(param, "customerId");
        String action = MapUtil.getMapValue(param, "action");
        String pageStr = MapUtil.getMapValue(param, "page");

        Integer page = Integer.parseInt(pageStr);

        EntityWrapper<CustomerBalanceDetail> wrapper = new EntityWrapper<>();
        wrapper.eq("FK_CUSTOMER_ID", customerId);
        if(!action.equals("SW0500")){
            wrapper.eq("TYPE", action);
        }
        wrapper.eq("IS_DELETE", 0);
        Page<CustomerBalanceDetail> pageList = new Page<>(page, 10);
        List<CustomerBalanceDetail> list = customerBalanceDetailMapper.selectPage(pageList, wrapper);

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