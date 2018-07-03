package com.sw.auth.service.impl;

import com.sw.auth.entity.JwtUser;
import com.sw.auth.service.IAuthService;
import com.sw.auth.util.JwtUtil;
import com.sw.base.entity.SysUser;
import com.sw.base.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户操作接口实现
 * @Author: yu.leilei
 * @Date: 下午 3:11 2018/5/25 0025
 */
@Service("authService")
public class AuthServiceImpl implements IAuthService{

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ISysUserService sysUserService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public SysUser register(SysUser userToAdd) {
        final String userName = userToAdd.getAccount();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getPassword();
        userToAdd.setPassword(passwordEncoder.encode(rawPassword));
        userToAdd.setLastPasswordResetDate(new Date());
        sysUserService.insert(userToAdd);
        return userToAdd;
    }

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtUtil.generateToken(userDetails);
        return token;
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        if (jwtUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
            return jwtUtil.refreshToken(token);
        }
        return null;
    }
}
