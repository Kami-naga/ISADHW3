package com.eis.hw.service;

public interface TradeService {
    public void save(Long instrumentId, double price, int qty, Long initiatorId, Long comple, String ini_buy);
}
