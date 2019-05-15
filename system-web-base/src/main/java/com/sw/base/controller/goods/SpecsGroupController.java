package com.sw.base.controller.goods;

import com.sw.cache.util.DataResponse;
import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.goods.SpecsGroupServiceImpl;
import com.sw.common.entity.goods.SpecsGroup;
import com.sw.common.util.CollectionUtil;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/system-web/specsGroup")
public class SpecsGroupController extends BaseController<SpecsGroupServiceImpl,SpecsGroup> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecsGroupController.class);

    @Override
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public DataResponse list() {
        DataResponse dataResponse = super.list();
        List<SpecsGroup> list = (List<SpecsGroup>) dataResponse.get("list");
        Map<String, String> map = new HashMap<>();
        List<Map<String, String>> newList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)){
            for(SpecsGroup specsGroup:list){
                Map<String, String> _map = new HashMap<>();
                map.put(specsGroup.getPkSpecsGroupId(), specsGroup.getName());
                _map.put("label", specsGroup.getName());
                _map.put("value", specsGroup.getPkSpecsGroupId());
                newList.add(_map);
            }
        }
        dataResponse.put("map", map);
        dataResponse.put("list", newList);
        return dataResponse;
    }

}