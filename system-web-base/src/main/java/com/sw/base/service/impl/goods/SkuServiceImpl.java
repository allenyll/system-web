package com.sw.base.service.impl.goods;

import org.springframework.stereotype.Service;

import com.sw.common.entity.goods.Sku;
import com.sw.base.mapper.goods.SkuMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 商品库存单位
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-05-11 22:07:42
 */
@Service("skuService")
public class SkuServiceImpl extends BaseService<SkuMapper,Sku> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SkuServiceImpl.class);
}