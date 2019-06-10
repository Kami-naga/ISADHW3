package com.eis.hw.service.Impl;

import com.eis.hw.dao.InstrumentRepository;
import com.eis.hw.model.entity.Instrument;
import com.eis.hw.service.InstrumentService;
import com.eis.hw.util.ProtostuffUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by kaclarpt on 2019/6/9
 */
@Service
public class InstrumentServiceImpl implements InstrumentService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void publishInstrument(Instrument instrument) {
        String topicExchangeName = "exchange";
        String queueName = "instrument";
        rabbitTemplate.convertAndSend(topicExchangeName, queueName, ProtostuffUtils.serialize(instrument));
    }
}
