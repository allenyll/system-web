package com.sw.base.service.impl.goods;

import org.springframework.stereotype.Service;

import com.sw.common.entity.goods.GoodsAppraises;
import com.sw.base.mapper.goods.GoodsAppraisesMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 商品评价表
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-05-09 15:16:31
 */
@Service("goodsAppraisesService")
public class GoodsAppraisesServiceImpl extends BaseService<GoodsAppraisesMapper,GoodsAppraises> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsAppraisesServiceImpl.class);
}