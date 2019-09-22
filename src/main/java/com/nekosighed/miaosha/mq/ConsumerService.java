package com.nekosighed.miaosha.mq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class ConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);
    // 消费者
    private DefaultMQPushConsumer consumer;

    @Value("${rocketmq.nameserver.addr}")
    private String nameServerAddr;

    @Value("${rocketmq.topicname}")
    private String topicName;

    private static Integer i = 0;

    @PostConstruct
    public void init() {
        consumer = new DefaultMQPushConsumer("consumer_group");
        consumer.setNamesrvAddr(nameServerAddr);
        try {
            // 监听指定 topic， 类型为任意
            consumer.subscribe(topicName, "*");
            // 注册消息处理监听器
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    Message message = list.get(0);
                    // 可以根据tags判断不同的消息
                    String tags = message.getTags();
                    System.out.println(tags);
                    String jsonString = new String(message.getBody());
                    Map<String, Object> map = (Map<String, Object>) JSON.parseObject(jsonString, Map.class);
                    Integer itemId = (Integer) map.get("itemId");
                    Integer amount = (Integer) map.get("amount");
                    if (Objects.isNull(itemId) || Objects.isNull(amount)){
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    } else {
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                }
            });
            // 启动 consumer
            consumer.start();
        } catch (MQClientException e) {
            logger.warn("消费者指定topic失败，原因是: " + e.toString());
            e.printStackTrace();
        }
    }
}
