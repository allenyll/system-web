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
 * 购物车明细
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-06-24 10:22:55
 */
@Data
@TableName("snu_cart")
public class Cart extends Entity<Cart>  {

	private static final long serialVersionUID = 1L;

	// 主键ID
    @TableId(type = IdType.UUID)
    private String pkCartId;

	// 商品ID
    private String fkGoodsId;

	// SKU ID
    private String fkSkuId;

	// 会员ID
    private String fkCustomerId;

	// 数量
    private Integer quantity;

	// 价格
    private BigDecimal price;

	//
    private String specValue;

	@Override
    protected Serializable pkVal() {
		return pkCartId;
	}



}
