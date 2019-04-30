package com.sw.base.service.impl.pay;

import org.springframework.stereotype.Service;

import com.sw.common.entity.pay.Transaction;
import com.sw.base.mapper.pay.TransactionMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 交易表
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-04-04 16:37:41
 */
@Service("transactionService")
public class TransactionServiceImpl extends BaseService<TransactionMapper,Transaction> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);
}