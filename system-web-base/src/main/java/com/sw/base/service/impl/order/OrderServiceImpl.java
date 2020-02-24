package com.sw.base.service.impl.order;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.auth.service.impl.UserServiceImpl;
import com.sw.base.service.impl.system.DictServiceImpl;
import com.sw.cache.service.IRedisService;
import com.sw.cache.util.DataResponse;
import com.sw.common.constants.dict.*;
import com.sw.common.entity.order.OrderDetail;
import com.sw.common.entity.order.OrderOperateLog;
import com.sw.common.entity.system.Dict;
import com.sw.common.entity.system.User;
import com.sw.common.util.CollectionUtil;
import com.sw.common.util.DateUtil;
import com.sw.common.util.MapUtil;
import com.sw.common.util.StringUtil;
import io.swagger.models.auth.In;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sw.common.entity.order.Order;
import com.sw.base.mapper.order.OrderMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderDetailServiceImpl orderDetailService;

    @Autowired
    protected IRedisService redisService;

    @Autowired
    DictServiceImpl dictService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    OrderOperateLogServiceImpl orderOperateLogService;

    public void createOrder(Order order, Map<String, Object> param) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = DateUtil.getCurrentDateTime();
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
        order.setOrderTime(time);
        order.setAddTime(time);
        order.setUpdateTime(time);
        order.setIsDelete(0);

        order.setIntegration(MapUtil.getInteger(param, "goodsIntegral"));
        order.setGrowth(MapUtil.getInteger(param, "giftGrowth"));

        order.setCouponAmount(new BigDecimal(MapUtil.getString(param, "couponAmount")));
        order.setDiscountAmount(new BigDecimal(MapUtil.getString(param, "discountAmount")));
        order.setPromotionAmount(new BigDecimal(MapUtil.getString(param, "promotionAmount")));
        order.setIntegrationAmount(new BigDecimal(MapUtil.getString(param, "integrationAmount")));
        order.setLogisticsFee(new BigDecimal(MapUtil.getString(param, "logisticsFee")));
        order.setTotalAmount(new BigDecimal(MapUtil.getString(param, "totalAmount")));
        order.setOrderAmount(new BigDecimal(MapUtil.getString(param, "allGoodsPrice")));
        order.setPayAmount(new BigDecimal(MapUtil.getString(param, "totalAmount")));

        order.setFkAddressId(MapUtil.getString(param, "fkAddressId"));
        order.setFkCouponId(MapUtil.getString(param, "couponId"));
        order.setFkCustomerId(MapUtil.getString(param, "fkCustomerId"));

        order.setOrderRemark(MapUtil.getString(param, "remark"));

        order.setReceiverName(MapUtil.getString(param, "name"));
        order.setReceiverPhone(MapUtil.getString(param, "phone"));
        order.setReceiverPostCode(MapUtil.getString(param, "postCode"));
        order.setReceiverProvince(MapUtil.getString(param, "province"));
        order.setReceiverCity(MapUtil.getString(param, "city"));
        order.setReceiverRegion(MapUtil.getString(param, "region"));
        order.setReceiverDetailAddress(MapUtil.getString(param, "detailAddress"));

        dealGoodsList(order, param);

        try {
            orderMapper.insert(order);
        } catch (Exception e) {
            LOGGER.info("订单创建异常");
            e.printStackTrace();
            return;
        }
        LOGGER.info("cacheOrder: ", order);
    }

    public void dealGoodsList(Order order, Map<String, Object> param) {
        int count = 0;
        String goodsJsonStr = MapUtil.getString(param, "goodsList");
        JSONArray jsonArray = JSONArray.fromObject(goodsJsonStr);
        if (jsonArray.size() > 0) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                int num = json.getInt("number");
                count += num;

                addOrderDetail(order, json);
            }
        }

        order.setGoodsCount(count);

    }

    private void addOrderDetail(Order order, JSONObject json) {

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setFkCategoryId(json.getString("fkCategoryId"));
        orderDetail.setFkGoodsId(json.getString("goodsId"));
        orderDetail.setFkOrderId(order.getPkOrderId());
        orderDetail.setGoodsName(json.getString("name"));
        orderDetail.setGoodsPrice(new BigDecimal(json.getString("price")));
        orderDetail.setOrderNo(order.getOrderNo());
        orderDetail.setPic(json.getString("pic"));
        orderDetail.setFkSkuId(json.getString("skuId"));
        orderDetail.setQuantity(json.getInt("number"));
        orderDetail.setSkuCode(json.getString("skuCode"));
        orderDetail.setDeductStockType("");
        orderDetail.setBrandName(json.getString("brandName"));
        orderDetail.setContent("");
        orderDetail.setTotalAmount(new BigDecimal("0"));
        orderDetail.setCouponAmount(new BigDecimal("0"));
        orderDetail.setIntegrationAmount(new BigDecimal("0"));
        orderDetail.setPromotionAmount(new BigDecimal("0"));
        orderDetail.setPromotionName("");
        orderDetail.setRealAmount(new BigDecimal("0"));
        orderDetail.setSpecValue(json.getString("specValue"));
        orderDetail.setAttributes(json.getString("selectSpecOption").replace("\"", "").replace("[", "").replace("]", ""));
        orderDetail.setRemark("");

        orderDetail.setIsDelete(0);
        orderDetail.setAddTime(order.getOrderTime());
        orderDetail.setUpdateTime(order.getOrderTime());


        try {
            orderDetailService.insert(orderDetail);
        } catch (Exception e) {
            LOGGER.info("订单商品明细新增异常");
            e.printStackTrace();
            return;
        }

    }

    public int getUnPayNum(Map<String, Object> params) {
        Integer num = orderMapper.getUnPayNum(params);
        return num == null ? 0 : num.intValue();
    }

    public int getUnReceiveNum(Map<String, Object> params) {
        Integer num = orderMapper.getUnReceiveNum(params);
        return num == null ? 0 : num.intValue();
    }

    public int getReceiveNum(Map<String, Object> params) {
        Integer num = orderMapper.getReceiveNum(params);
        return num == null ? 0 : num.intValue();
    }

    public int getAppraisesNum(Map<String, Object> params) {
        Integer num = orderMapper.getAppraisesNum(params);
        return num == null ? 0 : num.intValue();
    }

    public int getFinishNum(Map<String, Object> params) {
        Integer num = orderMapper.getFinishNum(params);
        return num == null ? 0 : num.intValue();
    }

    public int getDeliveryNum(Map<String, Object> params) {
        Integer num = orderMapper.getDeliveryNum(params);
        return num == null ? 0 : num.intValue();
    }

    public List<Order> getOrderList(Map<String, Object> params) {
        EntityWrapper<Order> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("ORDER_TYPE", "SW0601");
        wrapper.eq("FK_CUSTOMER_ID", MapUtil.getString(params, "customerId"));
        wrapper.orderBy(true, "ORDER_TIME", false);

        List<Order> list = orderMapper.selectList(wrapper);

        dealList(list);

        return list;
    }

    private void dealList(List<Order> list) {
        if (CollectionUtil.isNotEmpty(list)) {
            for (Order order : list) {
                Map<String, Object> _map = new HashMap<>();
                _map.put("orderId", order.getPkOrderId());
                Dict statusDict = dictService.getDictByCode(order.getOrderStatus());
                if (statusDict != null) {
                    order.setStatusStr(statusDict.getName());
                }
                // 获取订单明细
                List<OrderDetail> orderDetails = orderDetailService.getOrderDetailList(_map);
                order.setOrderDetails(orderDetails);
            }
        }
    }

    public int selectCount(Map<String, Object> params) {
        Integer num = orderMapper.selectCount(params);
        return num == null ? 0 : num.intValue();
    }

    /**
     * @param params
     * @return
     */
    public List<Map<String, Object>> getOrderPage(Map<String, Object> params) {
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        int start = (page - 1) * limit;
        params.put("start", start);
        params.put("limit", limit);
        params.put("year", DateUtil.getCurrentDate().substring(4));
        List<Map<String, Object>> orderList = orderMapper.getOrderPage(params);
        return orderList;
    }

    public DataResponse deleteOrder(Map<String, Object> params) {
        String userId = redisService.get("userId");
        String ids = MapUtil.getString(params, "ids");
        String[] idArr = ids.split(",");
        String time = DateUtil.getCurrentDateTime();
        if (idArr.length > 0) {
            for (int i = 0; i < idArr.length; i++) {
                try {
                    Order order = orderMapper.selectById(idArr[i]);
                    order.setIsDelete(1);
                    order.setUpdateUser(userId);
                    order.setUpdateTime(DateUtil.getCurrentDateTime());
                    orderMapper.updateById(order);
                    // 记录日志
                    dealOperateLog(userId, time, order, "", "delete");
                } catch (Exception e) {
                    e.printStackTrace();
                    return DataResponse.fail("订单删除失败");
                }
            }
        }
        return DataResponse.success();
    }

    public DataResponse closeOrder(Map<String, Object> params) {
        String userId = redisService.get("userId");
        String ids = MapUtil.getString(params, "ids");
        String note = MapUtil.getString(params, "note");
        String[] idArr = ids.split(",");
        String time = DateUtil.getCurrentDateTime();
        if (idArr.length > 0) {
            for (int i = 0; i < idArr.length; i++) {
                try {
                    Order order = orderMapper.selectById(idArr[i]);
                    order.setOrderStatus(OrderStatusDict.CLOSE.getCode());
                    order.setNote(note);
                    order.setUpdateUser(userId);
                    order.setUpdateTime(time);
                    orderMapper.updateById(order);
                    // 记录日志
                    dealOperateLog(userId, time, order, note, "close");
                } catch (Exception e) {
                    e.printStackTrace();
                    return DataResponse.fail("订单关闭失败");
                }
            }
        }
        return DataResponse.success();
    }

    public DataResponse deliveryOrder(Map<String, Object> params) {
        String userId = redisService.get("userId");
        String time = DateUtil.getCurrentDateTime();
        String orderId = MapUtil.getString(params, "orderId");
        if (StringUtil.isEmpty(orderId)) {
            return DataResponse.fail("订单不存在");
        }
        try {
            Order order = orderMapper.selectById(orderId);
            order.setDeliveryCompany(MapUtil.getString(params, "deliveryCompany"));
            order.setDeliveryNo(MapUtil.getString(params, "deliveryNo"));
            order.setDeliveryTime(time);
            order.setOrderStatus(OrderStatusDict.DELIVERY.getCode());
            order.setUpdateUser(userId);
            order.setUpdateTime(time);
            orderMapper.updateById(order);
            // 记录日志
            dealOperateLog(userId, time, order, "", "delivery");
        } catch (Exception e) {
            e.printStackTrace();
            return DataResponse.fail("订单发货失败");
        }
        return DataResponse.success();
    }

    public DataResponse updateMoneyInfo(Map<String, Object> params) {
        String userId = redisService.get("userId");
        String time = DateUtil.getCurrentDateTime();
        String orderId = MapUtil.getString(params, "orderId");
        if (StringUtil.isEmpty(orderId)) {
            return DataResponse.fail("订单不存在");
        }
        try {
            Order order = orderMapper.selectById(orderId);
            BigDecimal logisticsFee = new BigDecimal(MapUtil.getString(params, "logisticsFee"));
            BigDecimal discountAmount = new BigDecimal(MapUtil.getString(params, "discountAmount"));
            order.setLogisticsFee(logisticsFee);
            order.setDiscountAmount(discountAmount);
            order.setTotalAmount(order.getTotalAmount().add(logisticsFee).subtract(discountAmount));
            order.setPayAmount(order.getPayAmount().add(logisticsFee).subtract(discountAmount));
            order.setUpdateUser(userId);
            order.setUpdateTime(time);
            orderMapper.updateById(order);
            // 记录日志
            dealOperateLog(userId, time, order, "", "money");
        } catch (Exception e) {
            e.printStackTrace();
            return DataResponse.fail("订单修改金额失败");
        }
        return DataResponse.success();
    }

    public DataResponse updateReceiverInfo(Map<String, Object> params) {
        String userId = redisService.get("userId");
        String time = DateUtil.getCurrentDateTime();
        String orderId = MapUtil.getString(params, "orderId");
        if (StringUtil.isEmpty(orderId)) {
            return DataResponse.fail("订单不存在");
        }
        try {
            Order order = orderMapper.selectById(orderId);
            order.setReceiverName(MapUtil.getString(params, "receiverName"));
            order.setReceiverPhone(MapUtil.getString(params, "receiverPhone"));
            order.setReceiverPostCode(MapUtil.getString(params, "receiverPostCode"));
            order.setReceiverProvince(MapUtil.getString(params, "receiverProvince"));
            order.setReceiverCity(MapUtil.getString(params, "receiverCity"));
            order.setReceiverRegion(MapUtil.getString(params, "receiverRegion"));
            order.setReceiverDetailAddress(MapUtil.getString(params, "receiverDetailAddress"));
            order.setUpdateUser(userId);
            order.setUpdateTime(time);
            orderMapper.updateById(order);
            // 记录日志
            dealOperateLog(userId, time, order, "", "receiver");
        } catch (Exception e) {
            e.printStackTrace();
            return DataResponse.fail("订单修改收货地址失败");
        }
        return DataResponse.success();
    }

    public DataResponse updateOrderNote(Map<String, Object> params) {
        String userId = redisService.get("userId");
        String time = DateUtil.getCurrentDateTime();
        String orderId = MapUtil.getString(params, "orderId");
        if (StringUtil.isEmpty(orderId)) {
            return DataResponse.fail("订单不存在");
        }
        try {
            Order order = orderMapper.selectById(orderId);
            order.setNote(MapUtil.getString(params, "note"));
            order.setUpdateUser(userId);
            order.setUpdateTime(time);
            orderMapper.updateById(order);
            // 记录日志
            dealOperateLog(userId, time, order, "", "note");
        } catch (Exception e) {
            e.printStackTrace();
            return DataResponse.fail("订单后台备注失败");
        }
        return DataResponse.success();
    }


    private void dealOperateLog(String userId, String time, Order order, String note, String type) {
        User user = userService.selectById(userId);
        if (user == null) {
            user.setUserName("后台管理员");
        }
        String remark = "订单";
        OrderOperateLog orderOperateLog = new OrderOperateLog();
        if ("close".equals(type)) {
            remark = remark + "关闭：" + note;
        } else if ("delete".equals(type)) {
            remark = remark + "删除";
        } else if ("delivery".equals(type)) {
            remark = remark + "发货";
        } else if ("money".equals(type)) {
            remark = remark + "修改价格";
        } else if ("receiver".equals(type)) {
            remark = remark + "修改收货人信息";
        } else if ("note".equals(type)) {
            remark = remark + "修改备注信息";
        }
        orderOperateLog.setFkOrderId(order.getPkOrderId());
        orderOperateLog.setOrderStatus(order.getOrderStatus());
        orderOperateLog.setRemark(remark);
        orderOperateLog.setIsDelete(0);
        orderOperateLog.setAddUser(userId);
        orderOperateLog.setAddTime(time);
        orderOperateLog.setUpdateUser(user.getUserName());
        orderOperateLog.setUpdateTime(time);
        orderOperateLogService.insert(orderOperateLog);
    }
}
