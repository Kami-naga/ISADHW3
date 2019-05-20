package com.eis.trader.component;

import com.eis.trader.domain.Instrument;
import com.eis.trader.domain.OrderMain;
import com.eis.trader.util.ProtostuffUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by kaclarpt on 2019/5/20
 */
@Component
public class Recevier {
    @RabbitListener(queues = "trader")
    public void process(byte[] data) throws Exception {
        OrderMain orderMain = ProtostuffUtils.deserialize(data, OrderMain.class);
        System.out.println("接收到" + orderMain.toString());
    }
}
