package com.sw.base.service.impl.customer;

import org.springframework.stereotype.Service;

import com.sw.common.entity.customer.CustomerBalanceDetail;
import com.sw.base.mapper.customer.CustomerBalanceDetailMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 会员余额明细表
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-04-10 16:24:29
 */
@Service("customerBalanceDetailService")
public class CustomerBalanceDetailServiceImpl extends BaseService<CustomerBalanceDetailMapper,CustomerBalanceDetail> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerBalanceDetailServiceImpl.class);
}