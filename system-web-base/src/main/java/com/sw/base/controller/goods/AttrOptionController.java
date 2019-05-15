package com.sw.base.controller.goods;

import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.goods.AttrOptionServiceImpl;
import com.sw.common.entity.goods.AttrOption;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/system-web/attrOption")
public class AttrOptionController extends BaseController<AttrOptionServiceImpl,AttrOption> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttrOptionController.class);

}