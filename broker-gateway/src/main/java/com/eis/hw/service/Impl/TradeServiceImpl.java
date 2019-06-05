package com.eis.hw.service.Impl;

import com.eis.hw.dao.InstrumentRepository;
import com.eis.hw.dao.TradeRepository;
import com.eis.hw.dao.TraderRepository;
import com.eis.hw.model.entity.Trade;
import com.eis.hw.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private TraderRepository traderRepository;

    @Override
    public void save(Long instrumentId, double price, int qty, Long initiatorId, Long comple, String ini_buy) {
        Trade trade = new Trade();
        trade.setInstrument(instrumentRepository.findById(instrumentId).get());
        trade.setPrice(price);
        trade.setQty(qty);
        trade.setInitiator(traderRepository.findById(initiatorId).get());
        trade.setCompletion(traderRepository.findById(comple).get());
        String buy = "s";
        if(ini_buy.equals("buy")){
            buy="b";
        }
        trade.setInitiatorBuy(buy);
        tradeRepository.save(trade);
    }
}
