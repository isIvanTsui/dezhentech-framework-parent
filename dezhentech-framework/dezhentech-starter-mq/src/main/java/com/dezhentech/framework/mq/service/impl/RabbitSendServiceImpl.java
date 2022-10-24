package com.dezhentech.framework.mq.service.impl;

import com.dezhentech.framework.mq.constants.MqConstants;
import com.dezhentech.framework.mq.service.ISendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * @description: Rabbit发送消息Service
 * @title: com.dezhentech.framework.mq.service.impl.RabbitSendService
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/10/21 05:17:01
 * @version: 1.0.0
 **/
@ConditionalOnProperty(prefix = "dezhen.mq", name = "type", havingValue = MqConstants.RABBIT)
@Slf4j
public class RabbitSendServiceImpl implements ISendService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${dezhen.mq.rabbitmq.topic.name}")
    private String confTopic;

    @Value("${dezhen.mq.rabbitmq.exchange.name}")
    private String exchangeName;

    @Override
    public void send(String topic, String jsonValue, String tagId) {
        if (topic.equals(confTopic)) {
            rabbitTemplate.convertAndSend(exchangeName, confTopic, jsonValue);
        } else {
            log.error("RabbitSendMqServiceImpl send topic error! topic:{},confTopic:{}", topic, confTopic);
        }
    }

    @Override
    public void send(String topic, String jsonValue) {
        send(topic, jsonValue, null);
    }
}
