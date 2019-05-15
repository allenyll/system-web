package com.sw.base.controller.goods;

import com.sw.cache.util.DataResponse;
import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.goods.AttributesServiceImpl;
import com.sw.common.entity.goods.Attributes;
import com.sw.common.util.CollectionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system-web/attributes")
public class AttributesController extends BaseController<AttributesServiceImpl,Attributes> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttributesController.class);

    @Override
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public DataResponse list() {
        DataResponse dataResponse = super.list();
        List<Attributes> list = (List<Attributes>) dataResponse.get("list");
        Map<String, String> map = new HashMap<>();
        List<Map<String, String>> newList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)){
            for(Attributes attributes:list){
                Map<String, String> _map = new HashMap<>();
                map.put(attributes.getPkAttributeId(), attributes.getAttrName());
                _map.put("label", attributes.getAttrName());
                _map.put("value", attributes.getPkAttributeId());
                newList.add(_map);
            }
        }
        dataResponse.put("map", map);
        dataResponse.put("list", newList);
        return dataResponse;
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public DataResponse get(@PathVariable String id){
        Map<String, Object> result = new HashMap<>();

        DataResponse dataResponse = super.get(id);
        Attributes obj = (Attributes) dataResponse.get("obj");
        if(obj != null){
            String categoryId = obj.getFkCategoryId();
            categoryId = categoryId.substring(1, categoryId.length() - 1).replace("\"", "");
            String[] categoryIdArr = categoryId.split(",");
            obj.setCategoryIds(categoryIdArr);
        }

        result.put("obj", obj);


        return DataResponse.success(result);
    }

}