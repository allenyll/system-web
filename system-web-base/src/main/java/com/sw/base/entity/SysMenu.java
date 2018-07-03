package com.sw.base.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 菜单管理
 * </p>
 *
 * @author yu.leilei
 * @since 2018-06-12
 */
@TableName("sys_menu")
public class SysMenu extends Model<SysMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单主键
     */
	private String pkMenuId;
    /**
     * 菜单名称
     */
	private String parentMenuId;
    /**
     * 菜单名称
     */
	private String menuName;
    /**
     * 菜单跳转地址
     */
	private String menuUrl;
    /**
     * 菜单权限
     */
	private String menuPerms;
    /**
     * 菜单类型
     */
	private String menuType;
    /**
     * 菜单图标
     */
	private String menuIcon;
    /**
     * 排序
     */
	private Integer sortNum;
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


	public String getPkMenuId() {
		return pkMenuId;
	}

	public void setPkMenuId(String pkMenuId) {
		this.pkMenuId = pkMenuId;
	}

	public String getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuPerms() {
		return menuPerms;
	}

	public void setMenuPerms(String menuPerms) {
		this.menuPerms = menuPerms;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
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
		return this.pkMenuId;
	}

	@Override
	public String toString() {
		return "SysMenu{" +
			"pkMenuId=" + pkMenuId +
			", parentMenuId=" + parentMenuId +
			", menuName=" + menuName +
			", menuUrl=" + menuUrl +
			", menuPerms=" + menuPerms +
			", menuType=" + menuType +
			", menuIcon=" + menuIcon +
			", sortNum=" + sortNum +
			", isDelete=" + isDelete +
			", addUser=" + addUser +
			", addTime=" + addTime +
			", updateUser=" + updateUser +
			", updateTime=" + updateTime +
			"}";
	}
}
