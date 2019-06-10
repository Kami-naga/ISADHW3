package com.eis.hw.service.Impl;

import com.eis.hw.dao.BrokerRepository;
import com.eis.hw.dao.InstrumentRepository;
import com.eis.hw.dao.ProductRepository;
import com.eis.hw.model.entity.Instrument;
import com.eis.hw.service.InstrumentService;
import com.eis.hw.service.ROrderbookService;
import com.eis.hw.util.ProtostuffUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by kaclarpt on 2019/6/9
 */
@Service
@Slf4j
public class InstrumentServiceImpl implements InstrumentService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrokerRepository brokerRepository;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private ROrderbookService rOrderbookService;

    @Override
    public void publishInstrument(Instrument instrument) {
        String topicExchangeName = "exchange2";
        String queueName = "instrument";
        log.info(instrument.toString());
        rabbitTemplate.convertAndSend(topicExchangeName, queueName, ProtostuffUtils.serialize(instrument));
    }

    @Override
    public void addInstrument(Long productId, Long brokerId, String periodT) {
        Instrument instrument = new Instrument();
        instrument.setPeriodT(periodT);
        instrument.setProduct(productRepository.findById(productId).orElse(null));
        instrument.setBroker(brokerRepository.findById(brokerId).orElse(null));
        log.info(periodT);
        instrumentRepository.save(instrument);
        publishInstrument(instrument);
        rOrderbookService.createOrderBook("B"+String.valueOf(brokerId)+"I"+String.valueOf(instrument.getInstrumentId()));

    }
}
