package com.eis.trader.controller;

import com.eis.trader.dto.OrderDTO;
import com.eis.trader.entity.Orderbook;
import com.eis.trader.entity.ROrderbook;
import com.eis.trader.enums.OrderType;
import com.eis.trader.form.InstrumentForm;
import com.eis.trader.service.OrderService;
import com.eis.trader.util.ProtostuffUtils;
import com.eis.trader.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Created by kaclarpt on 2019/5/30
 */
@RestController
@Slf4j
public class OrderController {

    private final OrderService orderService;

    private final RedisUtils redisUtils;

    @Autowired
    public OrderController(OrderService orderService, RedisUtils redisUtils) {
        this.orderService = orderService;
        this.redisUtils = redisUtils;
    }

    @PostMapping("/sendOrder")
    public ResponseEntity<String> sendOrder(@RequestBody OrderDTO orderDTO) {
        orderService.transferOrder(ProtostuffUtils.serialize(orderDTO));
        log.info(orderDTO.toString());
        return new ResponseEntity<>(orderDTO.toString(), HttpStatus.OK);
    }

    @PostMapping("/showDetail")
    @ResponseBody
    public ROrderbook showDetail(Long brokerId, Long instrumentId) {
        String bookId = "B" + String.valueOf(brokerId) + "I" + String.valueOf(instrumentId);
        System.out.println(redisUtils.get("B1I1"));
        return null;
    }
}
