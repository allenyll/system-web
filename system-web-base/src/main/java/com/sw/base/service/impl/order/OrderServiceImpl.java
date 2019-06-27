package com.sw.base.service.impl.order;

import com.sw.common.constants.dict.*;
import com.sw.common.util.DateUtil;
import com.sw.common.util.MapUtil;
import com.sw.common.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import com.sw.common.entity.order.Order;
import com.sw.base.mapper.order.OrderMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * 订单基础信息表
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-06-26 21:11:07
 */
@Service("orderService")
public class OrderServiceImpl extends BaseService<OrderMapper,Order> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    public void createOrder(Order order, Map<String, Object> param) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String orderId = StringUtil.getUUID32();
        String no = StringUtil.getOrderNo(orderId, sdf);
        order.setPkOrderId(orderId);
        order.setOrderNo(no);
        order.setAfterStatus(OrderSaleDict.START.getCode());
        order.setOrderType(OrderTypeDict.ONLINE.getCode());
        order.setAutoConfirmDay(7);
        order.setConfirmStatus(OrderConfirmDict.UN_CONFIRM.getCode());
        order.setOrderStatus(OrderStatusDict.START.getCode());
        order.setSettlementStatus(OrderSettleDict.UN_SETTLE.getCode());
        order.setOrderTime(DateUtil.getCurrentDateTime());
        order.setAddTime(DateUtil.getCurrentDateTime());
        order.setUpdateTime(DateUtil.getCurrentDateTime());
        order.setIsDelete(0);
        dealGoodsList(order, param);
        LOGGER.info("cacheOrder: ", order);
    }

    public void dealGoodsList(Order order, Map<String, Object> param) {
        int count = 0;
        String goodsJsonStr = MapUtil.getString(param, "goodsJsonStr");
        JSONArray jsonArray = JSONArray.fromObject(goodsJsonStr);
        if(jsonArray.size() > 0){
            for(int i=0; i< jsonArray.size(); i++){
                JSONObject json = jsonArray.getJSONObject(i);
                int num  = json.getInt("number");
                count += num;
            }
        }

        order.setGoodsCount(count);

    }
}
