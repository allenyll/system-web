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
 * 文件相关信息
 * 
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-03-26 21:28:23
 */
@Data
@TableName("sys_file")
public class File extends Entity<File>  {

	private static final long serialVersionUID = 1L;

	// 文件主键
    @TableId(type = IdType.UUID)
    private String pkFileId;

	// 外键Id
    private String fkId;

	// 文件类型
    private String fileType;

	// 文件路径
    private String fileUrl;

	// 备注
    private String remark;

	@Override
    protected Serializable pkVal() {
		return pkFileId;
	}



}
