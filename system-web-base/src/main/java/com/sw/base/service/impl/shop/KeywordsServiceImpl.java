package com.sw.base.service.impl.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sw.common.entity.shop.Keywords;
import com.sw.base.mapper.shop.KeywordsMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 热闹关键词表
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-12-27 14:46:03
 */
@Service("keywordsService")
public class KeywordsServiceImpl extends BaseService<KeywordsMapper,Keywords> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeywordsServiceImpl.class);

    @Autowired
    KeywordsMapper keywordsMapper;

    public List<Map<String, String>> selectHotKeywordList() {
        return keywordsMapper.selectHotKeywordList();
    }
}
