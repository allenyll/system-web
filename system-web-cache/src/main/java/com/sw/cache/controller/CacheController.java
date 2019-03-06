package com.sw.cache.controller;

import com.sw.cache.CacheUtil;
import com.sw.cache.service.IRedisService;
import com.sw.cache.util.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cache 缓存控制器
 * @Author: yu.leilei
 * @Date: 下午 11:33 2018/5/28 0028
 */
@RestController
@RequestMapping("/system-web/cache")
public class CacheController {

    @Autowired
    IRedisService redisService;

    @Autowired
    CacheUtil cacheUtil;


    @ResponseBody
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public DataResponse page(@RequestParam Map<String, Object> params){
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> list =  redisService.getAllCache();
        result.put("list", list);
        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "remove/{key}", method = RequestMethod.DELETE)
    public DataResponse removeRedisByKey(@PathVariable String key){
        redisService.remove(key);
        return DataResponse.success();
    }

    @ResponseBody
    @RequestMapping(value = "removeAll", method = RequestMethod.DELETE)
    public DataResponse removeAllCache(){
        redisService.removeAll();
        return DataResponse.success();
    }

}
