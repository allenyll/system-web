package com.sw.common.entity.system;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.sw.common.entity.Entity;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 字典表

 * </p>
 *
 * @author yu.leilei
 * @since 2019-01-29
 */
@TableName("sys_dict")
@Data
@ToString
public class Dict extends Entity<Dict> {

    private static final long serialVersionUID = 1L;

    /**
     * 字典主键
     */
    @TableId(type = IdType.UUID)
	private String pkDictId;
    /**
     * 名称
     */
	private String name;
    /**
     * 编码
     */
	private String code;
    /**
     * 父字典
     */
	private String parentId;
    /**
     * 说明
     */
	private String description;
    /**
     * 是否有效
     */
	private String status;

	@Override
	protected Serializable pkVal() {
		return pkDictId;
	}
}
