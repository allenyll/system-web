package com.sw.base.controller.goods;

import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.goods.SpecOptionServiceImpl;
import com.sw.common.entity.goods.SpecOption;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/system-web/specOption")
public class SpecOptionController extends BaseController<SpecOptionServiceImpl,SpecOption> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecOptionController.class);

}