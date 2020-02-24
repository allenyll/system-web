package com.sw.base.service.impl.order;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.base.service.impl.customer.CustomerAddressServiceImpl;
import com.sw.base.service.impl.customer.CustomerServiceImpl;
import com.sw.common.entity.customer.Customer;
import com.sw.common.entity.customer.CustomerAddress;
import com.sw.common.entity.order.Order;
import com.sw.common.entity.order.OrderOperateLog;
import com.sw.common.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sw.common.entity.order.OrderDetail;
import com.sw.base.mapper.order.OrderDetailMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 订单明细表
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-06-26 21:15:58
 */
@Service("orderDetailService")
public class OrderDetailServiceImpl extends BaseService<OrderDetailMapper,OrderDetail> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDetailServiceImpl.class);

    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    CustomerAddressServiceImpl customerAddressService;

    @Autowired
    OrderOperateLogServiceImpl orderOperateLogService;

    @Autowired
    CustomerServiceImpl customerService;

    public List<OrderDetail> getOrderDetailList(Map<String, Object> params){
        EntityWrapper<OrderDetail> wrapper  = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("FK_ORDER_ID", MapUtil.getString(params, "orderId"));
        List<OrderDetail> list = orderDetailMapper.selectList(wrapper);
        return list;
    }

    public Order getOrderDetail(Map<String, Object> map) {
        EntityWrapper<Order> wrapper  = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("PK_ORDER_ID", MapUtil.getString(map, "orderId"));
        Order order = orderService.selectOne(wrapper);
        if(order == null){
            return null;
        }
        Customer customer = customerService.selectById(order.getFkCustomerId());
        order.setCustomer(customer);
        CustomerAddress customerAddress = customerAddressService.selectById(order.getFkAddressId());
        order.setCustomerAddress(customerAddress);
        List<OrderDetail> orderDetailList = getOrderDetailList(map);
        order.setOrderDetails(orderDetailList);
        List<OrderOperateLog> orderOperateLogs = orderOperateLogService.getOperateList(map);
        order.setOrderOperateLogs(orderOperateLogs);
        return order;
    }
}
