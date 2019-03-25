package com.sw.base.controller.goods;

import com.sw.base.controller.BaseController;
import com.sw.base.service.impl.goods.ColorGroupServiceImpl;
import com.sw.cache.util.DataResponse;
import com.sw.common.entity.goods.ColorGroup;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
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
@RequestMapping("/system-web/colorGroup")
public class ColorGroupController extends BaseController<ColorGroupServiceImpl,ColorGroup> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ColorGroupController.class);

    @Override
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public DataResponse list() {
        DataResponse dataResponse = super.list();
        List<ColorGroup> list = (List<ColorGroup>) dataResponse.get("list");
        Map<String, String> map = new HashMap<>();
        List<Map<String, String>> newList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            for(ColorGroup colorGroup:list){
                Map<String, String> _map = new HashMap<>();
                map.put(colorGroup.getPkColorGroupId(), colorGroup.getColorGroupName());
                _map.put("label", colorGroup.getColorGroupName());
                _map.put("value", colorGroup.getPkColorGroupId());
                newList.add(_map);
            }
        }
        dataResponse.put("map", map);
        dataResponse.put("list", newList);
        return dataResponse;
    }
}