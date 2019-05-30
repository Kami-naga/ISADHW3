package com.eis.trader.rabbitmq;

import com.eis.trader.util.ProtostuffUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * Created by kaclarpt on 2019/5/29
 */
public class Receiver {
    @RabbitListener(queues = "broker")
    public void process(byte[] data) throws Exception {
    }
}
