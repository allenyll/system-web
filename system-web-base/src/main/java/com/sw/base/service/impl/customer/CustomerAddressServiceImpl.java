package com.sw.base.service.impl.customer;

import org.springframework.stereotype.Service;

import com.sw.common.entity.customer.CustomerAddress;
import com.sw.base.mapper.customer.CustomerAddressMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 会员收货地址
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-06-25 15:58:58
 */
@Service("customerAddressService")
public class CustomerAddressServiceImpl extends BaseService<CustomerAddressMapper,CustomerAddress> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAddressServiceImpl.class);
}