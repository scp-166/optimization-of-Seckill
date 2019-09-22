package com.nekosighed.miaosha.mq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProducerService {
    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);
    // 生产者
    private DefaultMQProducer producer;

    @Value("${rocketmq.nameserver.addr}")
    private String nameServerAddr;

    @Value("${rocketmq.topicname}")
    private String topicName;

    @PostConstruct
    private void init() {
        // 生产者组的group名无所谓
        producer = new DefaultMQProducer("producer_group");
        producer.setNamesrvAddr(nameServerAddr);
        try {
            producer.start();
            //  重试操作0
            producer.setRetryTimesWhenSendAsyncFailed(0);
        } catch (MQClientException e) {
            e.printStackTrace();
            logger.warn("生产者启动失败: 原因是: " + e.toString());
        }
    }

    /**
     * 异步发送数据
     *
     * @param itemId
     * @param amount
     * @return
     */
    public void asyncSendInfo(Integer itemId, Integer amount) {
        Map<String, Object> bodyMap = new HashMap<>(2);
        bodyMap.put("itemId", itemId);
        bodyMap.put("amount", amount);
        try {
            Message message = new Message(topicName,   // topic name
                    "demo",     // tag
                    JSON.toJSONString(bodyMap).getBytes(RemotingHelper.DEFAULT_CHARSET));  // byte[] value

            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    logger.info(sendResult.toString());
                }

                @Override
                public void onException(Throwable throwable) {
                    throwable.printStackTrace();
                }
            });

        } catch (MQClientException | RemotingException | InterruptedException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
