package com.sw.base.controller.goods;

import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.goods.SizeGroupServiceImpl;
import com.sw.cache.util.DataResponse;
import com.sw.common.entity.goods.SizeGroup;
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
@RequestMapping("/system-web/sizeGroup")
public class SizeGroupController extends BaseController<SizeGroupServiceImpl,SizeGroup> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SizeGroupController.class);

    @Override
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public DataResponse list() {
        DataResponse dataResponse = super.list();
        List<SizeGroup> list = (List<SizeGroup>) dataResponse.get("list");
        Map<String, String> map = new HashMap<>();
        List<Map<String, String>> newList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            for(SizeGroup sizeGroup:list){
                Map<String, String> _map = new HashMap<>();
                map.put(sizeGroup.getPkSizeGroupId(), sizeGroup.getSizeGroupName());
                _map.put("label", sizeGroup.getSizeGroupName());
                _map.put("value", sizeGroup.getPkSizeGroupId());
                newList.add(_map);
            }
        }
        dataResponse.put("map", map);
        dataResponse.put("list", newList);
        return dataResponse;
    }

}