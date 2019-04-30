package com.sw.base.controller.pay;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.base.service.impl.customer.CustomerServiceImpl;
import com.sw.base.service.impl.pay.TransactionServiceImpl;
import com.sw.cache.service.IRedisService;
import com.sw.cache.util.DataResponse;
import com.sw.common.constants.dict.PayTypeDict;
import com.sw.common.entity.customer.Customer;
import com.sw.common.entity.pay.Transaction;
import com.sw.common.properties.WxProperties;
import com.sw.common.util.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Description:  微信支付接口
 * @Author:       allenyll
 * @Date:         2019/4/4 3:40 PM
 * @Version:      1.0
 */
@Controller
@RequestMapping("/system-web/pay")
public class WxPayController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WxPayController.class);

    @Autowired
    WxProperties wxProperties;

    @Autowired
    TransactionServiceImpl transactionService;

    @Autowired
    CustomerServiceImpl customerService;

    @ResponseBody
    @RequestMapping(value = "createUnifiedOrder", method = RequestMethod.POST)
    public DataResponse createUnifiedOrder(HttpServletRequest request, HttpServletResponse response) {
        //设置最终返回对象
        Map<String, Object> result = new HashMap<>();
        //接受参数(openid)
        String openid = request.getParameter("openid");
        if(!openid.equals(AppContext.getCurrentUserWechatOpenId())){
            return DataResponse.fail("当前用户与登录用户不匹配");
        }

        //接受参数(金额)
        String amount = request.getParameter("amount");
        //用户ID
        String customerId = request.getParameter("customerId");
        //支付来源
        String payName = request.getParameter("payName");
        //支付备注
        String remark = request.getParameter("remark");
        //接口调用总金额单位为分换算一下(测试金额改成1,单位为分则是0.01,根据自己业务场景判断是转换成float类型还是int类型)
        //String amountFen = Integer.valueOf((Integer.parseInt(amount)*100)).toString();
        //String amountFen = Float.valueOf((Float.parseFloat(amount)*100)).toString();
        String amountFen = "1";
        //创建hashmap(用户获得签名)
        SortedMap<String, String> paraMap = new TreeMap<String, String>();
        //设置body变量 (支付成功显示在微信支付 商品详情中)
        String body = "test";
        //设置随机字符串
        String nonceStr = StringUtil.getRandomString(11);
        //设置商户订单号
        String outTradeNo =  StringUtil.getRandomString(11);


        //设置请求参数(小程序ID)
        paraMap.put("appid", wxProperties.getAppId());
        //设置请求参数(商户号)
        paraMap.put("mch_id", wxProperties.getMchId());
        //设置请求参数(随机字符串)
        paraMap.put("nonce_str", nonceStr);
        //设置请求参数(商品描述)
        paraMap.put("body", body);
        //设置请求参数(商户订单号)
        paraMap.put("out_trade_no", outTradeNo);
        //设置请求参数(总金额)
        paraMap.put("total_fee", amountFen);
        //设置请求参数(终端IP)
        paraMap.put("spbill_create_ip", IPUtil.getIpAddr(request, response));
        //设置请求参数(通知地址)
        paraMap.put("notify_url", "http://localhost:8080/payNotify");
        //设置请求参数(交易类型)
        paraMap.put("trade_type", "JSAPI");
        //设置请求参数(openid)(在接口文档中 该参数 是否必填项 但是一定要注意 如果交易类型设置成'JSAPI'则必须传入openid)
        paraMap.put("openid", openid);
        //调用逻辑传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        String stringA = StringUtil.formatUrlMap(paraMap, false, false);
        //第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值signValue。(签名)
        String sign = MD5Util.md5Password(stringA+"&key="+wxProperties.getKey()).toUpperCase();
        //将参数 编写XML格式
        StringBuffer paramBuffer = new StringBuffer();
        paramBuffer.append("<xml>");
        paramBuffer.append("<appid>"+wxProperties.getAppId()+"</appid>");
        paramBuffer.append("<mch_id>"+wxProperties.getMchId()+"</mch_id>");
        paramBuffer.append("<nonce_str>"+paraMap.get("nonce_str")+"</nonce_str>");
        paramBuffer.append("<sign>"+sign+"</sign>");
        paramBuffer.append("<body>"+body+"</body>");
        paramBuffer.append("<out_trade_no>"+paraMap.get("out_trade_no")+"</out_trade_no>");
        paramBuffer.append("<total_fee>"+paraMap.get("total_fee")+"</total_fee>");
        paramBuffer.append("<spbill_create_ip>"+paraMap.get("spbill_create_ip")+"</spbill_create_ip>");
        paramBuffer.append("<notify_url>"+paraMap.get("notify_url")+"</notify_url>");
        paramBuffer.append("<trade_type>"+paraMap.get("trade_type")+"</trade_type>");
        paramBuffer.append("<openid>"+paraMap.get("openid")+"</openid>");
        paramBuffer.append("</xml>");

        try {
            //发送请求(POST)(获得数据包ID)(这有个注意的地方 如果不转码成ISO8859-1则会告诉你body不是UTF8编码 就算你改成UTF8编码也一样不好使 所以修改成ISO8859-1)
            Map<String,String> map = XmlUtil.doXMLParse(HttpContextUtils.getRemotePortData(wxProperties.getOrderUrl(), new String(paramBuffer.toString().getBytes(), "ISO8859-1")));
            //应该创建 支付表数据
            if(map!=null){
//                EntityWrapper<Customer> customerEntityWrapper = new EntityWrapper<>();
//                customerEntityWrapper.eq("IS_DELETE", 0);
//                customerEntityWrapper.eq("openid", openid);
//                Customer customer = customerService.selectOne(customerEntityWrapper);
//                if(customer == null){
//                    // TODO新建用户
//                }
                // 查看是否有支付记录
                EntityWrapper<Transaction> transactionEntityWrapper = new EntityWrapper<>();
                transactionEntityWrapper.eq("FK_CUSTOMER_ID", customerId);
                transactionEntityWrapper.eq("IS_DELETE", 0);
                List<Transaction> payInfoList = transactionService.selectList(transactionEntityWrapper);
                //如果等于空 则证明是第一次支付
                if(CollectionUtils.isEmpty(payInfoList)){
                    // 第一次支付
                }
                //创建支付信息对象
                Transaction transaction = new Transaction();
                transaction.setTransactionNo(outTradeNo);
                transaction.setFkCustomerId(customerId);
                transaction.setIntegral(0);
                transaction.setSource(payName);
                transaction.setTransactionTime(DateUtil.getCurrentDateTime());
                transaction.setAmount(new BigDecimal(amount));
                transaction.setStatus("SW1201");
                transaction.setPayChannel(PayTypeDict.WECHAT.getCode());
                transaction.setRemark(remark);

                transaction.setIsDelete(0);
                transaction.setAddTime(DateUtil.getCurrentDateTime());
                transaction.setAddUser(customerId);
                transaction.setUpdateTime(DateUtil.getCurrentDateTime());
                transaction.setUpdateUser(customerId);
                //插入Dao
                boolean sqlRow = transactionService.insert(transaction);
                //判断
                if(sqlRow){
                    LOGGER.info("微信 统一下单 接口调用成功 并且新增支付信息成功");
                    EntityWrapper<Transaction> entityWrapper = new EntityWrapper<>();
                    entityWrapper.eq("IS_DELETE", 0);
                    entityWrapper.eq("TRANSACTION_NO", outTradeNo);
                    entityWrapper.eq("FK_CUSTOMER_ID", customerId);
                    Transaction _transaction = transactionService.selectOne(entityWrapper);
                    if(_transaction != null){
                        result.put("transaction_id", _transaction.getPkTransactionId());
                    }
                    result.put("prepayId", map.get("prepay_id"));
                    result.put("outTradeNo", paraMap.get("out_trade_no"));
                    return DataResponse.success(result);
                }
            }
            //将 数据包ID 返回

            System.out.println(map);
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("微信 统一下单 异常："+e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            LOGGER.info("微信 统一下单 异常："+e.getMessage());
            e.printStackTrace();
        }
        LOGGER.info("微信 统一下单 失败");
        return DataResponse.success();
    }

    @ResponseBody
    @RequestMapping(value = "sign", method = RequestMethod.POST)
    public DataResponse sign(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("微信 支付接口生成签名 方法开始");
        //实例化返回对象
        Map<String, Object> result = new HashMap<>();

        //获得参数(微信统一下单接口生成的prepay_id )
        String prepayId = request.getParameter("prepayId");
        //创建 时间戳
        String timeStamp = Long.valueOf(System.currentTimeMillis()).toString();
        //创建 随机串
        String nonceStr = StringUtil.getRandomString(11);
        //创建 MD5
        String signType = "MD5";

        //创建hashmap(用户获得签名)
        SortedMap<String, String> paraMap = new TreeMap<String, String>();
        //设置(小程序ID)(这块一定要是大写)
        paraMap.put("appId", wxProperties.getAppId());
        //设置(时间戳)
        paraMap.put("timeStamp", timeStamp);
        //设置(随机串)
        paraMap.put("nonceStr", nonceStr);
        //设置(数据包)
        paraMap.put("package", "prepay_id="+prepayId);
        //设置(签名方式)
        paraMap.put("signType", signType);


        //调用逻辑传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        String stringA = StringUtil.formatUrlMap(paraMap, false, false);
        //第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值signValue。(签名)
        String sign = MD5Util.md5Password(stringA+"&key="+wxProperties.getKey()).toUpperCase();

        if(StringUtil.isNotEmpty(sign)){
            //返回签名信息
            result.put("sign", sign);
            //返回随机串(这个随机串是新创建的)
            result.put("nonceStr", nonceStr);
            //返回时间戳
            result.put("timeStamp", timeStamp);
            //返回数据包
            result.put("package", "prepay_id="+prepayId);

            LOGGER.info("微信 支付接口生成签名 设置返回值");
        }
        LOGGER.info("微信 支付接口生成签名 方法结束");
        return DataResponse.success(result);
    }

    @RequestMapping(value = "/updateStatus",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse updateObj(@RequestBody Map<String, Object> params){
        String transactionId = MapUtil.getMapValue(params, "transactionId");
        EntityWrapper<Transaction> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("IS_DELETE", 0);
        entityWrapper.eq("PK_TRANSACTION_ID", transactionId);
        Transaction transaction = transactionService.selectOne(entityWrapper);
        if(transaction != null){
            transaction.setStatus("SW1202");
            transactionService.updateById(transaction);
        }
        return DataResponse.success();
    }

}
