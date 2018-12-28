package com.sw.base.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.auth.service.ISysLogService;
import com.sw.common.constants.dict.LogType;
import com.sw.common.entity.SysLog;
import com.sw.common.util.DataResponse;
import com.sw.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 记录日志 前端控制器
 * </p>
 *
 * @author yu.leilei
 * @since 2018-12-23
 */
@Controller
@RequestMapping("/sysLog")
public class SysLogController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(SysLogController.class);

    @Autowired
    private ISysLogService logService;

    @ResponseBody
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public DataResponse getLogList(@RequestParam Map<String, Object> params){
        LOGGER.info("============= {开始调用方法：getLogList(} =============");
        Map<String, Object> result = new HashMap<>();
        LOGGER.info("传入参数=============" + params);
        page = Integer.parseInt(params.get("page").toString());
        limit = Integer.parseInt(params.get("limit").toString());
        String account = params.get("account").toString();

        EntityWrapper<SysLog> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        if(StringUtil.isNotEmpty(account)){
            wrapper.like("ACCOUNT", account);
        }
        int start = (page - 1) * limit;
        int total = logService.selectCount(wrapper);
        List<SysLog> list = logService.getLogListPage(wrapper, start, limit);
        if(!CollectionUtils.isEmpty(list)){
            for(SysLog log:list){
                log.setLogTypeMsg(LogType.codeToMessage(log.getLogType()));
            }
        }
        result.put("total", total);
        result.put("logList", list);
        LOGGER.info("============= {结束调用方法：getLogList(} =============");
        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public DataResponse get(@PathVariable String id){
        LOGGER.info("==================开始调用 get ================");
        Map<String, Object> result = new HashMap<>();

        EntityWrapper<SysLog> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("PK_LOG_ID", id);

        SysLog log = logService.selectOne(wrapper);
        if(log != null){
            log.setLogTypeMsg(LogType.codeToMessage(log.getLogType()));
        }

        result.put("sysLog", log);

        LOGGER.info("==================结束调用 get ================");

        return DataResponse.success(result);
    }
	
}
