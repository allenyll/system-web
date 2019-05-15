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
 * 颜色配置表
 * 
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-03-21 10:34:48
 */
@Data
@TableName("snu_color")
public class Color extends Entity<Color>  {

	private static final long serialVersionUID = 1L;

	// 颜色主键
    @TableId(type = IdType.UUID)
    private String pkColorId;

	// 分组主键
    private String fkColorGroupId;

	// 名称
    private String colorName;

	// 编码
    private String colorCode;

	// 色值
    private String colorAsc;

	// 是否启用
    private String isUsed;

	// 是否默认
    private String isDefault;

	@Override
    protected Serializable pkVal() {
		return pkColorId;
	}



}
