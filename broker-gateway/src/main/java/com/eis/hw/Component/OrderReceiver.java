package com.eis.hw.Component;


import com.eis.hw.Util.ProtostuffUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by kaclarpt on 2019/5/29
 */
@Component
public class OrderReceiver {
    @RabbitListener(queues = "trader")
    public void process(byte[] data) throws Exception {
        OrderMain orderMain = ProtostuffUtils.deserialize(data, OrderMain.class);
        System.out.println("接收到" + orderMain.toString());
    }
}
