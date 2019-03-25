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
 * 商品基本信息表
 * 
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-03-21 10:51:24
 */
@Data
@TableName("t_snu_goods")
public class Goods extends Entity<Goods>  {

	private static final long serialVersionUID = 1L;

	// 商品主键
    @TableId(type = IdType.UUID)
    private String pkGoodsId;

	// 品牌主键
    private String fkBrandId;

	// 分类主键
    private String fkCategoryId;

	// 颜色主键
    private String fkColorId;

	// 单位主键
    private String fkUnitId;

	// 尺码主键
    private String fkSizeId;

	// 商品名称
    private String goodsName;

	// 商品编码
    private String goodsCode;

	// 商品条形码
    private String goodsBarCode;

	// 商品标签
    private String goodsLabel;

	// 商品价格
    private BigDecimal price;

	// 市场价
    private BigDecimal marketPrice;

	// 成本价
    private BigDecimal costPrice;

	// 库存量
    private Integer stock;

	// 告警库存
    private Integer warningStock;

	// 商品积分
    private Integer goodsIntegral;

	// 商品图片
    private String goodsUrl;

	// 商品详情
    private String goodsDesc;

	// 季节性
    private String season;

	// 单位
    private String unit;

	// 商品排序
    private Integer goodsSeq;

	// 是否启用 SW1301 未启用 SW1302 启用
    private String isUsed;

	// 商品状态 SW1401 上架 SW1402 下架 SW1403 预售
    private String status;

	// 备注
    private String remark;

	@Override
    protected Serializable pkVal() {
		return pkGoodsId;
	}



}
