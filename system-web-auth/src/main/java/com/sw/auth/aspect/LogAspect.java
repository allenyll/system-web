package com.sw.auth.aspect;

import com.sw.auth.annotation.Log;
import com.sw.auth.util.JwtUtil;
import com.sw.auth.service.ISysLogService;
import com.sw.cache.service.IRedisService;
import com.sw.common.entity.SysLog;
import com.sw.common.util.*;
import net.sf.json.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @Description:  Log日志切面
 * @Author:       allenyll
 * @Date:         2018/12/23 1:11 PM
 * @Version:      1.0
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private ISysLogService logService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IRedisService redisService;

    @Pointcut("@annotation(com.sw.auth.annotation.Log)")
    public void logPoint(){

    }

    @Around("logPoint()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();

        // 保存日志
        saveLog(joinPoint, endTime - beginTime);

        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Method method = signature.getMethod();

        Log log = method.getAnnotation(Log.class);

        SysLog sysLog = new SysLog();

        if(log != null){
            sysLog.setOperation(log.value());
        }

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setClassName(className + "." + methodName + "()");

        Object[] args = joinPoint.getArgs();

        //sysLog.setParams(args.toString());
        if(args.length > 0){
            String params=  "";
            for(int i=0; i<args.length; i++){
                params += "[" + JSONUtil.beanToJson(args[i]) + "], ";
            }
            if(StringUtil.isNotEmpty(params)){
                params = params.substring(0, params.length() - 2);
            }
            sysLog.setParams(params);
        }

        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        sysLog.setIp(IPUtil.getIpAddr(request));

        String userId = redisService.get("userId");
        String account = redisService.get("account");
        if(StringUtil.isEmpty(userId)){
            JSONObject jsonObject = JSONObject.fromObject(args[0]);
            account = jsonObject.getString("username");
        }

        sysLog.setFkUserId(userId);
        sysLog.setAccount(account);
        sysLog.setOperateTime(time);
        sysLog.setLogType("SW0302");
        sysLog.setIsDelete(0);
        sysLog.setAddTime(DateUtil.getCurrentDateTime());
        sysLog.setAddUser(userId);
        sysLog.setUpdateTime(DateUtil.getCurrentDateTime());
        sysLog.setUpdateUser(userId);

        logService.insert(sysLog);

    }
}
