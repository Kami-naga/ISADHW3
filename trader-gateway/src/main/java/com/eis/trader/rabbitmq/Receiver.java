package com.eis.trader.rabbitmq;

import com.eis.trader.entity.ROrderbook;
import com.eis.trader.util.ProtostuffUtils;
import com.eis.trader.util.RedisUtils;
import com.eis.trader.util.SerializeUtil;
import com.eis.trader.vo.OrderbookVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by kaclarpt on 2019/5/29
 */
@Component
@Slf4j
public class Receiver {

    @Autowired
    private RedisConnection redisConnection;

    @RabbitListener(queues = "orderBook")
    public void saveOrderBook(byte[] data) throws Exception {
        OrderbookVO orderbookVO = ProtostuffUtils.deserialize(data, OrderbookVO.class);
        log.info(orderbookVO.toString());
        redisConnection.set((orderbookVO.getOrderbookId()+"T").getBytes(), SerializeUtil.serialize(orderbookVO));
    }
}
