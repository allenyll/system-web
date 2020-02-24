package com.sw.base.service.impl.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sw.common.entity.shop.SearchHistory;
import com.sw.base.mapper.shop.SearchHistoryMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-12-27 14:46:25
 */
@Service("searchHistoryService")
public class SearchHistoryServiceImpl extends BaseService<SearchHistoryMapper,SearchHistory> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchHistoryServiceImpl.class);

    @Autowired
    SearchHistoryMapper searchHistoryMapper;

    public List<Map<String, String>> selectHistoryKeywordList(Map<String, Object> params) {
        return searchHistoryMapper.selectHistoryKeywordList(params);
    }

    public int updateByCustomerId(Map<String, Object> params) {
        return searchHistoryMapper.updateByCustomerId(params);
    }
}
