package com.eis.trader.rabbitmq;

import com.eis.trader.util.ProtostuffUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by kaclarpt on 2019/5/29
 */
@Component
public class Receiver {
    @RabbitListener(queues = "broker")
    public void process(byte[] data) throws Exception {
        String s = ProtostuffUtils.deserialize(data, String.class);
        System.out.println("接收到" + s);
    }
}
