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
 * 属性选项表,也就是属性的详情
 * 
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-05-12 17:55:00
 */
@Data
@TableName("snu_attr_option")
public class AttrOption extends Entity<AttrOption>  {

	private static final long serialVersionUID = 1L;

	// 
    @TableId(type = IdType.UUID)
    private String pkAttrOptionId;

	// 属性Id
    private String fkAttributeId;

	// 名称
    private String optionName;

	@Override
    protected Serializable pkVal() {
		return pkAttrOptionId;
	}



}
