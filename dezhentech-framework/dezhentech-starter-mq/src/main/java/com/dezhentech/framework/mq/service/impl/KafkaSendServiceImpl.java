package com.dezhentech.framework.mq.service.impl;

import com.dezhentech.framework.mq.constants.MqConstants;
import com.dezhentech.framework.mq.service.ISendService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * @description: Kafka发送消息Service
 * @title: com.dezhentech.framework.mq.service.impl.KafkaSendService
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/10/21 05:17:53
 * @version: 1.0.0
 **/
@ConditionalOnProperty(prefix = "dezhen.mq", name = "type", havingValue = MqConstants.KAFKA)
public class KafkaSendServiceImpl implements ISendService {
    @Override
    public void send(String topic, String jsonValue, String tagId) {

    }

    @Override
    public void send(String topic, String jsonValue) {

    }
}
