package com.eis.trader.rabbitmq;

import com.eis.trader.entity.Orderbook;
import com.eis.trader.util.ProtostuffUtils;
import com.eis.trader.util.RedisUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by kaclarpt on 2019/5/29
 */
@Component
public class Receiver {
    final
    RedisUtils redisUtils;

    @Autowired
    public Receiver(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @RabbitListener(queues = "broker")
    public void process(byte[] data) throws Exception {
        String s = ProtostuffUtils.deserialize(data, String.class);
        System.out.println("接收到" + s);
    }

    @RabbitListener(queues = "orderBook")
    public void saveOrderBook(byte[] data) throws Exception {
        Orderbook orderbook = ProtostuffUtils.deserialize(data, Orderbook.class);
        redisUtils.set("orderbook", orderbook);
    }
}
