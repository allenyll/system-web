package com.sw.base.controller.order;

import com.sw.cache.util.DataResponse;
import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.order.OrderServiceImpl;
import com.sw.common.entity.order.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/system-web/order")
public class OrderController extends BaseController<OrderServiceImpl,Order> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderServiceImpl orderService;

    @ResponseBody
    @RequestMapping(value = "/cacheOrder", method = RequestMethod.POST)
    public DataResponse cacheOrder(@RequestBody Map<String, Object> param){
        Map<String, Object> result = new HashMap<>();
        log.info("缓存订单参数: {}"+param);
        Order order = new Order();
        try {
            orderService.createOrder(order, param);
        } catch (Exception e) {
            e.printStackTrace();
            return DataResponse.fail("创建订单失败!");
        }


        return DataResponse.success();
    }

}
