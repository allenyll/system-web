package com.sw.common.entity.system;

import lombok.Data;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.sw.common.entity.Entity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 调度任务表
 * 
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-03-17 12:07:34
 */
@Data
@TableName("sys_job")
public class Job extends Entity<Job>  {

	private static final long serialVersionUID = 1L;

	// 任务主键
    @TableId(type = IdType.UUID)
    private String pkJobId;

	// 名称
    private String jobName;

	// 调用方法名
    private String methodName;

	// 表达式
    private String corn;

	// 任务状态
    private String status;

	// 调用类名
    private String className;

	// 任务分组
    private String jobGroup;

	// 描述
    private String description;

	// springBean
    private String springBean;

	@Override
    protected Serializable pkVal() {
		return pkJobId;
	}



}
