package com.sw.base.controller.customer;


import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.customer.CustomerPointDetailServiceImpl;
import com.sw.common.entity.customer.CustomerPointDetail;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yu.leilei
 * @since 2019-01-09
 */
@Controller
@RequestMapping("/system-web/customerPointDetail")
public class CustomerPointDetailController extends BaseController<CustomerPointDetailServiceImpl, CustomerPointDetail> {

}
