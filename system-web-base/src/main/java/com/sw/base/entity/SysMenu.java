package com.sw.base.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

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
@Data
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


	@Override
	protected Serializable pkVal() {
		return this.pkMenuId;
	}
}
