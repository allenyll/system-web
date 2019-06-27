package com.sw.base.controller.customer;

import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.customer.CustomerLevelServiceImpl;
import com.sw.common.entity.customer.CustomerLevel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/system-web/customerLevel")
public class CustomerLevelController extends BaseController<CustomerLevelServiceImpl,CustomerLevel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerLevelController.class);

}