package com.sw.base.service.impl.order;

import org.springframework.stereotype.Service;

import com.sw.common.entity.order.OrderDetail;
import com.sw.base.mapper.order.OrderDetailMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}