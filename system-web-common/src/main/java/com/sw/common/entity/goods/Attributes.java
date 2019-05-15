package com.sw.common.entity.goods;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.sw.common.entity.Entity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 属性值
 * 
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-05-12 17:47:06
 */
@Data
@TableName("snu_attributes")
public class Attributes extends Entity<Attributes>  {

	private static final long serialVersionUID = 1L;

	// 属性Id
    @TableId(type = IdType.UUID)
    private String pkAttributeId;

	// 分类主键
    private String fkCategoryId;

	// 属性名称
    private String attrName;

	// 属性类型
    private String attType;

	// 属性值
    private String attrVal;

	// 排序
    private Integer attrSeq;

	// 是否显示
    private String isShow;

	// 
    private String status;

    @TableField(exist = false)
    private String[] categoryIds;

	@Override
    protected Serializable pkVal() {
		return pkAttributeId;
	}



}
