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
 * 尺码表
 * 
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-03-21 10:50:24
 */
@Data
@TableName("t_snu_size")
public class Size extends Entity<Size>  {

	private static final long serialVersionUID = 1L;

	// 尺码主键
    @TableId(type = IdType.UUID)
    private String pkSizeId;

	// 分组主键
    private String fkSizeGroupId;

	// 名称
    private String sizeName;

	// 编码
    private String sizeCode;

	// 是否启用
    private String isUsed;

	// 是否默认
    private String isDefault;

	@Override
    protected Serializable pkVal() {
		return pkSizeId;
	}



}
