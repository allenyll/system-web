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
 * 商品分类
 * 
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-03-21 10:51:04
 */
@Data
@TableName("t_snu_category")
public class Category extends Entity<Category>  {

	private static final long serialVersionUID = 1L;

	// 分类主键
    @TableId(type = IdType.UUID)
    private String pkCategoryId;

	// 分类编码
    private String categoryNo;

	// 分类名称
    private String categoryName;

	// 父级id
    private String parentId;

    // 父级节点名称
	@TableField(exist = false)
	private String parentCategoryName;

	// 层级
    private Integer categoryLevel;

	// 描述
    private String description;

	// 顺序
    private Integer categorySeq;

	// 是否最有一层
    private String isEnd;

	// 是否启用
    private String isUsed;

	@Override
    protected Serializable pkVal() {
		return pkCategoryId;
	}



}
