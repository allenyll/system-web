package com.sw.base.service.impl.market;

import org.springframework.stereotype.Service;

import com.sw.common.entity.market.Message;
import com.sw.base.mapper.market.MessageMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息表，包含通知，推送，私信等
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-12-25 18:51:28
 */
@Service("messageService")
public class MessageServiceImpl extends BaseService<MessageMapper,Message> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);
}