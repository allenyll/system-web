package com.sw.base.controller.order;

import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.order.CartServiceImpl;
import com.sw.common.entity.order.Cart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/system-web/cart")
public class CartController extends BaseController<CartServiceImpl,Cart> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

}