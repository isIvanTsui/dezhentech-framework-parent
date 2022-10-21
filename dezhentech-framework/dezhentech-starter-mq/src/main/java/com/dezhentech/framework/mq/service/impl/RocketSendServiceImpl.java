package com.dezhentech.framework.mq.service.impl;

import com.dezhentech.framework.mq.constants.MqConstants;
import com.dezhentech.framework.mq.service.ISendService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * @description: rocket发送消息Service
 * @title: com.dezhentech.framework.mq.service.impl.RocketSendService
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/10/21 05:18:38
 * @version: 1.0.0
 **/
@ConditionalOnProperty(prefix = "dezhen.mq", name = "type", havingValue = MqConstants.ROCKET)
public class RocketSendServiceImpl implements ISendService {
    @Override
    public void send(String topic, String jsonValue, String tagId) {

    }

    @Override
    public void send(String topic, String jsonValue) {

    }
}
