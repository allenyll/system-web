package com.sw.auth.service.impl;

import com.sw.auth.mapper.LogMapper;
import com.sw.common.entity.system.Log;
import com.sw.common.service.BaseService;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 记录日志 服务实现类
 * </p>
 *
 * @author yu.leilei
 * @since 2018-12-23
 */
@Service("logService")
public class LogServiceImpl extends BaseService<LogMapper, Log> {
}
