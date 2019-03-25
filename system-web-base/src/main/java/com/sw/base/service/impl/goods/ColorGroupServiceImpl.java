package com.sw.base.service.impl.goods;

import org.springframework.stereotype.Service;

import com.sw.common.entity.goods.ColorGroup;
import com.sw.base.mapper.goods.ColorGroupMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 颜色分组表
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-03-21 10:29:56
 */
@Service("colorGroupService")
public class ColorGroupServiceImpl extends BaseService<ColorGroupMapper,ColorGroup> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ColorGroupServiceImpl.class);
}