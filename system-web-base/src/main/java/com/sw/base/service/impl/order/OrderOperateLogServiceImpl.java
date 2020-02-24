package com.sw.base.service.impl.order;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.common.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sw.common.entity.order.OrderOperateLog;
import com.sw.base.mapper.order.OrderOperateLogMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 记录订单操作日志
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-07-16 16:34:06
 */
@Service("orderOperateLogService")
public class OrderOperateLogServiceImpl extends BaseService<OrderOperateLogMapper,OrderOperateLog> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderOperateLogServiceImpl.class);

    @Autowired
    OrderOperateLogMapper orderOperateLogMapper;

    public List<OrderOperateLog> getOperateList(Map<String, Object> map) {
        EntityWrapper<OrderOperateLog> wrapper  = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("FK_ORDER_ID", MapUtil.getString(map, "orderId"));
        return  orderOperateLogMapper.selectList(wrapper);
    }
}
