package com.sw.base.controller.order;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sw.cache.util.DataResponse;
import com.sw.common.constants.dict.OrderStatusDict;
import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.order.OrderServiceImpl;
import com.sw.common.entity.order.Order;
import com.sw.common.util.DateUtil;
import com.sw.common.util.MapUtil;
import com.sw.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/system-web/order")
public class OrderController extends BaseController<OrderServiceImpl,Order> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderServiceImpl orderService;

    @Override
    @ResponseBody
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public DataResponse page(@RequestParam Map<String, Object> params){
        Map<String, Object> result = new HashMap<>();
        int total = orderService.selectCount(params);
        List<Map<String, Object>>  orderList = orderService.getOrderPage(params);
        result.put("total", total);
        result.put("list", orderList);
        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "deleteOrder", method = RequestMethod.POST)
    public DataResponse deleteOrder(@RequestParam Map<String, Object> params){
        LOGGER.info("deleteOrder=>params："+params);
        DataResponse dataResponse = orderService.deleteOrder(params);
        return dataResponse;
    }

    @ResponseBody
    @RequestMapping(value = "deliveryOrder", method = RequestMethod.POST)
    public DataResponse deliveryOrder(@RequestParam Map<String, Object> params){
        LOGGER.info("deliveryOrder=>params："+params);
        DataResponse dataResponse = orderService.deliveryOrder(params);
        return dataResponse;
    }

    @ResponseBody
    @RequestMapping(value = "closeOrder", method = RequestMethod.POST)
    public DataResponse closeOrder(@RequestParam Map<String, Object> params){
        LOGGER.info("closeOrder=>params："+params);
        DataResponse dataResponse = orderService.closeOrder(params);
        return dataResponse;
    }

    @ResponseBody
    @RequestMapping(value = "updateMoneyInfo", method = RequestMethod.POST)
    public DataResponse updateMoneyInfo(@RequestParam Map<String, Object> params){
        LOGGER.info("updateMoneyInfo=>params："+params);
        DataResponse dataResponse = orderService.updateMoneyInfo(params);
        return dataResponse;
    }

    @ResponseBody
    @RequestMapping(value = "updateReceiverInfo", method = RequestMethod.POST)
    public DataResponse updateReceiverInfo(@RequestParam Map<String, Object> params){
        LOGGER.info("updateReceiverInfo=>params："+params);
        DataResponse dataResponse = orderService.updateReceiverInfo(params);
        return dataResponse;
    }

    @ResponseBody
    @RequestMapping(value = "updateOrderNote", method = RequestMethod.POST)
    public DataResponse updateOrderNote(@RequestParam Map<String, Object> params){
        LOGGER.info("updateOrderNote=>params："+params);
        DataResponse dataResponse = orderService.updateOrderNote(params);
        return dataResponse;
    }

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

        result.put("order", order);

        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "/getOrderNum", method = RequestMethod.POST)
    public DataResponse getOrderNum(@RequestBody Map<String, Object> params){
        Map<String, Object> result =  new HashMap<>();

        int unPayNum = orderService.getUnPayNum(params);
        int unReceiveNum = orderService.getUnReceiveNum(params);
        int deliveryNum = orderService.getDeliveryNum(params);
        int receiveNum = orderService.getReceiveNum(params);
        int appraisesNum = orderService.getAppraisesNum(params);
        int finishNum = orderService.getFinishNum(params);
        result.put("unPayNum", unPayNum);
        result.put("unReceiveNum", unReceiveNum);
        result.put("deliveryNum", deliveryNum);
        result.put("receiveNum", receiveNum);
        result.put("appraisesNum", appraisesNum);
        result.put("finishNum", finishNum);
        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "/getAllOrderList", method = RequestMethod.POST)
    public DataResponse getAllOrderList(@RequestBody Map<String, Object> params){
        Map<String, Object> result =  new HashMap<>();
        page = Integer.parseInt(params.get("page").toString());
        limit = Integer.parseInt(params.get("limit").toString());

        EntityWrapper<Order> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("ORDER_TYPE", "SW0601");
        wrapper.eq("FK_CUSTOMER_ID", MapUtil.getString(params, "customerId"));

        int total = service.selectCount(wrapper);
        Page<Order> list = service.selectPage(new Page<>(page, limit), wrapper);

        result.put("total", total);
        result.put("list", list.getRecords());

        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
    public DataResponse getOrderList(@RequestBody Map<String, Object> params){
        Map<String, Object> result =  new HashMap<>();

        List<Order> list = orderService.getOrderList(params);

        result.put("list", list);

        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "/cancelOrder/{orderId}", method = RequestMethod.POST)
    public DataResponse cancelOrder(@PathVariable String orderId){
        if(StringUtil.isEmpty(orderId)){
            return DataResponse.fail("订单不存在!");
        }
        try {
            Order order = orderService.selectById(orderId);
            order.setOrderStatus(OrderStatusDict.CANCEL.getCode());
            order.setUpdateTime(DateUtil.getCurrentDateTime());
            orderService.updateById(order);
        } catch (Exception e) {
            e.printStackTrace();
            return DataResponse.fail("订单取消失败");
        }

        return DataResponse.success();
    }

    @ResponseBody
    @RequestMapping(value = "/deleteOrder/{orderId}", method = RequestMethod.POST)
    public DataResponse deleteOrder(@PathVariable String orderId){
        if(StringUtil.isEmpty(orderId)){
            return DataResponse.fail("订单不存在!");
        }
        try {
            Order order = orderService.selectById(orderId);
            order.setIsDelete(1);
            order.setUpdateTime(DateUtil.getCurrentDateTime());
            orderService.updateById(order);
        } catch (Exception e) {
            e.printStackTrace();
            return DataResponse.fail("订单删除失败");
        }

        return DataResponse.success();
    }

    @ResponseBody
    @RequestMapping(value = "/receiveOrder/{orderId}", method = RequestMethod.POST)
    public DataResponse receiveOrder(@PathVariable String orderId){
        if(StringUtil.isEmpty(orderId)){
            return DataResponse.fail("订单不存在!");
        }
        String time = DateUtil.getCurrentDateTime();
        try {
            Order order = orderService.selectById(orderId);
            order.setOrderStatus(OrderStatusDict.RECEIVE.getCode());
            order.setReceiveTime(time);
            order.setUpdateTime(time);
            orderService.updateById(order);
        } catch (Exception e) {
            e.printStackTrace();
            return DataResponse.fail("订单删除失败");
        }

        return DataResponse.success();
    }

}
