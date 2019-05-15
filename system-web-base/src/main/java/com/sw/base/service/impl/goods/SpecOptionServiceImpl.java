package com.sw.base.service.impl.goods;

import org.springframework.stereotype.Service;

import com.sw.common.entity.goods.SpecOption;
import com.sw.base.mapper.goods.SpecOptionMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 规格选项
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-05-13 16:09:07
 */
@Service("specOptionService")
public class SpecOptionServiceImpl extends BaseService<SpecOptionMapper,SpecOption> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecOptionServiceImpl.class);
}