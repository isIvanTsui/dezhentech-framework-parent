package com.dezhentech.framework.mq.consumer;

import com.dezhentech.framework.mq.constants.MqConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * @description: Rabbit消费端
 * @title: com.dezhentech.framework.mq.consumer.RabbitConsumer
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/10/21 05:28:47
 * @version: 1.0.0
 **/
@ConditionalOnProperty(prefix = "dezhen.mq", name = "type", havingValue = MqConstants.RABBIT)
public class RabbitConsumer {

}
