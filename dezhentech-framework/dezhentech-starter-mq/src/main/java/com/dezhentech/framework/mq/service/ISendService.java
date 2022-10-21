package com.dezhentech.framework.mq.service;


/**
 * @description: 发送消息到消息队列
 * @title: com.dezhentech.framework.mq.service.SendMqService
 * @author: yingfan.cui@dezhentech.com
 * @create: 2022/10/21 05:14:40
 * @version: 1.0.0
 **/
public interface ISendService {
    /**
     * 发送消息
     *
     * @param topic
     * @param jsonValue
     * @param tagId
     */
    void send(String topic, String jsonValue, String tagId);


    /**
     * 发送消息
     *
     * @param topic
     * @param jsonValue
     */
    void send(String topic, String jsonValue);

}
