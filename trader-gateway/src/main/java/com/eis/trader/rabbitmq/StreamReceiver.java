package com.eis.trader.rabbitmq;

import com.eis.trader.form.InstrumentForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * Created by kaclarpt on 2019/5/29
 */
@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {



//    @StreamListener("input")
////    @SendTo("output")
////    public Object processInput(Object message) {
////        log.info("StreamReceiver: {}", message);
////        return message;
////    }
    @StreamListener("input")
    @SendTo("input2")
    public String processInput(InstrumentForm instrumentForm) {
        log.info("StreamReceiver: {}", instrumentForm);
        return "received.";
    }

    @StreamListener("input2")
    public void process(String message) {
        log.info("StreamReceiver: {}", message);
    }
}
