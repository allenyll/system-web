package com.sw.common.entity.order;

import lombok.Data;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.sw.common.entity.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * 订单基础信息表
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-06-26 21:11:07
 */
@Data
@TableName("snu_order")
public class Order extends Entity<Order>  {

	private static final long serialVersionUID = 1L;

	// 订单主键
    @TableId(type = IdType.UUID)
    private String pkOrderId;

	// 订单编码
    private String orderNo;

	// 用户ID
    private String fkCustomerId;

	// 订单类型   SW0601 线上  SW0602 线下
    private String orderType;

	// 订单状态 SW0701 未付款 SW0702 已付款  SW0703 已收货 SW0704 已评价 SW0705 已完成 SW0706 已取消
    private String orderStatus;

	// 售后状态 SW0801 未发起售后 SW0802 申请售后 SW0803 取消售后 SW0804 售后处理中 SW0805 处理完成
    private String afterStatus;

	// 商品数量
    private Integer goodsCount;

	// 订单总价
    private BigDecimal orderAmount;

	// 支付金额
    private BigDecimal payAmount;

	// 最终金额
    private BigDecimal totalAmount;

	// 运费金额
    private BigDecimal logisticsFee;

	// 收货地址
    private String fkAddressId;

	// 支付渠道 SW0901 余额 SW0902 微信 SW0903 支付宝 SW0904 银联
    private String payChannel;

	// 订单支付单号
    private String tradeNo;

	// 第三方支付流水
    private String escrowTradeNo;

	// 支付时间
    private String payTime;

	// 订单创建时间
    private String orderTime;

	// 发货时间
    private String deliveryTime;

	// 确认状态
    private String confirmStatus;

	// 收货时间
    private String receiveTime;

	// 评论时间
    private String commentTime;

	// 订单结算状态 SW1101 未结算 SW1102 已结算
    private String settlementStatus;

	// 订单结算时间
    private String settlementTime;

	// 优惠券
    private String fkCouponId;

	// 促销优化金额（促销价、满减、阶梯价）
    private BigDecimal promotionAmount;

	// 积分抵扣金额
    private BigDecimal integrationAmount;

	// 优惠券抵扣金额
    private BigDecimal couponAmount;

	// 管理员后台调整订单使用的折扣金额
    private BigDecimal discountAmount;

	// 自动确认时间（天）
    private Integer autoConfirmDay;

	// 可以获得的积分
    private Integer integration;

	// 可以获得的成长值
    private Integer growth;

	// 活动信息
    private String promotionInfo;

	// 发票类型：0->不开发票；1->电子发票；2->纸质发票
    private String billType;

	// 发票抬头
    private String billHeader;

	// 发票内容
    private String billContent;

	// 收票人电话
    private String billReceiverPhone;

	// 收票人邮箱
    private String billReceiverEmail;

	// 是否积分产品
    private String isIntegral;

	// 订单备注
    private String orderRemark;

	@Override
    protected Serializable pkVal() {
		return pkOrderId;
	}



}
