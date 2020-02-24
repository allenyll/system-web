package com.sw.base.controller.market;

import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.market.CouponDetailServiceImpl;
import com.sw.common.entity.market.CouponDetail;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/system-web/couponDetail")
public class CouponDetailController extends BaseController<CouponDetailServiceImpl,CouponDetail> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouponDetailController.class);

}
