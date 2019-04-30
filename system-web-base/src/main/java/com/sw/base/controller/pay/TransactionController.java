package com.sw.base.controller.pay;

import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.pay.TransactionServiceImpl;
import com.sw.common.entity.pay.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/system-web/transaction")
public class TransactionController extends BaseController<TransactionServiceImpl,Transaction> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

}