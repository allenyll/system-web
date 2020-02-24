package com.sw.base.service.impl.market;

import org.springframework.stereotype.Service;

import com.sw.common.entity.market.CouponDetail;
import com.sw.base.mapper.market.CouponDetailMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 优惠券领取详情表
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-06-29 22:31:34
 */
@Service("couponDetailService")
public class CouponDetailServiceImpl extends BaseService<CouponDetailMapper,CouponDetail> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouponDetailServiceImpl.class);
}