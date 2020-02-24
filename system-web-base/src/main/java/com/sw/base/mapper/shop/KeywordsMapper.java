package com.sw.base.mapper.shop;

import com.sw.common.entity.shop.Keywords;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 热闹关键词表
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-12-27 14:46:03
 */
public interface KeywordsMapper extends BaseMapper<Keywords> {

    List<Map<String, String>> selectHotKeywordList();
}
