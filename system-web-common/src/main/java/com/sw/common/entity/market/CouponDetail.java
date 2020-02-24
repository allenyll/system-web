package com.sw.common.entity.market;

import lombok.Data;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.sw.common.entity.Entity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 优惠券领取详情表
 * 
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-06-29 22:31:34
 */
@Data
@TableName("snu_coupon_detail")
public class CouponDetail extends Entity<CouponDetail>  {

	private static final long serialVersionUID = 1L;

	// 主键ID
    @TableId(type = IdType.UUID)
    private String pkDetailId;

	// 优惠券ID
    private String fkCouponId;

	// 会员ID
    private String fkCustomerId;

	// 优惠券编码
    private String couponCode;

	// 会员名称
    private String nickName;

	// 获取方式
    private String getType;

	// 使用状态
    private String useStatus;

	// 使用时间
    private String useTime;

	// 订单主键
    private String orderId;

	// 订单编码
    private String orderNo;

	@Override
    protected Serializable pkVal() {
		return pkDetailId;
	}



}
