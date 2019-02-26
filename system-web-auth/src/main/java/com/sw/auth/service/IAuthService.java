package com.sw.auth.service;


import com.sw.common.entity.system.User;

/**
 * 用户操作接口
 * @Author: yu.leilei
 * @Date: 下午 3:01 2018/5/25 0025
 */
public interface IAuthService {

    /**
     * 用户注册
     *
     * @param userToAdd 用户信息
     * @return 操作结果
     */
    User register(User userToAdd);

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 操作结果
     */
    String login(String username, String password);

    /**
     * 刷新密钥
     *
     * @param oldToken 原密钥
     * @return 新密钥
     */
    String refresh(String oldToken);
}
