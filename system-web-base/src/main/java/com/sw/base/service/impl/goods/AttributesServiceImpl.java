package com.sw.base.service.impl.goods;

import com.sw.base.mapper.goods.AttributesMapper;
import com.sw.common.entity.goods.Attributes;
import com.sw.common.service.BaseService;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 属性值
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-05-12 17:47:06
 */
@Service("attributesService")
public class AttributesServiceImpl extends BaseService<AttributesMapper, Attributes> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttributesServiceImpl.class);
}