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
 * 规格组
 * 
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-05-13 16:05:43
 */
@Data
@TableName("snu_specs_group")
public class SpecsGroup extends Entity<SpecsGroup>  {

	private static final long serialVersionUID = 1L;

	// 规格主键
    @TableId(type = IdType.UUID)
    private String pkSpecsGroupId;

	// 名称
    private String name;

	// 编码
    private String code;

	@Override
    protected Serializable pkVal() {
		return pkSpecsGroupId;
	}



}
