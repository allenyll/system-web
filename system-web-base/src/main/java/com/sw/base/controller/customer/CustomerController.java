package com.sw.base.controller.customer;


import com.sw.base.controller.BaseController;
import com.sw.base.service.impl.customer.CustomerServiceImpl;
import com.sw.common.entity.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

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
public class CustomerController extends BaseController<CustomerServiceImpl, Customer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

}
