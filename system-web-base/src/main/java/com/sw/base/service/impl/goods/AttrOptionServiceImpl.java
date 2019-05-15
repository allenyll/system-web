package com.sw.base.service.impl.goods;

import org.springframework.stereotype.Service;

import com.sw.common.entity.goods.AttrOption;
import com.sw.base.mapper.goods.AttrOptionMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 属性选项表,也就是属性的详情
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-05-12 17:55:00
 */
@Service("attrOptionService")
public class AttrOptionServiceImpl extends BaseService<AttrOptionMapper,AttrOption> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttrOptionServiceImpl.class);
}