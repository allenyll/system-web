package com.sw.common.entity.customer;

import java.io.Serializable;

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
@Data
@ToString
@TableName("t_snu_customer_point_detail")
public class CustomerPointDetail extends Entity<CustomerPointDetail> {

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

	@Override
	protected Serializable pkVal() {
		return pkPointDetailId;
	}
}
