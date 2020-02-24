package com.sw.common.entity.market;

import lombok.Data;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.sw.common.entity.Entity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 广告位表
 * 
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-12-19 20:12:58
 */
@Data
@TableName("snu_ad_position")
public class AdPosition extends Entity<AdPosition>  {

	private static final long serialVersionUID = 1L;

	// 主键ID
    @TableId(type = IdType.UUID)
    private String pkAdPositionId;

	// 名称
    private String name;

	// 长度
    private Integer width;

	// 宽度
    private Integer height;

	// 描述
    private String description;

	@Override
    protected Serializable pkVal() {
		return pkAdPositionId;
	}



}
