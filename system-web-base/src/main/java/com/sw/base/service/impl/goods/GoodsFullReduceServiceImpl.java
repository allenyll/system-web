package com.sw.base.service.impl.goods;

import org.springframework.stereotype.Service;

import com.sw.common.entity.goods.GoodsFullReduce;
import com.sw.base.mapper.goods.GoodsFullReduceMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 商品满减
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-05-28 17:24:36
 */
@Service("goodsFullReduceService")
public class GoodsFullReduceServiceImpl extends BaseService<GoodsFullReduceMapper,GoodsFullReduce> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsFullReduceServiceImpl.class);
}