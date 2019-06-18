package com.eis.trader.service.impl;

import com.eis.trader.domain.Broker;
import com.eis.trader.domain.Instrument;
import com.eis.trader.domain.Product;
import com.eis.trader.repository.BrokerRepository;
import com.eis.trader.repository.InstrumentRepository;
import com.eis.trader.repository.ProductRepository;
import com.eis.trader.service.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kaclarpt on 2019/6/9
 */
@Service
public class InstrumentServiceImpl implements InstrumentService {

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrokerRepository brokerRepository;

    @Override
    public List<Instrument> findInstrumentsByProductId(Long productId) {
        return instrumentRepository.findAllByProduct(productRepository.findById(productId).orElse(null));
    }

    @Override
    public List<Instrument> findInstrumentsByBroker(Broker broker) {
        return instrumentRepository.findAllByBroker(broker);
    }

    @Override
    public List<Instrument> findInstrumentsByProductIdAndBrokerId(Long productId, Long brokerId) {
        return instrumentRepository.findAllByProductAndBroker(productRepository.findById(productId).orElse(null),brokerRepository.findById(brokerId).orElse(null));
    }

    @Override
    public void addInstrument(Long productId, Long brokerId, String periodT) {
        Instrument instrument = new Instrument();
        instrument.setProduct(productRepository.findById(productId).orElse(null));
        instrument.setBroker(brokerRepository.findById(brokerId).orElse(null));
        instrument.setPeriodT(periodT);
        instrumentRepository.save(instrument);
    }
}
