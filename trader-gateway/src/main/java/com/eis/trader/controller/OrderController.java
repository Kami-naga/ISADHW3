package com.eis.trader.controller;

import com.eis.trader.dto.OrderDTO;
import com.eis.trader.entity.ROrderbook;
import com.eis.trader.service.OrderService;
import com.eis.trader.util.MyObjectInputStream;
import com.eis.trader.util.ProtostuffUtils;
import com.eis.trader.util.SerializeUtil;
import com.eis.trader.vo.OrderbookVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;


/**
 * Created by kaclarpt on 2019/5/30
 */
@RestController
@Slf4j
public class OrderController {

    private final OrderService orderService;


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisConnection redisConnection;


    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/sendOrder")
    public ResponseEntity<String> sendOrder(@RequestBody OrderDTO orderDTO) {
        orderService.transferOrder(ProtostuffUtils.serialize(orderDTO));
        log.info(orderDTO.toString());
        return new ResponseEntity<>(orderDTO.toString(), HttpStatus.OK);
    }

    @PostMapping("/showDetail")
    @ResponseBody
    public OrderbookVO showDetail(Long brokerId, Long instrumentId) {
        String bookId = "B" + String.valueOf(brokerId) + "I" + String.valueOf(instrumentId) + "T";
        byte[] rOrderbook_byte = redisConnection.get(bookId.getBytes());
        OrderbookVO orderbookVO = (OrderbookVO) SerializeUtil.unserialize(rOrderbook_byte);
        return orderbookVO;
    }
}
