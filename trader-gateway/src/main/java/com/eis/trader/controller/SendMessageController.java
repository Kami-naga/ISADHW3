package com.eis.trader.controller;

import com.eis.trader.form.InstrumentForm;
import com.eis.trader.rabbitmq.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by kaclarpt on 2019/5/29
 */
@RestController
public class SendMessageController {

    @Autowired
    private StreamClient streamClient;

    @GetMapping("/sendMessage")
    public void process() {
        String message = "now " + new Date();
        InstrumentForm instrumentForm = new InstrumentForm();
        instrumentForm.setQuantity(100);
        streamClient.output().send(MessageBuilder.withPayload(instrumentForm).build());
    }
}
