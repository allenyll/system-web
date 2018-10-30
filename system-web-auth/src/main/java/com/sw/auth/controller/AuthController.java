package com.sw.auth.controller;

import com.sw.auth.entity.JwtAuthenticationRequest;
import com.sw.auth.entity.JwtAuthenticationResponse;
import com.sw.auth.service.IAuthService;
import com.sw.auth.util.JwtUtil;
import com.sw.base.entity.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

/**
 * 测试JWT
 * @Author: yu.leilei
 * @Date: 下午 3:37 2018/5/25 0025
 */
@Api(description = "权限接口")
@RestController
public class AuthController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private IAuthService authService;

    @ApiOperation(value = "授权" ,  notes="授权")
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken( @RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
        final String token = authService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @ApiOperation(value = "刷新token" ,  notes="刷新token")
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException{
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if(refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        }
    }

    @ApiOperation(value = "注册用户" ,  notes="注册用户")
    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public SysUser register(@RequestBody SysUser addedUser) throws AuthenticationException{
        return authService.register(addedUser);
    }

    @ApiOperation(value = "登录" ,  notes="登录")
    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public String login(@RequestParam String username, @RequestParam String password){
        String token = authService.login(username, password);
        return "index";
    }
}
