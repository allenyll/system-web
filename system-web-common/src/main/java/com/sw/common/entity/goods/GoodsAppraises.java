package com.sw.common.entity.goods;

import lombok.Data;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.sw.common.entity.Entity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 商品评价表
 * 
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-05-09 15:16:31
 */
@Data
@TableName("snu_goods_appraises")
public class GoodsAppraises extends Entity<GoodsAppraises>  {

	private static final long serialVersionUID = 1L;

	// 主键Id
    @TableId(type = IdType.UUID)
    private String pkAppraiseId;

	// 订单Id
    private String fkOrderId;

	// 商品Id
    private String fkGoodsId;

	// 会员Id
    private String fkCustomerId;

	// 商品评分
    private Integer goodsScore;

	// 服务评分
    private Integer serviceScore;

	// 时效评分
    private Integer timeScore;

	// 点评内容
    private String content;

	// 回复内容
    private String replyContent;

	// 回复时间
    private String replyTime;

	// 上传图片
    private String imageUrl;

	// 是否显示
    private String isShow;

	// 是否有效
    private String status;

	@Override
    protected Serializable pkVal() {
		return pkAppraiseId;
	}



}
