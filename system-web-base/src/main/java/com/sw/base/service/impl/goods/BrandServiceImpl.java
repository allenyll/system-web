package com.sw.base.service.impl.goods;

import org.springframework.stereotype.Service;

import com.sw.common.entity.goods.Brand;
import com.sw.base.mapper.goods.BrandMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 商品品牌
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-03-21 10:04:09
 */
@Service("brandService")
public class BrandServiceImpl extends BaseService<BrandMapper,Brand> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrandServiceImpl.class);
}