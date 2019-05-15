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
 * 规格表
 * 
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-05-13 16:08:41
 */
@Data
@TableName("snu_specs")
public class Specs extends Entity<Specs>  {

	private static final long serialVersionUID = 1L;

	// 
    @TableId(type = IdType.UUID)
    private String pkSpecsId;

	// 规格主键
    private String fkSpecsGroupId;

	// 分类主键
    private String fkCategoryId;

	// 名称
    private String specsName;

	// 类型
    private String specsType;

	// 值
    private String specsVal;

	// 排序
    private Integer specsSeq;

	// 是否显示
    private String isShow;

	// 
    private String status;

	@TableField(exist = false)
	private String[] categoryIds;

	@Override
    protected Serializable pkVal() {
		return pkSpecsId;
	}



}
