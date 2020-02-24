package com.sw.base.controller.market;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.cache.util.DataResponse;
import com.sw.common.constants.dict.StatusDict;
import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.market.AdServiceImpl;
import com.sw.common.entity.Entity;
import com.sw.common.entity.market.Ad;
import com.sw.common.entity.market.Ad;
import com.sw.common.util.DateUtil;
import com.sw.common.util.MapUtil;
import com.sw.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system-web/ad")
public class AdController extends BaseController<AdServiceImpl,Ad> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdController.class);

    @Autowired
    AdServiceImpl adService;

    @Override
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public DataResponse list() {
        DataResponse dataResponse = super.list();
        List<Ad> list = (List<Ad>) dataResponse.get("list");
        Map<String, String> map = new HashMap<>();
        List<Map<String, String>> newList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            for(Ad Ad:list){
                Map<String, String> _map = new HashMap<>();
                map.put(Ad.getPkAdId(), Ad.getAdName());
                _map.put("label", Ad.getAdName());
                _map.put("value", Ad.getPkAdId());
                newList.add(_map);
            }
        }
        dataResponse.put("map", map);
        dataResponse.put("list", newList);
        return dataResponse;
    }

    @ResponseBody
    @RequestMapping(value = "getAdList", method = RequestMethod.POST)
    public DataResponse getAdList(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        String adType = MapUtil.getString(params, "adType");
        String time = DateUtil.getCurrentDateTime();
        EntityWrapper<Ad> wrapper = new EntityWrapper<>();
        wrapper.eq("AD_TYPE", adType);
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("IS_USED", StatusDict.START.getCode());
        wrapper.gt("END_TIME", time);
        wrapper.lt("START_TIME", time);
        List<Ad> ads = adService.selectList(wrapper);
        result.put("adList", ads);
        return DataResponse.success(result);
    }

}
