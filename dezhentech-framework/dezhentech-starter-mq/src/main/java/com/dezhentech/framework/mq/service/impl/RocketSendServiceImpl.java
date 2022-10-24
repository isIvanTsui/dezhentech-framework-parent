package com.dezhentech.framework.mq.service.impl;

import cn.hutool.core.util.StrUtil;
import com.dezhentech.framework.mq.constants.MqConstants;
import com.dezhentech.framework.mq.service.ISendService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @description: rocket发送消息Service
 * @title: com.dezhentech.framework.mq.service.impl.RocketSendService
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/10/21 05:18:38
 * @version: 1.0.0
 **/
@ConditionalOnProperty(prefix = "dezhen.mq", name = "type", havingValue = MqConstants.ROCKET)
public class RocketSendServiceImpl implements ISendService {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void send(String topic, String jsonValue, String tagId) {
        if (StrUtil.isNotBlank(tagId)) {
            topic = topic + ":" + tagId;
        }
        send(topic, jsonValue);
    }

    @Override
    public void send(String topic, String jsonValue) {
        rocketMQTemplate.send(topic, MessageBuilder.withPayload(jsonValue).build());
    }
}
