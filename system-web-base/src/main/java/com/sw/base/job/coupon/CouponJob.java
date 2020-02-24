package com.sw.base.job.coupon;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.base.service.impl.market.CouponDetailServiceImpl;
import com.sw.base.service.impl.market.CouponServiceImpl;
import com.sw.common.constants.dict.CouponDict;
import com.sw.common.entity.market.Coupon;
import com.sw.common.entity.market.CouponDetail;
import com.sw.common.util.CollectionUtil;
import com.sw.common.util.DateUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:  优惠券过期
 * @Author:       allenyll
 * @Date:         2019-07-15 12:07
 * @Version:      1.0
 */
@Component
public class CouponJob implements Job {

    @Autowired
    CouponServiceImpl couponService;

    @Autowired
    CouponDetailServiceImpl couponDetailService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        EntityWrapper<Coupon> couponEntityWrapper = new EntityWrapper<>();
        couponEntityWrapper.eq("is_delete", 0);
        List<Coupon> list = couponService.selectList(couponEntityWrapper);
        if(CollectionUtil.isNotEmpty(list)){
            for(Coupon coupon:list){
                String time = DateUtil.getCurrentDate();
                // 优惠券已过期
                if(time.compareTo(coupon.getEndTime()) > 0){
                    EntityWrapper<CouponDetail> couponDetailEntityWrapper = new EntityWrapper<>();
                    couponDetailEntityWrapper.eq("IS_DELETE", 0);
                    couponDetailEntityWrapper.eq("FK_COUPON_ID", coupon.getPkCouponId());
                    List<CouponDetail> couponDetails = couponDetailService.selectList(couponDetailEntityWrapper);
                    if(CollectionUtil.isNotEmpty(couponDetails)){
                        for(CouponDetail couponDetail:couponDetails){
                            if(couponDetail.getUseStatus().equals(CouponDict.UN_USE.getCode())){
                                couponDetail.setUseStatus(CouponDict.EXPIRE.getCode());
                                couponDetailService.updateById(couponDetail);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        String time = "2019-07-15";
        String time1 = "2019-07-06";
        System.out.println(time.compareTo(time1) > 0);
    }
}
