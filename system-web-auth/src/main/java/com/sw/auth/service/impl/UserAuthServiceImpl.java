package com.sw.auth.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.auth.service.IUserAuthService;
import com.sw.common.constants.dict.UserStatus;
import com.sw.common.entity.system.User;
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
    UserServiceImpl userService;

    @Override
    @Cacheable(value= "SYS_AUTH", key="#account+'_USER'")
    public User getSysUser(String userName) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("ACCOUNT", userName);
        wrapper.eq("STATUS", UserStatus.OK.getCode());
        wrapper.eq("IS_DELETE", 0);
        User sysUser = userService.selectOne(wrapper);
        return sysUser;
    }
}
