package com.sw.common.entity.goods;

import com.google.common.collect.Maps;
import lombok.Data;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.sw.common.entity.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import javax.persistence.*;


/**
 * 商品库存单位
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-05-28 16:22:46
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
    private String skuCode;

	// 条形码
    private String skuBarCode;

	// 数量
    private Integer skuNum;

	// 规格值 [id,value];[id,value]
    private String specValue;

	// 库存
    private Integer skuStock;

	// 告警库存
    private Integer warnStock;

	// 价格
    private BigDecimal skuPrice;

	// 状态
    private String skuStatus;

	// 规格图
    private String picUrl;

	public Map<String, Object> toMap () {
		Map<String, Object> map = Maps.newHashMap();
		map.put("pkSkuId", pkSkuId);
		map.put("fkGoodsId", fkGoodsId);
		map.put("skuName", skuName);
		map.put("skuCode", skuCode);
		map.put("skuBarCode", skuBarCode);
		map.put("skuNum", skuNum);
		map.put("specValue", specValue);
		map.put("skuStock", skuStock);
		map.put("warnStock", warnStock);
		map.put("skuPrice", skuPrice);
		map.put("skuStatus", skuStatus);
		map.put("picUrl", picUrl);
		return map;
	}

	@Override
    protected Serializable pkVal() {
		return pkSkuId;
	}



}
