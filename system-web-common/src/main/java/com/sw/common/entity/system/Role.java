package com.sw.common.entity.system;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.sw.common.entity.Entity;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

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
@ToString
public class Role extends Entity<Role> {

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

	@Override
	protected Serializable pkVal() {
		return pkRoleId;
	}
}
