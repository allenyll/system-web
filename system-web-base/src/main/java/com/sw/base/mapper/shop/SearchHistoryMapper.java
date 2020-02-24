package com.sw.base.mapper.shop;

import com.sw.common.entity.shop.SearchHistory;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-12-27 14:46:25
 */
public interface SearchHistoryMapper extends BaseMapper<SearchHistory> {

    List<Map<String, String>> selectHistoryKeywordList(Map<String, Object> params);

    int updateByCustomerId(Map<String, Object> params);
}
