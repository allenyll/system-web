package com.sw.auth.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sw.auth.mapper.UserMapper;
import com.sw.common.entity.system.User;
import com.sw.common.service.BaseService;
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
@Service("userService")
public class UserServiceImpl extends BaseService<UserMapper, User> {

    @Autowired
    UserMapper userMapper;

    public List<Map<String, Object>> getUserRoleMenuList(Map<String, String> param) {
        return userMapper.getUserRoleMenuList(param);
    }

    public List<User> getUserListByPage(EntityWrapper<User> userWrapper, int page, int limit) {
        return userMapper.selectPage(new Page<User>(page, limit), userWrapper);
    }
}
