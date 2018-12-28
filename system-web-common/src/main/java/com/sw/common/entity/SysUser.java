package com.sw.common.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author yu.leilei
 * @since 2018-06-12
 */
@TableName("sys_user")
@Data
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

	@TableField(exist = false)
	private String depotName;
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

	@TableField(exist = false)
	private String realStatus;
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

	@TableField(exist = false)
	private String realSex;
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

	@Override
	protected Serializable pkVal() {
		return this.pkUserId;
	}

}
