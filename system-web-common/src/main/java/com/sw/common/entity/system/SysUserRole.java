package com.sw.common.entity.system;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.ToString;

/**
 * <p>
 * 用户权限表
 * </p>
 *
 * @author yu.leilei
 * @since 2018-11-13
 */
@Data
@ToString
@TableName("sys_user_role")
public class SysUserRole extends Model<SysUserRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 关系主键
     */
	@TableId(type = IdType.UUID)
	private String pkRelationId;
    /**
     * 用户ID
     */
	private String fkUserId;
    /**
     * 权限ID
     */
	private String fkRoleId;

	@Override
	protected Serializable pkVal() {
		return this.pkRelationId;
	}

}
