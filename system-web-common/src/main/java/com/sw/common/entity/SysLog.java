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
 * 记录日志
 * </p>
 *
 * @author yu.leilei
 * @since 2018-12-23
 */
@TableName("sys_log")
@Data
public class SysLog extends Model<SysLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 日志主键
     */
    @TableId(type = IdType.UUID)
	private String pkLogId;
    /**
     * 操作人ID
     */
	private String fkUserId;
    /**
     * 操作人账号
     */
	private String account;
    /**
     * 日志类型
     */
	private String logType;

	/**
	 * 日志类型
	 */
	@TableField(exist = false)
	private String logTypeMsg;
    /**
     * 操作事件
     */
	private String operation;
    /**
     * 操作时长
     */
	private Long operateTime;
    /**
     * 操作类
     */
	private String className;
    /**
     * 参数
     */
	private String params;
    /**
     * ip
     */
	private String ip;
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
		return this.pkLogId;
	}

	@Override
	public String toString() {
		return "SysLog{" +
			"pkLogId=" + pkLogId +
			", fkUserId=" + fkUserId +
			", account=" + account +
			", logType=" + logType +
			", operation=" + operation +
			", operateTime=" + operateTime +
			", className=" + className +
			", params=" + params +
			", ip=" + ip +
			", isDelete=" + isDelete +
			", addUser=" + addUser +
			", addTime=" + addTime +
			", updateUser=" + updateUser +
			", updateTime=" + updateTime +
			"}";
	}
}
