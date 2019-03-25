package com.sw.base.service.impl.goods;

import org.springframework.stereotype.Service;

import com.sw.common.entity.goods.Goods;
import com.sw.base.mapper.goods.GoodsMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 商品基本信息表
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-03-21 10:51:24
 */
@Service("goodsService")
public class GoodsServiceImpl extends BaseService<GoodsMapper,Goods> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsServiceImpl.class);
}