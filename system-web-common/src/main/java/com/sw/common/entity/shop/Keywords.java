package com.sw.common.entity.shop;

import lombok.Data;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.sw.common.entity.Entity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 热闹关键词表
 * 
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-12-27 14:46:03
 */
@Data
@TableName("snu_keywords")
public class Keywords extends Entity<Keywords>  {

	private static final long serialVersionUID = 1L;

	// 主键
    @TableId(type = IdType.UUID)
    private String pkKeywordsId;

	// 关键字
    private String keyword;

	// 热销
    private String isHot;

	// 默认
    private String isDefault;

	// 显示
    private String isShow;

	// 排序
    private Integer sortOrder;

	// 关键词的跳转链接
    private String schemeUrl;

	// 类型
    private String type;

	@Override
    protected Serializable pkVal() {
		return pkKeywordsId;
	}



}
