package com.sw.common.entity.system;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.sw.common.entity.Entity;
import lombok.Data;

import java.io.Serializable;


/**
 * <p>
 * 管理部门
 * </p>
 *
 * @author yu.leilei
 * @since 2018-11-20
 */
@TableName("sys_depot")
@Data
public class Depot extends Entity<Depot> {

	private static final long serialVersionUID = 1L;

	/**
	 * 部门主键ID
	 */
	@TableId(type = IdType.UUID)
	private String pkDepotId;
	/**
	 * 父级部门ID
	 */
	private String parentDepotId;

	@TableField(exist = false)
	private String parentDepotName;
	/**
	 * 部门名称
	 */
	private String depotName;
	/**
	 * 部门编码
	 */
	private String depotCode;
	/**
	 * 排序
	 */
	private Integer sort;

	@Override
	protected Serializable pkVal() {
		return this.pkDepotId;
	}

}
