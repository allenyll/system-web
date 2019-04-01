package com.sw.base.controller.system;


import com.sw.auth.service.impl.LogServiceImpl;
import com.sw.common.controller.BaseController;
import com.sw.common.entity.system.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 记录日志 前端控制器
 * </p>
 *
 * @author yu.leilei
 * @since 2018-12-23
 */
@Controller
@RequestMapping("/system-web/log/")
public class LogController extends BaseController<LogServiceImpl, Log> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogController.class);

	
}
