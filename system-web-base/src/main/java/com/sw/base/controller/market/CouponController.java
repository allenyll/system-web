package com.sw.base.controller.market;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.base.service.impl.customer.CustomerServiceImpl;
import com.sw.cache.util.DataResponse;
import com.sw.common.constants.dict.CouponDict;
import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.market.CouponServiceImpl;
import com.sw.common.entity.Entity;
import com.sw.common.entity.customer.Customer;
import com.sw.common.entity.market.Coupon;
import com.sw.common.util.CollectionUtil;
import com.sw.common.util.DateUtil;
import com.sw.common.util.MapUtil;
import com.sw.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system-web/coupon")
public class CouponController extends BaseController<CouponServiceImpl,Coupon> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouponController.class);

    @Autowired
    CouponServiceImpl couponService;

    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public DataResponse add(@RequestBody Coupon coupon){
        LOGGER.info("coupon: " + coupon);
        String id = StringUtil.getUUID32();
        coupon.setPkCouponId(id);
        coupon.setReceiveCount(0);
        coupon.setUseCount(0);
        super.add(coupon);
        List<Map<String, Object>> list = coupon.getCouponGoodsList();
        if(CollectionUtil.isNotEmpty(list)){
            for(Map<String, Object> map:list){
                map.put("pkRelationId", StringUtil.getUUID32());
                map.put("fkCouponId", id);
                couponService.addCouponGoods(map);
            }
        }
        return DataResponse.success();
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public DataResponse get(@PathVariable String id){
        Map<String, Object> result = new HashMap<>();

        Coupon obj =  couponService.selectById(id);

        if(CouponDict.GOODS.getCode().equals(obj.getUseType())){
            List<Map<String, Object>> list = couponService.selectCouponGoods(obj.getPkCouponId());
            obj.setCouponGoodsList(list);
        }
        result.put("obj", obj);
        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "publishCoupon", method = RequestMethod.POST)
    public DataResponse publishCoupon(@RequestBody Map<String, Object> params){
        DataResponse dataResponse = couponService.publishCoupon(params);
        return dataResponse;
    }

    @ResponseBody
    @RequestMapping(value = "getCouponList", method = RequestMethod.POST)
    public DataResponse getCouponList(@RequestBody Map<String, Object> params){
        DataResponse dataResponse = couponService.getCouponList(params);
        return dataResponse;
    }

}
