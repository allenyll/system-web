package com.sw.auth.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sw.auth.mapper.SysLogMapper;
import com.sw.auth.service.ISysLogService;
import com.sw.common.entity.SysLog;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 记录日志 服务实现类
 * </p>
 *
 * @author yu.leilei
 * @since 2018-12-23
 */
@Service("logService")
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public List<SysLog> getLogListPage(EntityWrapper<SysLog> wrapper, int start, int limit) {
        return sysLogMapper.selectPage(new Page<SysLog>(start, limit), wrapper);
    }
}
