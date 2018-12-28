package com.sw.auth.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.common.entity.SysLog;
import com.baomidou.mybatisplus.service.IService;
import com.sw.common.entity.SysUser;

import java.util.List;

/**
 * <p>
 * 记录日志 服务类
 * </p>
 *
 * @author yu.leilei
 * @since 2018-12-23
 */
public interface ISysLogService extends IService<SysLog> {

    /**
     * 分页获取日志集合
     * @param wrapper
     * @param start
     * @param limit
     * @return
     */
    List<SysLog> getLogListPage(EntityWrapper<SysLog> wrapper, int start, int limit);
}
