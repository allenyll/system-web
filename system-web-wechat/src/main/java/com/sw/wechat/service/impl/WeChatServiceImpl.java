package com.sw.wechat.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw.common.entity.Customer;
import com.sw.base.service.ICustomerService;
import com.sw.cache.service.IRedisService;
import com.sw.common.constants.WxConstants;
import com.sw.common.constants.dict.UserStatus;
import com.sw.common.util.AppContext;
import com.sw.common.util.DataResponse;
import com.sw.common.util.DateUtil;
import com.sw.wechat.entity.WxCodeResponse;
import com.sw.wechat.properties.WeChatProperties;
import com.sw.wechat.service.IWeChatService;
import org.apache.commons.lang.RandomStringUtils;
import org.omg.CORBA.OBJ_ADAPTER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
* @Title: WeChatServiceImpl
* @Package com.sw.wechat.service.impl
* @Description: 微信业务实现
* @author yu.leilei
* @date 2018/10/19 17:40
* @version V1.0
*/
@Service("weChatService")
public class WeChatServiceImpl implements IWeChatService {

    private static final Logger LOG = LoggerFactory.getLogger(WeChatServiceImpl.class);

    /**
     * 服务器第三方session有效时间，单位秒, 默认1天
     */
    private static final Long EXPIRES = 86400L;

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    WeChatProperties weChatProperties;

    @Autowired
    ICustomerService customerService;

    @Autowired
    IRedisService redisService;

    @Override
    public String auth(String code) {

        WxCodeResponse wxCodeResponse = getWxCodeSession(code);
        String openid = wxCodeResponse.getOpenid();
        String sessionKey = wxCodeResponse.getSessionKey();

        String token = createToken(sessionKey, openid);
        redisService.set(WxConstants.WX_JWT_MARK, WxConstants.WX_JWT);
        redisService.expire(WxConstants.WX_JWT_MARK, EXPIRES);
        return token;
    }

    @Override
    public String login(Customer customer){
        customerService.loginOrRegisterConsumer(customer);
        return null;
    }

    /**
     * 创建token
     * @param sessionKey
     * @param openid
     * @return
     */
    private String createToken(String sessionKey, String openid) {
        String cacheKey = RandomStringUtils.randomAlphanumeric(64);
        StringBuffer sb = new StringBuffer();
        sb.append(sessionKey + "#" + openid);
        redisService.set(cacheKey + "#" + openid, sb.toString());
        redisService.expire(cacheKey + "#" + openid, EXPIRES);
        return cacheKey + "#" + openid;
    }

    /**
     * code2session
     * @param code
     * @return
     */
    private WxCodeResponse getWxCodeSession(String code) {

        LOG.info("获取session_key的code:" + code);
        String urlString = "?appid={appid}&secret={secret}&js_code={code}&grant_type={grantType}";
        Map<String, Object> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppId());
        map.put("secret", weChatProperties.getAppSecret());
        map.put("code", code);
        map.put("grantType", weChatProperties.getGrantType());

        String response = restTemplate.getForObject(weChatProperties.getSessionHost() + urlString, String.class, map);

        ObjectMapper objectMapper = new ObjectMapper();
        WxCodeResponse wxCodeResponse;
        try {
            wxCodeResponse = objectMapper.readValue(response, WxCodeResponse.class);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            wxCodeResponse = null;
            e.printStackTrace();
        }

        LOG.info(wxCodeResponse.toString());
        if (null == wxCodeResponse) {
            throw new RuntimeException("调用微信接口失败");
        }
        if (wxCodeResponse.getErrcode() != null) {
            throw new RuntimeException(wxCodeResponse.getErrMsg());
        }

        return wxCodeResponse;
    }

    @Override
    public void updateCustomer(Customer customer) {
        EntityWrapper<Customer> customerEntityWrapper = new EntityWrapper<>();
        customerEntityWrapper.eq("OPENID", AppContext.getCurrentUserWechatOpenId());
        customerEntityWrapper.eq("STATUS", UserStatus.OK.getCode());
        customerEntityWrapper.eq("IS_DELETE", 0);
        Customer customerExist = customerService.selectOne(customerEntityWrapper);
        customerExist.setUpdateTime(DateUtil.getCurrentDateTime());
        customerExist.setGender(customer.getGender());
        customerExist.setOpenid(customer.getOpenid());
        customerExist.setEmail(customer.getEmail());
        customerExist.setPhone(customer.getPhone());
        customerExist.setCountry(customer.getCountry());
        customerExist.setProvince(customer.getProvince());
        customerExist.setCity(customer.getCity());
        customerExist.setAvatarUrl(customer.getAvatarUrl());
        customer.setStatus(UserStatus.OK.getCode());
        customerService.updateById(customerExist);
    }

    @Override
    public DataResponse queryUserByOpenId(String openid) {
        Map<String, Object> map = new HashMap<>();
        EntityWrapper<Customer> customerEntityWrapper = new EntityWrapper<>();
        if(openid.equals(AppContext.getCurrentUserWechatOpenId())){
            customerEntityWrapper.eq("OPENID", AppContext.getCurrentUserWechatOpenId());
            customerEntityWrapper.eq("STATUS", UserStatus.OK.getCode());
            customerEntityWrapper.eq("IS_DELETE", 0);
            Customer customer = customerService.selectOne(customerEntityWrapper);
            if(customer != null){
                map.put("customer", customer);
            }else{
                return DataResponse.fail("没有查询到用户！");
            }
        }
        return DataResponse.success(map);
    }
}
