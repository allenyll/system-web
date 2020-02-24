package com.sw.base.controller.market;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.cache.util.DataResponse;
import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.market.MessageServiceImpl;
import com.sw.common.entity.market.Message;
import com.sw.common.util.MapUtil;
import com.sw.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system-web/message")
public class MessageController extends BaseController<MessageServiceImpl,Message> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageServiceImpl messageService;

    @ResponseBody
    @RequestMapping(value = "getMessageListByType", method = RequestMethod.POST)
    public DataResponse getMessageListByType(@RequestBody Map<String, Object> params){
        Map<String, Object> result = new HashMap<>();
        String msgType = MapUtil.getString(params, "msgType");
        if (StringUtil.isEmpty(msgType)) {
            msgType = "SW2701";
        }
        EntityWrapper<Message> wrapper = new EntityWrapper<>();
        wrapper.eq("TYPE", msgType);
        wrapper.eq("IS_DELETE", 0);
        List<Message> list = messageService.selectList(wrapper);
        result.put("messageList", list);
        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "getMessageById", method = RequestMethod.POST)
    public DataResponse getMessageById(@RequestBody Map<String, Object> params){
        Map<String, Object> result = new HashMap<>();
        String id = MapUtil.getString(params, "id");
        if (StringUtil.isEmpty(id)) {
            return DataResponse.fail("查询通知的ID不能为空");
        }
        Message message = messageService.selectById(id);
        result.put("message", message);
        return DataResponse.success(result);
    }
}
