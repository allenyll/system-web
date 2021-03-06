package com.sw.base.controller.goods;

import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.goods.GoodsAppraisesServiceImpl;
import com.sw.common.entity.goods.GoodsAppraises;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/system-web/goodsAppraises")
public class GoodsAppraisesController extends BaseController<GoodsAppraisesServiceImpl,GoodsAppraises> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsAppraisesController.class);

}