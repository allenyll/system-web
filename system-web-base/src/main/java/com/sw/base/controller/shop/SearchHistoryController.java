package com.sw.base.controller.shop;

import com.sw.cache.util.DataResponse;
import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.shop.SearchHistoryServiceImpl;
import com.sw.common.entity.shop.SearchHistory;
import com.sw.common.util.DateUtil;
import com.sw.common.util.MapUtil;
import com.sw.common.util.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/system-web/searchHistory")
public class SearchHistoryController extends BaseController<SearchHistoryServiceImpl,SearchHistory> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchHistoryController.class);

    @Autowired
    SearchHistoryServiceImpl searchHistoryService;

    @ResponseBody
    @RequestMapping(value = "clearHistoryKeyword", method = RequestMethod.POST)
    public DataResponse clearHistoryKeyword(@RequestBody Map<String, Object> params){
        Map<String, Object> result = new HashedMap();
        String customerId = MapUtil.getString(params, "userId");
        if (StringUtil.isEmpty(customerId)) {
            return DataResponse.fail("关联用户为空，无法查询");
        }
        params.put("time", DateUtil.getCurrentDateTime());

        int num = searchHistoryService.updateByCustomerId(params);

        return DataResponse.success(result);
    }

}
