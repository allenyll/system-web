package com.sw.auth.service;

import com.sw.common.entity.SysUser;

/**
 * 用户权限接口
 * @Author: yu.leilei
 * @Date: 下午 4:49 2018/5/24 0024
 */
public interface IUserAuthService {

    /**
     * 根据账户查询用户
     * @param userName
     * @return
     */
    SysUser getSysUser(String userName);
}
