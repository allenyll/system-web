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
 * 颜色分组表
 * 
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-03-21 10:29:56
 */
@Data
@TableName("t_snu_color_group")
public class ColorGroup extends Entity<ColorGroup>  {

	private static final long serialVersionUID = 1L;

	// 分组主键
    @TableId(type = IdType.UUID)
    private String pkColorGroupId;

	// 分组名称
    private String colorGroupName;

	// 分组编码
    private String colorCode;

	@Override
    protected Serializable pkVal() {
		return pkColorGroupId;
	}



}
