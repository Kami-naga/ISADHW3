package com.eis.trader.service;

import com.eis.trader.domain.Broker;
import com.eis.trader.domain.Instrument;

import java.util.List;

/**
 * Created by kaclarpt on 2019/6/9
 */
public interface InstrumentService {
    List<Instrument> findInstrumentsByProductId(Long productId);
    List<Instrument> findInstrumentsByBroker(Broker broker);
    List<Instrument> findInstrumentsByProductIdAndBrokerId(Long productId, Long brokerId);
    void addInstrument(Long productId, Long brokerId, String periodT);
}
