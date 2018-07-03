package com.sw.base.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author yu.leilei
 * @since 2018-06-12
 */
@TableName("sys_user")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户主键
     */
	@TableId(type = IdType.UUID)
	private String pkUserId;
    /**
     * 组织ID
     */
	private String fkDepotId;
    /**
     * 名称
     */
	private String userName;
    /**
     * 账号
     */
	private String account;
    /**
     * 密码
     */
	private String password;
    /**
     * 密码盐
     */
	private String salt;
	/**
	 * 会员状态
	 */
	private String status;
    /**
     * 电话
     */
	private String phone;
    /**
     * 邮箱
     */
	private String email;
    /**
     * 性别
     */
	private String sex;
    /**
     * 头像
     */
	private String picId;
    /**
     * 地址
     */
	private String address;
    /**
     * 省份
     */
	private String province;
    /**
     * 城市
     */
	private String city;
    /**
     * 区划
     */
	private String area;
	/**
	 * 最后更新密码时间
	 */
	private Date lastPasswordResetDate;
	/**
	 * 是否删除
	 */
	@TableField("IS_DELETE")
	private Integer isDelete;

	/**
	 * 创建人
	 */
	@TableField("ADD_USER")
	private String addUser;

	/**
	 * 创建时间
	 */
	@TableField("ADD_TIME")
	private String addTime;

	/**
	 * 更新人
	 */
	@TableField("UPDATE_USER")
	private String updateUser;

	/**
	 * 更新时间
	 */
	@TableField("UPDATE_TIME")
	private String updateTime;


	public String getPkUserId() {
		return pkUserId;
	}

	public void setPkUserId(String pkUserId) {
		this.pkUserId = pkUserId;
	}

	public String getFkDepotId() {
		return fkDepotId;
	}

	public void setFkDepotId(String fkDepotId) {
		this.fkDepotId = fkDepotId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPicId() {
		return picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Date lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
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
		return this.pkUserId;
	}

	@Override
	public String toString() {
		return "SysUser{" +
			"pkUserId=" + pkUserId +
			", fkDepotId=" + fkDepotId +
			", userName=" + userName +
			", account=" + account +
			", password=" + password +
			", salt=" + salt +
			", status=" + status +
			", phone=" + phone +
			", email=" + email +
			", sex=" + sex +
			", picId=" + picId +
			", address=" + address +
			", province=" + province +
			", city=" + city +
			", area=" + area +
			", lastPasswordResetDate=" + lastPasswordResetDate +
			", isDelete=" + isDelete +
			", addUser=" + addUser +
			", addTime=" + addTime +
			", updateUser=" + updateUser +
			", updateTime=" + updateTime +
			"}";
	}
}
