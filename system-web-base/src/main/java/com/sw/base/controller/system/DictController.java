package com.sw.base.controller.system;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.system.DictServiceImpl;
import com.sw.common.constants.dict.StatusDict;
import com.sw.common.entity.system.Dict;
import com.sw.cache.util.DataResponse;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表
 前端控制器
 * </p>
 *
 * @author yu.leilei
 * @since 2019-01-29
 */
@Controller
@RequestMapping("/system-web/dict")
public class DictController extends BaseController<DictServiceImpl, Dict> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DictController.class);

    @Autowired
    DictServiceImpl dictService;

    @ResponseBody
    @RequestMapping(value = "list/{code}", method = RequestMethod.POST)
    public DataResponse list(@PathVariable String code) {
        Map<String, Object> result = new HashMap<>();
        EntityWrapper<Dict> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("STATUS", StatusDict.START.getCode());
        wrapper.like("CODE", code);
        wrapper.ne("PARENT_ID","0");
        List<Dict> list = service.selectList(wrapper);

        Map<String, String> map = new HashMap<>();
        List<Map<String, String>> newList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            for(Dict dict:list){
                Map<String, String> _map = new HashMap<>();
                map.put(dict.getCode(), dict.getName());
                _map.put("label", dict.getName());
                _map.put("value", dict.getCode());
                newList.add(_map);
            }
        }
        result.put("map", map);
        result.put("list", newList);
        return DataResponse.success(result);
    }

    @ApiOperation(value = "获取 parentId = 0 的字典集合")
    @GetMapping("/getParent")
    @ResponseBody
    public DataResponse getParent(){
        LOGGER.info("============= {开始调用方法：getParentDict(} =============");
        Map<String, Object> result = new HashMap<>();

        EntityWrapper<Dict> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.orderBy(true, "CODE", false);
        wrapper.eq("PARENT_ID", "0");
        List<Dict> list = service.selectList(wrapper);
        result.put("list", list);
        return DataResponse.success(result);
    }


    @ApiOperation(value = "获取子字典集合")
    @RequestMapping(value = "/getChild/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getChild(@PathVariable String id){
        LOGGER.info("============= {开始调用方法：DICT getChild(} =============");
        Map<String, Object> result = new HashMap<>();

        EntityWrapper<Dict> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.orderBy(true, "CODE", false);
        wrapper.eq("PARENT_ID", id);
        List<Dict> list = service.selectList(wrapper);

        result.put("list", list);
        return DataResponse.success(result);
    }

}
