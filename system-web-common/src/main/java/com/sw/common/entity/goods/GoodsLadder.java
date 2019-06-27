package com.sw.common.entity.goods;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.sw.common.entity.Entity;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 商品阶梯价格关联
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-05-28 17:24:48
 */
@Data
@TableName("snu_goods_ladder")
public class GoodsLadder extends Model<GoodsLadder> {

	private static final long serialVersionUID = 1L;

	// 主键ID
    @TableId(type = IdType.UUID)
    private String pkLadderId;

	// 商品主键
    private String fkGoodsId;

	// 数量
    private BigDecimal count;

	// 折扣
    private BigDecimal discount;

	// 价格
    private BigDecimal price;

	@TableField(exist = false)
	private boolean isDefault;

	@Override
    protected Serializable pkVal() {
		return pkLadderId;
	}



}
