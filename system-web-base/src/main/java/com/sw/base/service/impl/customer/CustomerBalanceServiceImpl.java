package com.sw.base.service.impl.customer;

import org.springframework.stereotype.Service;

import com.sw.common.entity.customer.CustomerBalance;
import com.sw.base.mapper.customer.CustomerBalanceMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 会员余额表
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-04-10 16:16:16
 */
@Service("customerBalanceService")
public class CustomerBalanceServiceImpl extends BaseService<CustomerBalanceMapper,CustomerBalance> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerBalanceServiceImpl.class);
}