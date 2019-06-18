package com.eis.trader.controller;

import com.eis.trader.domain.Broker;
import com.eis.trader.domain.Instrument;
import com.eis.trader.service.InstrumentService;
import com.eis.trader.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaclarpt on 2019/6/10
 */
@RestController
@RequestMapping("/api/broker")
public class BrokerController {

    @Autowired
    private InstrumentService instrumentService;

    @PostMapping("/byProduct")
    @ResponseBody
    public List<Broker> getBrokersByProduct(Long productId) {
        List<Instrument> instrumentList = instrumentService.findInstrumentsByProductId(productId);
        List<Broker> brokerList = new ArrayList<>();
        for (Instrument instrument: instrumentList) {
            Broker broker = instrument.getBroker();
            List<Instrument> b_i = broker.getInstruments();
            if (!brokerList.contains(broker))
                brokerList.add(broker);
        }
//        for (Broker broker: brokerList) {
//            broker.setInstruments(instrumentService.findInstrumentsByBroker(broker));
//        }
        return brokerList;
    }
}
