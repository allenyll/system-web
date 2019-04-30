package com.sw.common.entity.customer;

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
 * 会员余额表
 * 
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-04-10 16:16:16
 */
@Data
@TableName("t_snu_customer_balance")
public class CustomerBalance extends Entity<CustomerBalance>  {

	private static final long serialVersionUID = 1L;

	// 用户余额ID
    @TableId(type = IdType.UUID)
    private String pkBalanceId;

	// 
    private String fkCustomerId;

	// 余额
    private BigDecimal balance;

	// 提现金额
    private BigDecimal withdrawCash;

	@Override
    protected Serializable pkVal() {
		return pkBalanceId;
	}



}
