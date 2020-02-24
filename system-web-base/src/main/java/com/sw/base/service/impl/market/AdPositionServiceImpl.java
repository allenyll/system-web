package com.sw.base.service.impl.market;

import org.springframework.stereotype.Service;

import com.sw.common.entity.market.AdPosition;
import com.sw.base.mapper.market.AdPositionMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 广告位表
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-12-19 20:12:58
 */
@Service("adPositionService")
public class AdPositionServiceImpl extends BaseService<AdPositionMapper,AdPosition> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdPositionServiceImpl.class);
}