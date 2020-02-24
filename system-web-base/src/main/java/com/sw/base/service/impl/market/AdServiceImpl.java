package com.sw.base.service.impl.market;

import org.springframework.stereotype.Service;

import com.sw.common.entity.market.Ad;
import com.sw.base.mapper.market.AdMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-12-19 20:14:24
 */
@Service("adService")
public class AdServiceImpl extends BaseService<AdMapper,Ad> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdServiceImpl.class);
}