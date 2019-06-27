package com.sw.base.service.impl.goods;

import org.springframework.stereotype.Service;

import com.sw.common.entity.goods.GoodsLadder;
import com.sw.base.mapper.goods.GoodsLadderMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 商品阶梯价格关联
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-05-28 17:24:48
 */
@Service("goodsLadderService")
public class GoodsLadderServiceImpl extends BaseService<GoodsLadderMapper,GoodsLadder> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsLadderServiceImpl.class);
}