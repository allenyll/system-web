package com.sw.base.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.common.entity.SysRole;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author yu.leilei
 * @since 2018-11-13
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 分页查询角色
     * @param userWrapper
     * @param start
     * @param limit
     * @return
     */
    List<SysRole> getRoleListByPage(EntityWrapper<SysRole> wrapper, int start, int limit);
}
