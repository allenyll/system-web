package com.sw.base.service.impl.goods;

import org.springframework.stereotype.Service;

import com.sw.common.entity.goods.Specs;
import com.sw.base.mapper.goods.SpecsMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 规格表
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-05-13 16:08:41
 */
@Service("specsService")
public class SpecsServiceImpl extends BaseService<SpecsMapper,Specs> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecsServiceImpl.class);
}