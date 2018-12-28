package com.sw.base.controller;

import com.sw.cache.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 基础控制器
 * @Author: yu.leilei
 * @Date: 上午 10:53 2018/2/28 0028
 */
@Controller
public class BaseController {


    @Autowired
    IRedisService redisService;

    protected String userId;

    /**
     * 当前页数
     */
    protected int page = 1;

    /**
     * 每页数量
     */
    protected int limit = 10;

    public void getUser(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
