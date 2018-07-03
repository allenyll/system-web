package com.sw.auth.filter;

import com.sw.auth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.sw.common.util.StringUtil;

/**
 * token验证
 * @Author: yu.leilei
 * @Date: 上午 10:41 2018/5/25 0025
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter{

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(this.tokenHeader);
        if(StringUtil.isNotEmpty(authHeader) && authHeader.startsWith(tokenHead)){
            // token 在"Bearer "之后
            final String authToken = authHeader.substring(tokenHead.length());
            // 根据token获取用户名
            String userName = jwtUtil.getUsernameFromToken(authToken);
            logger.info("JwtAuthenticationTokenFilter[doFilterInternal] checking authentication " + userName);

            // token 校验通过
            if(StringUtil.isNotEmpty(userName) && SecurityContextHolder.getContext().getAuthentication() == null){
                // 根据account去数据库中查询user数据，足够信任token的情况下，可以省略这一步
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);

                // 判断token是否有效
                if(jwtUtil.validateToken(authToken, userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.info("authenticated user " + userName + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
