package com.sw.common.entity.goods;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 商品满减
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-05-28 17:24:36
 */
@Data
@TableName("snu_goods_full_reduce")
public class GoodsFullReduce extends Model<GoodsFullReduce> {

	private static final long serialVersionUID = 1L;

	// 满减主键
    @TableId(type = IdType.UUID)
    private String pkReduceId;

	// 商品主键
    private String fkGoodsId;

	// 满足价格
    private BigDecimal fullPrice;

	// 减去价格
    private BigDecimal reducePrice;

    @TableField(exist = false)
    private boolean isDefault;

	@Override
    protected Serializable pkVal() {
		return pkReduceId;
	}



}
