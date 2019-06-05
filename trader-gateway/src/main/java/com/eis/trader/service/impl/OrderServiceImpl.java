package com.eis.trader.service.impl;

import com.eis.trader.entity.Orderbook;
import com.eis.trader.rabbitmq.Sender;
import com.eis.trader.service.OrderService;
import com.eis.trader.util.RedisUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kaclarpt on 2019/5/29
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final RabbitTemplate rabbitTemplate;

    private final RedisUtils redisUtils;

    @Autowired
    private Sender sender;

    @Autowired
    public OrderServiceImpl(RabbitTemplate rabbitTemplate, RedisUtils redisUtils) {
        this.rabbitTemplate = rabbitTemplate;
        this.redisUtils = redisUtils;
    }

    @Override
    public boolean transferOrder(byte[] data) {
        String topicExchangeName = "exchange";
        String sendQueueName = "trader";
        sender.convertAndSend(topicExchangeName,sendQueueName,data);
        return true;
    }

    @Override
    public Orderbook getOrderBook() {
        String receiveQueueName = "orderBook";
        return (Orderbook) rabbitTemplate.receiveAndConvert(receiveQueueName);
    }
}
