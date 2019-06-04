package com.eis.trader.controller;

import com.eis.trader.enums.OrderType;
import com.eis.trader.form.InstrumentForm;
import com.eis.trader.service.OrderService;
import com.eis.trader.util.ProtostuffUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created by kaclarpt on 2019/5/30
 */
@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/market")
    public ResponseEntity<String> sendOrder(@RequestBody InstrumentForm instrumentForm) {
        orderService.transferOrder(ProtostuffUtils.serialize(instrumentForm));
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/showDetail")
    public ResponseEntity<String> showDetail(@RequestBody InstrumentForm instrumentForm) {
        return new ResponseEntity<>("success", HttpStatus.OK);

    }
}
