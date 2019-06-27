package com.sw.base.service.impl.customer;

import org.springframework.stereotype.Service;

import com.sw.common.entity.customer.CustomerLevel;
import com.sw.base.mapper.customer.CustomerLevelMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 会员等级表
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-05-18 16:03:02
 */
@Service("customerLevelService")
public class CustomerLevelServiceImpl extends BaseService<CustomerLevelMapper,CustomerLevel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerLevelServiceImpl.class);
}