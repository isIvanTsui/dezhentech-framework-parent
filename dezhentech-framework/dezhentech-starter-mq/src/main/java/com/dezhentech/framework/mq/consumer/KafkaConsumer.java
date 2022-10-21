package com.dezhentech.framework.mq.consumer;

import com.dezhentech.framework.mq.constants.MqConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * @description: Kafka消费者
 * @title: com.dezhentech.framework.mq.consumer.KafkaConsumer
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/10/21 05:30:24
 * @version: 1.0.0
 **/
@ConditionalOnProperty(prefix = "dezhen.mq", name = "type", havingValue = MqConstants.KAFKA)
public class KafkaConsumer {
}
