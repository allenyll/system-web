package com.sw.common.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

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
public class SysDepot extends Model<SysDepot> {

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
    /**
     * 是否删除
     */
	private Integer isDelete;
    /**
     * 创建人
     */
	private String addUser;
    /**
     * 创建时间
     */
	private String addTime;
    /**
     * 更新人
     */
	private String updateUser;
    /**
     * 更新时间
     */
	private String updateTime;


	@Override
	protected Serializable pkVal() {
		return this.pkDepotId;
	}

	@Override
	public String toString() {
		return "SysDepot{" +
			"pkDepotId=" + pkDepotId +
			", parentDepotId=" + parentDepotId +
			", depotName=" + depotName +
			", depotCode=" + depotCode +
			", sort=" + sort +
			", isDelete=" + isDelete +
			", addUser=" + addUser +
			", addTime=" + addTime +
			", updateUser=" + updateUser +
			", updateTime=" + updateTime +
			"}";
	}
}
