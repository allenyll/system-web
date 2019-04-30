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
 * 会员余额明细表
 * 
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-04-10 16:24:29
 */
@Data
@TableName("t_snu_customer_balance_detail")
public class CustomerBalanceDetail extends Entity<CustomerBalanceDetail>  {

	private static final long serialVersionUID = 1L;

	// 
    @TableId(type = IdType.UUID)
    private String pkBalanceDetail;

	// 
    private String fkCustomerId;

	// 使用金额
    private BigDecimal balance;

    private String type;

	// 使用时间
    private String time;

	// 使用状态
    private String status;

    private String remark;

	// 充值来源
    private String channelId;

	@Override
    protected Serializable pkVal() {
		return pkBalanceDetail;
	}



}
