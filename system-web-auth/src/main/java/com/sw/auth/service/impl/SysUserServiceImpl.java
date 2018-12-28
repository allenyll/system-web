package com.sw.auth.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sw.auth.mapper.SysUserMapper;
import com.sw.auth.service.ISysUserService;
import com.sw.common.entity.SysUser;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author yu.leilei
 * @since 2018-06-12
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public List<Map<String, Object>> getUserRoleMenuList(Map<String, String> param) {

        return sysUserMapper.getUserRoleMenuList(param);

    }

    @Override
    public List<SysUser> getUserListByPage(EntityWrapper<SysUser> userWrapper, int page, int limit) {
        return sysUserMapper.selectPage(new Page<SysUser>(page, limit), userWrapper);
    }
}
