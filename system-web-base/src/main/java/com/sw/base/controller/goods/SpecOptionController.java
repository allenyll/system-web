package com.sw.base.controller.goods;

import com.sw.cache.util.DataResponse;
import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.goods.SpecOptionServiceImpl;
import com.sw.common.entity.goods.SpecOption;
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
@RequestMapping("/system-web/specOption")
public class SpecOptionController extends BaseController<SpecOptionServiceImpl,SpecOption> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecOptionController.class);

    @Override
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public DataResponse list() {
        DataResponse dataResponse = super.list();
        List<SpecOption> list = (List<SpecOption>) dataResponse.get("list");
        Map<String, String> map = new HashMap<>();
        List<Map<String, String>> newList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)){
            for(SpecOption specOption:list){
                Map<String, String> _map = new HashMap<>();
                map.put(specOption.getPkSpecOptionId(), specOption.getName());
                _map.put("label", specOption.getName());
                _map.put("value", specOption.getPkSpecOptionId());
                newList.add(_map);
            }
        }
        dataResponse.put("map", map);
        dataResponse.put("list", newList);
        return dataResponse;
    }

}
