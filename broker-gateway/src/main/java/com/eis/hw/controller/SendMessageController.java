package com.eis.hw.controller;

import com.eis.hw.model.entity.Orderbook;
import com.eis.hw.service.OrderbookService;
import com.eis.hw.util.ProtostuffUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by kaclarpt on 2019/5/30
 */
@RestController
@Slf4j
public class SendMessageController {

    private final OrderbookService orderbookService;

    @Autowired
    public SendMessageController(OrderbookService orderbookService) {
        this.orderbookService = orderbookService;
    }

    @PostMapping("/sendMessage")
    @ResponseBody
    public String sendMessage(@RequestBody Orderbook orderbook) {
        byte[] data = ProtostuffUtils.serialize(orderbook);
        log.info("Sending orderbook");
        orderbookService.transferOrder(data);
        return "success";
    }

    @GetMapping("/sendMessageTest")
    @ResponseBody
    public String sendMessageTest() {
        byte[] data = ProtostuffUtils.serialize("abc");
        orderbookService.transferOrder(data);
        return "success";
    }
}
