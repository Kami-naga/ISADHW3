package com.eis.hw.service.Impl;

import com.eis.hw.dao.TradeRepository;
import com.eis.hw.model.entity.Trade;
import com.eis.hw.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeRepository tradeRepository;


    @Override
    public void save(Long instrumentId, double price, int qty, Long initiatorId, Long comple, String ini_buy) {
        Trade trade = new Trade();
        trade.setInstrumentId(instrumentId);
        trade.setPrice(price);
        trade.setQty(qty);
        trade.setInitiatorId(initiatorId);
        trade.setCompletionId(comple);
        String buy = "s";
        if(ini_buy.equals("buy")){
            buy="b";
        }
        trade.setInitiatorBuy(buy);
        tradeRepository.save(trade);
    }
}
