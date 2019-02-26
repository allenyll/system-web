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
@TableName("t_snu_customer_point")
public class CustomerPoint extends Model<CustomerPoint> {

    private static final long serialVersionUID = 1L;

    /**
     * 积分主键ID
     */
    @TableId(type = IdType.UUID)
	private String pkPointId;
    /**
     * 用户id
     */
	private String fkCustomerId;
    /**
     * 积分
     */
	private Integer point;
    /**
     * 使用积分
     */
	private Integer used;


	public String getPkPointId() {
		return pkPointId;
	}

	public void setPkPointId(String pkPointId) {
		this.pkPointId = pkPointId;
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

	public Integer getUsed() {
		return used;
	}

	public void setUsed(Integer used) {
		this.used = used;
	}

	@Override
	protected Serializable pkVal() {
		return this.pkPointId;
	}

	@Override
	public String toString() {
		return "CustomerPoint{" +
			"pkPointId=" + pkPointId +
			", fkCustomerId=" + fkCustomerId +
			", point=" + point +
			", used=" + used +
			"}";
	}
}
