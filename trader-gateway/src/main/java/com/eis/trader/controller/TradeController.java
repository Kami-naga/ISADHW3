package com.eis.trader.controller;

import com.eis.trader.form.InstrumentForm;
import com.eis.trader.service.OrderService;
import com.eis.trader.util.ProtostuffUtils;
import com.eis.trader.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class TradeController {

    private final OrderService orderService;

    @Autowired
    public TradeController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/makeOrder")
    @ResponseBody
    public String makeOrder(@RequestBody InstrumentForm instrumentForm, HttpServletRequest request) {
        byte[] data = ProtostuffUtils.serialize(instrumentForm);
        log.info("Sending OrderMain...");
        orderService.transferOrder(data);
        return "success";
    }
}
