package com.sw.base.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sw.common.entity.SysRole;
import com.sw.base.mapper.SysRoleMapper;
import com.sw.base.service.ISysRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author yu.leilei
 * @since 2018-11-13
 */
@Service("roleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    SysRoleMapper roleMapper;

    @Override
    public List<SysRole> getRoleListByPage(EntityWrapper<SysRole> wrapper, int start, int limit) {
        return roleMapper.selectPage(new Page<SysRole>(start, limit), wrapper);
    }
}
