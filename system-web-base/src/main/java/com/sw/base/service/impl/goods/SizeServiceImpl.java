package com.sw.base.service.impl.goods;

import org.springframework.stereotype.Service;

import com.sw.common.entity.goods.Size;
import com.sw.base.mapper.goods.SizeMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 尺码表
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-03-21 10:50:24
 */
@Service("sizeService")
public class SizeServiceImpl extends BaseService<SizeMapper,Size> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SizeServiceImpl.class);
}