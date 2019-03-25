package com.sw.base.service.impl.goods;

import org.springframework.stereotype.Service;

import com.sw.common.entity.goods.Category;
import com.sw.base.mapper.goods.CategoryMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 商品分类
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-03-21 10:51:04
 */
@Service("categoryService")
public class CategoryServiceImpl extends BaseService<CategoryMapper,Category> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);
}