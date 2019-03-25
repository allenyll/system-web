package com.sw.common.entity.goods;

import lombok.Data;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.sw.common.entity.Entity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 商品品牌
 * 
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-03-21 10:04:09
 */
@Data
@TableName("t_snu_brand")
public class Brand extends Entity<Brand>  {

	private static final long serialVersionUID = 1L;

	// 品牌主键
    @TableId(type = IdType.UUID)
    private String pkBrandId;

	// 
    private String brandName;

	// 品牌编码
    private String brandNo;

	// 品牌类型
    private String brandType;

	@Override
    protected Serializable pkVal() {
		return pkBrandId;
	}



}
