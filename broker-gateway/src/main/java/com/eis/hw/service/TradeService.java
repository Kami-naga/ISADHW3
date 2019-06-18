package com.eis.hw.service;

import com.eis.hw.model.entity.Trade;

import java.util.List;

public interface TradeService {
    void save(Long instrumentId, double price, int qty, Long initiatorId, Long comple, String ini_buy);
    List<Trade> getAllTrades();
}
