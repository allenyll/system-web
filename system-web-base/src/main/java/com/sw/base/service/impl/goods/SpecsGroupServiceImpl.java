package com.sw.base.service.impl.goods;

import org.springframework.stereotype.Service;

import com.sw.common.entity.goods.SpecsGroup;
import com.sw.base.mapper.goods.SpecsGroupMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 规格组
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-05-13 16:05:43
 */
@Service("specsGroupService")
public class SpecsGroupServiceImpl extends BaseService<SpecsGroupMapper,SpecsGroup> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecsGroupServiceImpl.class);
}