package com.eis.trader.service.impl;

import com.eis.trader.service.OrderService;
import com.eis.trader.util.ProtostuffUtils;
import com.eis.trader.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kaclarpt on 2019/5/29
 */
@Service
public class OrderServiceImpl implements OrderService {
    private static final String topicExchangeName="exchange";

    private final RabbitTemplate rabbitTemplate;

    private final RedisUtils redisUtils;

    @Autowired
    public OrderServiceImpl(RabbitTemplate rabbitTemplate, RedisUtils redisUtils) {
        this.rabbitTemplate = rabbitTemplate;
        this.redisUtils = redisUtils;
    }

    @Override
    public boolean transferOrder(byte[] data) {
        rabbitTemplate.convertAndSend(topicExchangeName, "trader", data);
        return true;
    }
}
