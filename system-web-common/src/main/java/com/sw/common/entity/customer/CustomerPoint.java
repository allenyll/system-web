package com.sw.common.entity.customer;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.sw.common.entity.Entity;
import lombok.Data;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author yu.leilei
 * @since 2019-01-09
 */
@TableName("t_snu_customer_point")
@ToString
@Data
public class CustomerPoint extends Entity<CustomerPoint> {

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
	 * 用户名称
	 */
	@TableField(exist = false)
	private String customerName;

	/**
	 * 用户账户
	 */
	@TableField(exist = false)
	private String customerAccount;

    /**
     * 积分
     */
	private Integer point;
    /**
     * 使用积分
     */
	private Integer used;

	@Override
	protected Serializable pkVal() {
		return this.pkPointId;
	}

}
