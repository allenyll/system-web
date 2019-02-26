package com.sw.base.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sw.common.service.BaseService;
import com.sw.cache.service.IRedisService;
import com.sw.common.constants.WrapperContants;
import com.sw.common.entity.Entity;
import com.sw.common.util.DataResponse;
import com.sw.common.util.DateUtil;
import com.sw.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * 基础控制器
 * @Author: yu.leilei
 * @Date: 上午 10:53 2018/2/28 0028
 */
public class BaseController<Service extends BaseService, T extends Entity>{

    @Autowired
    protected Service service;

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected IRedisService redisService;

    /**
     * 当前页数
     */
    protected int page = 1;

    /**
     * 每页数量
     */
    protected int limit = 10;

    @ResponseBody
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public DataResponse page(@RequestParam Map<String, Object> params){
        Map<String, Object> result = new HashMap<>();

        LOGGER.info("传入参数=============" + params);

        page = Integer.parseInt(params.get("page").toString());
        limit = Integer.parseInt(params.get("limit").toString());

        EntityWrapper<T> wrapper = mapToWrapper(params);

        int total = service.selectCount(wrapper);
        Page<T> list = service.selectPage(new Page<T>(page, limit), wrapper);

        result.put("total", total);
        result.put("list", list.getRecords());

        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public DataResponse get(@PathVariable String id){
        Map<String, Object> result = new HashMap<>();

        T obj = (T) service.selectById(id);

        result.put("obj", obj);


        return DataResponse.success(result);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public DataResponse add(@RequestBody T t){
        LOGGER.info("开始添加 "+t.getClass());
        String userId = redisService.get("userId");

        t.setIsDelete(0);
        t.setAddTime(DateUtil.getCurrentDateTime());
        t.setAddUser(userId);
        t.setUpdateTime(DateUtil.getCurrentDateTime());
        t.setUpdateUser(userId);
        try {
            service.insert(t);
        } catch (Exception e) {
            LOGGER.info(t.getClass() + " 添加失败");
            return DataResponse.fail(t.getClass()+" 添加失败");
        }
        LOGGER.info("结束添加 "+ t.getClass());
        return DataResponse.success();
    }

    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    @ResponseBody
    public DataResponse update( @RequestBody T t){
        String userId = redisService.get("userId");

        t.setUpdateTime(DateUtil.getCurrentDateTime());
        t.setUpdateUser(userId);
        service.updateById(t);

        return DataResponse.success();
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public DataResponse delete(@PathVariable String id, @RequestParam Map<String, Object> params){

        String userId = redisService.get("userId");
        LOGGER.info("userId" + userId);

        T obj = (T) service.selectById(id);

        EntityWrapper<T> delWrapper = mapToWrapper(params);
        obj.setIsDelete(1);
        obj.setUpdateTime(DateUtil.getCurrentDateTime());
        obj.setUpdateUser(userId);
        service.update(obj, delWrapper);

        return DataResponse.success();
    }

    /**
     * 将参数封装成wrapper查询条件
     * @param params
     * @return
     */
    protected EntityWrapper<T> mapToWrapper(Map<String, Object> params) {
        EntityWrapper<T> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        Set<Map.Entry<String, Object>> set = params.entrySet();
        if(!CollectionUtils.isEmpty(set)){
            for(Map.Entry<String, Object> entry : set){
                String key = entry.getKey();
                if(StringUtil.isNotEmpty(key)){
                    String condition = key.split("_")[0];
                    String val = entry.getValue().toString();
                    if(StringUtil.isNotEmpty(val)){
                        if(WrapperContants.LIKE.equals(condition)){
                            wrapper.like(key.substring(key.indexOf("_")+1), entry.getValue().toString());
                        }else if(WrapperContants.EQ.equals(condition)){
                            wrapper.eq(key.substring(key.indexOf("_")+1), entry.getValue().toString());
                        }else if(WrapperContants.GT.equals(condition)){
                            wrapper.gt(key.substring(key.indexOf("_")+1), entry.getValue().toString());
                        }else if(WrapperContants.GE.equals(condition)){
                            wrapper.ge(key.substring(key.indexOf("_")+1), entry.getValue().toString());
                        }else if(WrapperContants.LT.equals(condition)){
                            wrapper.lt(key.substring(key.indexOf("_")+1), entry.getValue().toString());
                        }else if(WrapperContants.LE.equals(condition)){
                            wrapper.le(key.substring(key.indexOf("_")+1), entry.getValue().toString());
                        }
                    }
                    if(WrapperContants.ORDER.equals(condition)){
                        wrapper.orderBy(val);
                    }
                }
            }
        }
        return wrapper;
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
