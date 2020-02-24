package com.sw.base.mapper.market;

import com.sw.common.entity.market.Coupon;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * snu_coupon
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-06-28 09:13:54
 */
public interface CouponMapper extends BaseMapper<Coupon> {

    void addCouponGoods(Map<String, Object> map);

    List<Map<String, Object>> selectCouponGoods(String pkCouponId);

    List<Map<String, Object>> getCouponList(Map<String, Object> params);
}
