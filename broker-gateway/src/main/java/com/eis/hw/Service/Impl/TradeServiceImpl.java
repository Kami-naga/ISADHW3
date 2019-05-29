package com.eis.hw.Service.Impl;

import com.eis.hw.DAO.TradeRepository;
import com.eis.hw.Model.Entity.Trade;
import com.eis.hw.Service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeRepository tradeRepository;


    @Override
    public void save(Integer instrumentId, double price, int qty, Integer initiatorId, Integer comple, String ini_buy) {
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
