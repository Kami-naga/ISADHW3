package com.eis.hw.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by kaclarpt on 2019/6/5
 */
@Component
public class Sender {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public Sender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void convertAndSend(String topicExchangeName, String sendQueueName, byte[] data) {
        rabbitTemplate.convertAndSend(topicExchangeName, sendQueueName, data);
    }

}
