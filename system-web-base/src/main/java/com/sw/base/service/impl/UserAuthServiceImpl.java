package com.sw.base.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.base.entity.SysUser;
import com.sw.base.service.ISysUserService;
import com.sw.common.constants.dict.UserStatus;
import com.sw.base.service.IUserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 用户权限相关业务实现
 * @Author: yu.leilei
 * @Date: 下午 4:57 2018/5/24 0024
 */
@Service("userAuthService")
public class UserAuthServiceImpl implements IUserAuthService {

    @Autowired
    ISysUserService sysUserService;

    @Override
    @Cacheable(value= "SYS_AUTH", key="#account+'_USER'")
    public SysUser getSysUser(String userName) {
        EntityWrapper<SysUser> wrapper = new EntityWrapper<>();
        wrapper.eq("ACCOUNT", userName);
        wrapper.eq("STATUS", UserStatus.OK.getCode());
        wrapper.eq("IS_DELETE", 0);
        SysUser sysUser = sysUserService.selectOne(wrapper);
        return sysUser;
    }
}
