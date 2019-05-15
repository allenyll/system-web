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
 * 尺码分组表
 * 
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-03-21 10:46:28
 */
@Data
@TableName("snu_size_group")
public class SizeGroup extends Entity<SizeGroup>  {

	private static final long serialVersionUID = 1L;

	// 分组主键
    @TableId(type = IdType.UUID)
    private String pkSizeGroupId;

	// 分组名称
    private String sizeGroupName;

	// 分组编码
    private String sizeCode;

	@Override
    protected Serializable pkVal() {
		return pkSizeGroupId;
	}



}
