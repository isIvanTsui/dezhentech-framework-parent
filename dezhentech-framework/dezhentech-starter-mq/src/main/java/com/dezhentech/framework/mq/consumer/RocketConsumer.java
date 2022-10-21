package com.dezhentech.framework.mq.consumer;

import com.dezhentech.framework.mq.constants.MqConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * @description: Rocket消费者
 * @title: com.dezhentech.framework.mq.consumer.RocketConsumer
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/10/21 05:29:57
 * @version: 1.0.0
 **/
@ConditionalOnProperty(prefix = "dezhen.mq", name = "type", havingValue = MqConstants.ROCKET)
public class RocketConsumer {
}
