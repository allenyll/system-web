package com.sw.common.entity.order;

import lombok.Data;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.sw.common.entity.Entity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 记录订单操作日志
 * 
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-07-16 16:34:06
 */
@Data
@TableName("snu_order_operate_log")
public class OrderOperateLog extends Entity<OrderOperateLog>  {

	private static final long serialVersionUID = 1L;

	// 操作日志主键
    @TableId(type = IdType.UUID)
    private String pkOperateId;

	// 订单ID
    private String fkOrderId;

	// 订单状态
    private String orderStatus;

	// 
    private String remark;

	@Override
    protected Serializable pkVal() {
		return pkOperateId;
	}



}
