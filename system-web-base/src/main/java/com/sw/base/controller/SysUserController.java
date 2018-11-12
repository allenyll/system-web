package com.sw.base.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.auth.service.ISysUserService;
import com.sw.auth.service.IUserAuthService;
import com.sw.auth.util.JwtUtil;
import com.sw.common.constants.dict.UserStatus;
import com.sw.common.entity.SysUser;
import com.sw.common.util.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author yu.leilei
 * @since 2018-06-12
 */
@Controller
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    ISysUserService sysUserService;

    @Autowired
    IUserAuthService userAuthService;

    public DataResponse getUserInfo(@RequestParam String token){
        JwtUtil jwtUtil = new JwtUtil();
        String userName = jwtUtil.getUsernameFromToken(token);
        SysUser user = userAuthService.getSysUser(userName);
        if(user != null){
            String userId = user.getPkUserId();
            List<>
        }
        return DataResponse.success();
    }
}
