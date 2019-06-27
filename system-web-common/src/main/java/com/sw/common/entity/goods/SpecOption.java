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
 * 规格选项
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-05-13 16:09:07
 */
@Data
@TableName("snu_spec_option")
public class SpecOption extends Entity<SpecOption>  {

	private static final long serialVersionUID = 1L;

	// 规格选项
    @TableId(type = IdType.UUID)
    private String pkSpecOptionId;

	//
    private String fkSpecsId;

	// 名称
    private String name;

	// 编码
    private String code;

	@Override
    protected Serializable pkVal() {
		return pkSpecOptionId;
	}



}
