package com.sw.base.service.impl.goods;

import org.springframework.stereotype.Service;

import com.sw.common.entity.goods.Color;
import com.sw.base.mapper.goods.ColorMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 颜色配置表
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-03-21 10:34:48
 */
@Service("colorService")
public class ColorServiceImpl extends BaseService<ColorMapper,Color> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ColorServiceImpl.class);
}