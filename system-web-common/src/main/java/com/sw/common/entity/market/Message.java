package com.sw.common.entity.market;

import lombok.Data;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.sw.common.entity.Entity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 消息表，包含通知，推送，私信等
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-12-25 18:51:28
 */
@Data
@TableName("snu_message")
public class Message extends Entity<Message>  {

	private static final long serialVersionUID = 1L;

	// 主键ID
    @TableId(type = IdType.UUID)
    private String pkMessageId;

	// 消息名称
    private String messageName;

	// 消息内容
    private String content;

	// 消息类型
    private String type;

	// 消息摘要
    private String abstractMsg;

	// 消息时间
    private String messageTime;

	// 消息创建人
    private String messageSender;

	@Override
    protected Serializable pkVal() {
		return pkMessageId;
	}



}
