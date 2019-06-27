package com.sw.base.controller.order;

import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.order.OrderDetailServiceImpl;
import com.sw.common.entity.order.OrderDetail;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/system-web/orderDetail")
public class OrderDetailController extends BaseController<OrderDetailServiceImpl,OrderDetail> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDetailController.class);

}