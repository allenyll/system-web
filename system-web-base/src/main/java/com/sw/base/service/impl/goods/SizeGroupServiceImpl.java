package com.sw.base.service.impl.goods;

import org.springframework.stereotype.Service;

import com.sw.common.entity.goods.SizeGroup;
import com.sw.base.mapper.goods.SizeGroupMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 尺码分组表
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-03-21 10:46:28
 */
@Service("sizeGroupService")
public class SizeGroupServiceImpl extends BaseService<SizeGroupMapper,SizeGroup> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SizeGroupServiceImpl.class);
}