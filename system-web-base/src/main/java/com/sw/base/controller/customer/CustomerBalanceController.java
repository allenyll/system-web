package com.sw.base.controller.customer;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.base.service.impl.customer.CustomerBalanceDetailServiceImpl;
import com.sw.base.service.impl.customer.CustomerServiceImpl;
import com.sw.cache.util.DataResponse;
import com.sw.common.constants.dict.StatusDict;
import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.customer.CustomerBalanceServiceImpl;
import com.sw.common.entity.customer.CustomerBalance;
import com.sw.common.entity.customer.CustomerBalanceDetail;
import com.sw.common.util.AppContext;
import com.sw.common.util.DateUtil;
import com.sw.common.util.MapUtil;
import com.sw.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/system-web/customerBalance")
public class CustomerBalanceController extends BaseController<CustomerBalanceServiceImpl,CustomerBalance> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerBalanceController.class);

    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    CustomerBalanceServiceImpl customerBalanceService;

    @Autowired
    CustomerBalanceDetailServiceImpl customerBalanceDetailService;

    @RequestMapping(value = "/updateBalance",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse updateObj(@RequestBody Map<String, Object> params){
        String openid = MapUtil.getMapValue(params, "openid");
        if(openid.equals(AppContext.getCurrentUserWechatOpenId())){
            String amountStr = MapUtil.getMapValue(params, "amount", "0");
            String customerId = MapUtil.getMapValue(params, "customerId", "0");
            String remark = MapUtil.getMapValue(params, "remark", "");
            BigDecimal amount = new BigDecimal(amountStr);
            if(StringUtil.isEmpty(openid)){
                return DataResponse.fail("用户不能为空");
            }

            EntityWrapper<CustomerBalance> customerBalanceEntityWrapper = new EntityWrapper<>();
            customerBalanceEntityWrapper.eq("IS_DELETE", 0);
            customerBalanceEntityWrapper.eq("FK_CUSTOMER_ID", customerId);
            CustomerBalance customerBalance = customerBalanceService.selectOne(customerBalanceEntityWrapper);
            if(customerBalance == null){
                // 新增
                customerBalance = new CustomerBalance();
                customerBalance.setBalance(amount);
                customerBalance.setWithdrawCash(new BigDecimal("0"));
                customerBalance.setFkCustomerId(customerId);
                customerBalance.setIsDelete(0);
                customerBalance.setAddUser("微信");
                customerBalance.setAddTime(DateUtil.getCurrentDateTime());
                customerBalance.setUpdateUser("微信");
                customerBalance.setUpdateTime(DateUtil.getCurrentDateTime());
                customerBalanceService.insert(customerBalance);
            }else{
                BigDecimal balance = customerBalance.getBalance();
                balance = balance.add(amount);
                customerBalance.setBalance(balance);
                customerBalance.setUpdateUser("微信");
                customerBalance.setUpdateTime(DateUtil.getCurrentDateTime());
                customerBalanceService.updateById(customerBalance);
            }

            // 新增余额明细
            CustomerBalanceDetail customerBalanceDetail = new CustomerBalanceDetail();
            customerBalanceDetail.setFkCustomerId(customerId);
            customerBalanceDetail.setBalance(amount);
            customerBalanceDetail.setType("SW1501");
            customerBalanceDetail.setRemark(remark);
            customerBalanceDetail.setStatus(StatusDict.START.getCode());
            customerBalanceDetail.setTime(DateUtil.getCurrentDateTime());
            customerBalanceDetail.setIsDelete(0);
            customerBalanceDetail.setAddUser("微信");
            customerBalanceDetail.setAddTime(DateUtil.getCurrentDateTime());
            customerBalanceDetail.setUpdateUser("微信");
            customerBalanceDetail.setUpdateTime(DateUtil.getCurrentDateTime());
            customerBalanceDetailService.insert(customerBalanceDetail);
        }else{
            DataResponse.fail("当前登录用户不匹配");
        }

        return DataResponse.success();
    }

    /**
     * 获取余额
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getBalance", method = RequestMethod.POST)
    public DataResponse getBalance(@RequestBody Map<String, Object> param){
        LOGGER.info("==============开始调用 getBalance================");

        Map<String, Object> result = new HashMap<>();

        String customerId = MapUtil.getMapValue(param, "customerId");

        EntityWrapper<CustomerBalance> wrapper = new EntityWrapper<>();
        wrapper.eq("FK_CUSTOMER_ID", customerId);

        CustomerBalance customerBalance = customerBalanceService.selectOne(wrapper);

        result.put("customerBalance", customerBalance);

        return DataResponse.success(result);
    }
}