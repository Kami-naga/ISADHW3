package com.eis.trader.rabbitmq;

import com.eis.trader.entity.ROrderbook;
import com.eis.trader.util.ProtostuffUtils;
import com.eis.trader.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by kaclarpt on 2019/5/29
 */
@Component
@Slf4j
public class Receiver {

    private final RedisUtils redisUtils;

    @Autowired
    public Receiver(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @RabbitListener(queues = "orderBook")
    public void saveOrderBook(byte[] data) throws Exception {
        ROrderbook rOrderbook = ProtostuffUtils.deserialize(data, ROrderbook.class);
        log.info(rOrderbook.toString());
        redisUtils.set(rOrderbook.getOrderBookId(),rOrderbook);
    }
}
