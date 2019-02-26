package com.sw.common.entity.customer;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 
 * </p>
 *
 * @author yu.leilei
 * @since 2019-01-09
 */
@TableName("t_snu_customer_point_detail")
public class CustomerPointDetail extends Model<CustomerPointDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 积分明细表
     */
    @TableId(type = IdType.UUID)
	private String pkPointDetailId;
    /**
     * 用户id
     */
	private String fkCustomerId;

	/**
	 * 积分数值
	 */
	private Integer point;

	/**
	 * 积分类型
	 */
	private String type;

	/**
	 * 过期时间
	 */
	private String expiredTime;

	/**
	 * 获得时间
	 */
	private String getTime;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 是否有效
	 */
	private Integer isDeleted;


	public String getPkPointDetailId() {
		return pkPointDetailId;
	}

	public void setPkPointDetailId(String pkPointDetailId) {
		this.pkPointDetailId = pkPointDetailId;
	}

	public String getFkCustomerId() {
		return fkCustomerId;
	}

	public void setFkCustomerId(String fkCustomerId) {
		this.fkCustomerId = fkCustomerId;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public String getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(String expiredTime) {
		this.expiredTime = expiredTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	protected Serializable pkVal() {
		return this.pkPointDetailId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGetTime() {
		return getTime;
	}

	public void setGetTime(String getTime) {
		this.getTime = getTime;
	}

	@Override
	public String toString() {
		return "CustomerPointDetail{" +
			"pkPointDetailId=" + pkPointDetailId +
			", fkCustomerId=" + fkCustomerId +
			", point=" + point +
			", expiredTime=" + expiredTime +
			", remark=" + remark +
			", isDeleted=" + isDeleted +
			"}";
	}
}
