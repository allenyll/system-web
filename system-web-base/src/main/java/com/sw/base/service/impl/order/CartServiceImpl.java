package com.sw.base.service.impl.order;

import org.springframework.stereotype.Service;

import com.sw.common.entity.order.Cart;
import com.sw.base.mapper.order.CartMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 购物车明细
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-06-24 10:22:55
 */
@Service("cartService")
public class CartServiceImpl extends BaseService<CartMapper,Cart> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);
}