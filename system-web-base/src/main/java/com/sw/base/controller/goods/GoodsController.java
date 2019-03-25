package com.sw.base.controller.goods;

import com.sw.base.controller.BaseController;
import com.sw.base.service.impl.goods.GoodsServiceImpl;
import com.sw.common.entity.goods.Goods;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/system-web/goods")
public class GoodsController extends BaseController<GoodsServiceImpl,Goods> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsController.class);

}