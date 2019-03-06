package com.sw.base.controller.customer;


import com.sw.base.controller.BaseControllerBak;
import com.sw.base.service.customer.ICustomerService;
import com.sw.common.entity.customer.Customer;
import com.sw.cache.util.DataResponse;
import com.sw.common.util.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author yu.leilei
 * @since 2018-10-22
 */
@Controller
@RequestMapping("/system-web/customer")
public class CustomerController extends BaseControllerBak<Customer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    ICustomerService customerService;

    @ResponseBody
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public DataResponse page(@RequestParam Map<String, Object> params) {
        LOGGER.info("============= { 开始调用方法：CUSTOMER page() } =============");
        Map<String, Object> result = new HashMap<>();
        page = Integer.parseInt(MapUtil.getMapValue(params, "page"));
        limit = Integer.parseInt(MapUtil.getMapValue(params, "limit"));

        int start = (page - 1) * limit;
        params.put("start", start);
        params.put("limit", limit);

        int total = customerService.count(params);

        List<Customer> list = customerService.page(params);

        if(!CollectionUtils.isEmpty(list)){
        }

        result.put("total", total);
        result.put("list", list);

        LOGGER.info("============= { 结束调用方法：CUSTOMER page() } =============");
        return DataResponse.success(result);
    }

}
