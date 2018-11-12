package com.sw.wechat.controller;

import com.sw.auth.entity.JwtAuthenticationRequest;
import com.sw.auth.entity.JwtAuthenticationResponse;
import com.sw.common.entity.Customer;
import com.sw.common.util.DataResponse;
import com.sw.wechat.entity.WxUserInfo;
import com.sw.wechat.service.IWeChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@Api(description = "微信API")
@RestController
@RequestMapping(value = "wx")
public class WeChatController {

    private static final Logger LOG = LoggerFactory.getLogger(WeChatController.class);

    @Autowired
    IWeChatService weChatService;

    @ApiOperation(value = "微信授权" ,  notes="微信授权")
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<?> createWxAuthenticationToken(@RequestBody WxUserInfo user) throws AuthenticationException {
        final String token = weChatService.auth(user.getCode());

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @RequestMapping(value = "/wxLogin", method = RequestMethod.POST)
    public void wxLogin(@RequestBody Customer customer){
        weChatService.login(customer);
    }

    @ResponseBody
    @RequestMapping(value = "queryUserByOpenId", method = RequestMethod.POST)
    public DataResponse queryUserByOpenId(@RequestParam String openid){
        LOG.info("开始调用查询微信用户openid:" + openid);
        DataResponse dataResponse;
        try {
            dataResponse = weChatService.queryUserByOpenId(openid);
        } catch (Exception e) {
            LOG.error("查询微信用户异常");
            return DataResponse.fail("查询微信用户异常");
        }
        return dataResponse;
    }

    @RequestMapping(value = "/updateCustomer", method = RequestMethod.POST)
    public void updateCustomer(@RequestBody Customer customer){
        weChatService.updateCustomer(customer);
    }

}
