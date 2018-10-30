package com.sw.base.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author yu.leilei
 * @since 2018-10-22
 */
@Data
@TableName("t_snu_customer")
public class Customer extends Model<Customer> {

    private static final long serialVersionUID = 1L;

    /**
     * 会员ID
     */
	@TableId(type = IdType.UUID)
	private String pkCustomerId;
    /**
     * 会员名称
     */
	private String customerName;
    /**
     * 会员账号
     */
	private String customerAccount;
	/**
	 * 昵称
	 */
	private String nickName;
    /**
     * 密码
     */
	private String password;
    /**
     * 密码盐
     */
	private String salt;
    /**
     * 状态
     */
	private String status;
    /**
     * 手机号
     */
	private String phone;
    /**
     * 邮箱
     */
	private String email;
    /**
     * 性别
     */
	private String gender;
    /**
     * 头像
     */
	private String avatarUrl;
    /**
     * 地址
     */
	private String address;
	private String country;
	private String province;
	private String city;
    /**
     * 微信openid
     */
	private String openid;
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
		return this.pkCustomerId;
	}

}
