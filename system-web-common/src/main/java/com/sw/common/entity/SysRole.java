package com.sw.common.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author yu.leilei
 * @since 2018-11-13
 */
@Data
@TableName("sys_role")
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 权限主键
     */
	@TableId(type = IdType.UUID)
	private String pkRoleId;
    /**
     * 权限名称
     */
	private String roleName;
    /**
     * 权限标识
     */
	private String roleSign;
    /**
     * 备注
     */
	private String remark;
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


	public String getPkRoleId() {
		return pkRoleId;
	}

	public void setPkRoleId(String pkRoleId) {
		this.pkRoleId = pkRoleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleSign() {
		return roleSign;
	}

	public void setRoleSign(String roleSign) {
		this.roleSign = roleSign;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getAddUser() {
		return addUser;
	}

	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.pkRoleId;
	}

	@Override
	public String toString() {
		return "SysRole{" +
			"pkRoleId=" + pkRoleId +
			", roleName=" + roleName +
			", roleSign=" + roleSign +
			", remark=" + remark +
			", isDelete=" + isDelete +
			", addUser=" + addUser +
			", addTime=" + addTime +
			", updateUser=" + updateUser +
			", updateTime=" + updateTime +
			"}";
	}
}
