package com.dezhentech.framework.mq.service.impl;

import cn.hutool.core.util.StrUtil;
import com.dezhentech.framework.mq.constants.MqConstants;
import com.dezhentech.framework.mq.service.ISendService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @description: Kafka发送消息Service
 * @title: com.dezhentech.framework.mq.service.impl.KafkaSendService
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/10/21 05:17:53
 * @version: 1.0.0
 **/
@ConditionalOnProperty(prefix = "dezhen.mq", name = "type", havingValue = MqConstants.KAFKA)
public class KafkaSendServiceImpl implements ISendService {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Value("${dezhen.mq.kafka.tagId.key}")
    private String tagIdKey;

    @Override
    public void send(String topic, String jsonValue, String tagId) {
        if (StrUtil.isNotBlank(tagId)) {
            List<Header> headers = Arrays.asList(new RecordHeader(tagIdKey, tagId.getBytes(StandardCharsets.UTF_8)));
            kafkaTemplate.send(new ProducerRecord(topic, null, null, null, jsonValue, headers));
        } else {
            kafkaTemplate.send(topic, jsonValue);
        }
    }

    @Override
    public void send(String topic, String jsonValue) {
        send(topic, jsonValue, null);
    }
}
