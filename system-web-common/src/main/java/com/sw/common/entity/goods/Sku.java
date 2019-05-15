package com.sw.common.entity.goods;

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
 * 商品库存单位
 * 
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-05-11 22:07:42
 */
@Data
@TableName("snu_sku")
public class Sku extends Entity<Sku>  {

	private static final long serialVersionUID = 1L;

	// 主键id
    @TableId(type = IdType.UUID)
    private String pkSkuId;

	// 商品主键
    private String fkGoodsId;

	// 名称
    private String skuName;

	// 编码
    private String skuCodee;

	// 条形码
    private String skuBarCode;

	// 数量
    private Integer skuNum;

	// 库存
    private Integer skuStock;

	// 价格
    private BigDecimal skuPrice;

	// 状态
    private String skuStatus;

	@Override
    protected Serializable pkVal() {
		return pkSkuId;
	}



}
