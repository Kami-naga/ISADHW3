package com.eis.trader.controller;

import com.eis.trader.domain.Instrument;
import com.eis.trader.util.ProtostuffUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/orders")
public class TradeController {
    static final String topicExchangeName="exchange";

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostMapping("/test")
    public void convert(@RequestBody Instrument instrument, HttpServletRequest request) {
        byte[] data = ProtostuffUtils.serialize(instrument);
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(topicExchangeName, "trader", data);
    }
}
