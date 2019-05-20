package com.eis.trader.controller;

import com.eis.trader.domain.Instrument;
import com.eis.trader.domain.OrderMain;
import com.eis.trader.util.ProtostuffUtils;
import com.eis.trader.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/orders")
public class TradeController {

    private static final String topicExchangeName="exchange";

    private static final Logger logger = LoggerFactory.getLogger(TradeController.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RedisUtils redisUtils;

    @PostMapping("/test")
    public void convert(@RequestBody Instrument instrument, HttpServletRequest request) {
        byte[] data = ProtostuffUtils.serialize(instrument);
        logger.info("Sending message...");
        rabbitTemplate.convertAndSend(topicExchangeName, "trader", data);
    }

    @PostMapping("/makeOrder")
    @ResponseBody
    public String makeOrder(@RequestBody OrderMain orderMain, HttpServletRequest request) {
        byte[] data = ProtostuffUtils.serialize(orderMain);
        redisUtils.set(orderMain.getOrderId().toString(), orderMain);
        logger.info("Sending OrderMain...");
        rabbitTemplate.convertAndSend(topicExchangeName, "trader", data);
        return "success";
    }
}
