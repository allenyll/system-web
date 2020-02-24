package com.sw.base.controller.shop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.base.service.impl.shop.SearchHistoryServiceImpl;
import com.sw.cache.util.DataResponse;
import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.shop.KeywordsServiceImpl;
import com.sw.common.entity.shop.Keywords;
import com.sw.common.util.CollectionUtil;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system-web/keywords")
public class KeywordsController extends BaseController<KeywordsServiceImpl,Keywords> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeywordsController.class);

    @Autowired
    KeywordsServiceImpl keywordsService;

    @Autowired
    SearchHistoryServiceImpl searchHistoryService;

    @ResponseBody
    @RequestMapping(value = "getSearchKeyword", method = RequestMethod.POST)
    public DataResponse getSearchKeyword(@RequestBody Map<String, Object> params){
        Map<String, Object> result = new HashedMap();
        EntityWrapper<Keywords> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("IS_DEFAULT", "SW1001");
        List<Keywords> isDefaultKeywords = keywordsService.selectList(wrapper);
        Keywords keywords = new Keywords();
        if (CollectionUtil.isNotEmpty(isDefaultKeywords)) {
            keywords = isDefaultKeywords.get(0);
        }

        List<Map<String, String>> isHotKeyWords = keywordsService.selectHotKeywordList();

        result.put("isDefaultKeyWords", keywords);
        result.put("isHotKeywords", isHotKeyWords);

        String customerId = MapUtil.getString(params, "userId");
        List<Map<String, String>> historyKeywords = null;

        if (StringUtil.isEmpty(customerId)) {
            historyKeywords = new ArrayList<>();
            result.put("historyKeywords", historyKeywords);
            return DataResponse.success(result);
        }

        historyKeywords = searchHistoryService.selectHistoryKeywordList(params);

        result.put("historyKeywords", historyKeywords);

        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "getKeywords", method = RequestMethod.POST)
    public DataResponse getKeywords(@RequestBody Map<String, Object> params){
        Map<String, Object> result = new HashedMap();
        String keyword = MapUtil.getString(params, "keyword");
        if (StringUtil.isEmpty(keyword)) {
            return DataResponse.fail("关键字为空，无法查询");
        }
        EntityWrapper<Keywords> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.like("KEYWORD", keyword);
        List<Keywords> keywords = keywordsService.selectList(wrapper);
        if (keywords != null && keywords.size() > 10) {
            keywords = keywords.subList(0, 10);
        }

        result.put("keywordList", keywords);

        return DataResponse.success(result);
    }

}
