package com.eis.trader.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.eis.trader.controller.ProductController;
import com.eis.trader.domain.Instrument;
import com.eis.trader.service.ProductService;
import com.eis.trader.util.ProtostuffUtils;
import com.eis.trader.util.SerializeUtil;
import com.eis.trader.vo.OrderbookVO;
import com.eis.trader.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Component;

/**
 * Created by kaclarpt on 2019/5/29
 */
@Component
@Slf4j
public class Receiver {

    @Autowired
    private RedisConnection redisConnection;

    @Autowired
    private WebSocketServer webSocketServer;

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "orderBook")
    public void saveOrderBook(byte[] data) throws Exception {
        OrderbookVO orderbookVO = ProtostuffUtils.deserialize(data, OrderbookVO.class);
        log.info(orderbookVO.toString());
        redisConnection.set((orderbookVO.getOrderbookId()+"T").getBytes(), SerializeUtil.serialize(orderbookVO));
        WebSocketServer.sendInfo(JSONObject.toJSONString(orderbookVO), null);
    }

    @RabbitListener(queues = "instrument")
    public void updateInstrument(byte[] data) throws Exception {
        Instrument instrument = ProtostuffUtils.deserialize(data, Instrument.class);
        log.info(instrument.toString());
        productService.addInstrument(instrument);
    }
}
