package com.sw.base.service.impl.market;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.base.service.impl.customer.CustomerServiceImpl;
import com.sw.cache.util.DataResponse;
import com.sw.common.constants.dict.CouponDict;
import com.sw.common.entity.customer.Customer;
import com.sw.common.entity.market.CouponDetail;
import com.sw.common.util.CollectionUtil;
import com.sw.common.util.DateUtil;
import com.sw.common.util.MapUtil;
import com.sw.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sw.common.entity.market.Coupon;
import com.sw.base.mapper.market.CouponMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * snu_coupon
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-06-28 09:13:54
 */
@Service("couponService")
public class CouponServiceImpl extends BaseService<CouponMapper,Coupon> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouponServiceImpl.class);

    @Autowired
    CouponMapper couponMapper;

    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    CouponDetailServiceImpl couponDetailService;

    public void addCouponGoods(Map<String, Object> map) {
        couponMapper.addCouponGoods(map);
    }

    public List<Map<String, Object>> selectCouponGoods(String pkCouponId) {
        return couponMapper.selectCouponGoods(pkCouponId);
    }

    public DataResponse publishCoupon(Map<String, Object> params) {
        String customerId = MapUtil.getString(params, "customerId");
        if (StringUtil.isEmpty(customerId)){
            return DataResponse.fail("发放用户不能为空，请选择！");
        }
        String couponId = MapUtil.getString(params, "couponId");
        if (StringUtil.isEmpty(couponId)) {
            return DataResponse.fail("发放优惠券不能为空，请选择！");
        }

        EntityWrapper<Customer> customerEntityWrapper = new EntityWrapper<>();
        customerEntityWrapper.eq("IS_DELETE", 0);
        customerEntityWrapper.eq("PK_CUSTOMER_ID", customerId);
        Customer customer = customerService.selectOne(customerEntityWrapper);
        if (customer == null) {
            return DataResponse.fail("用户不存在，请选择！");
        }

        EntityWrapper<Coupon> couponEntityWrapper = new EntityWrapper<>();
        couponEntityWrapper.eq("IS_DELETE", 0);
        couponEntityWrapper.eq("PK_COUPON_ID", couponId);
        List<Coupon> coupon = couponMapper.selectList(couponEntityWrapper);
        if (CollectionUtil.isEmpty(coupon)) {
            return DataResponse.fail("优惠券已失效，请选择！");
        }

        CouponDetail couponDetail = new CouponDetail();
        couponDetail.setCouponCode(coupon.get(0).getCode());
        couponDetail.setNickName(customer.getNickName());
        couponDetail.setFkCouponId(couponId);
        couponDetail.setFkCustomerId(customerId);
        couponDetail.setGetType(CouponDict.BACKSTAGE_GIFT.getCode());
        couponDetail.setUseStatus(CouponDict.UN_USE.getCode());
        couponDetail.setIsDelete(0);
        couponDetail.setAddTime(DateUtil.getCurrentDateTime());

        try {
            couponDetailService.insert(couponDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return DataResponse.fail(e.getMessage());
        }

        return DataResponse.success();
    }

    public DataResponse getCouponList(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        String customerId = MapUtil.getString(params, "customerId");
        if (StringUtil.isEmpty(customerId)){
            return DataResponse.fail("用户不能为空，请选择！");
        }

        params.put("time", DateUtil.getCurrentDate());

        List<Map<String, Object>> list = couponMapper.getCouponList(params);

        result.put("list", list);

        return DataResponse.success(result);
    }
}
