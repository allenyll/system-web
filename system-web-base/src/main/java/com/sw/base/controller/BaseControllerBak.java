package com.sw.base.controller;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sw.cache.service.IRedisService;
import com.sw.common.util.DataResponse;
import com.sw.common.util.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础控制器
 * @Author: yu.leilei
 * @Date: 上午 10:53 2018/2/28 0028
 */
@Controller
public class BaseControllerBak<T extends Model>{

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseControllerBak.class);

    @Autowired
    protected IRedisService redisService;

    protected String userId;

    /**
     * 当前页数
     */
    protected int page = 1;

    /**
     * 每页数量
     */
    protected int limit = 10;


    /**
     * 分页查询
     * @param service
     * @param wrapper
     * @param params
     * @return
     */
    protected DataResponse page(IService<T> service, EntityWrapper<T> wrapper, Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        LOGGER.info("传入参数=============" + params);
        page = Integer.parseInt(MapUtil.getMapValue(params, "page"));
        limit = Integer.parseInt(MapUtil.getMapValue(params, "limit"));
        int total = service.selectCount(wrapper);
        Page<T> pages = service.selectPage(new Page<>(page, limit), wrapper);
        List<T> list = pages.getRecords();
        result.put("total", total);
        result.put("list", list);

        return DataResponse.success(result);
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
