package com.sw.base.controller.goods;

import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.goods.SkuServiceImpl;
import com.sw.common.entity.goods.Sku;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/system-web/sku")
public class SkuController extends BaseController<SkuServiceImpl,Sku> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SkuController.class);

}