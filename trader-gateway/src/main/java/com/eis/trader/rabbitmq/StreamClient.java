package com.eis.trader.rabbitmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

/**
 * Created by kaclarpt on 2019/5/29
 */
public interface StreamClient {

    @Input("input")
    SubscribableChannel input();

    @Output("output")
    MessageChannel output();

    @Input("input2")
    SubscribableChannel input2();

    @Output("output2")
    MessageChannel output2();
}
