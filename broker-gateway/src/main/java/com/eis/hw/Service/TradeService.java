package com.eis.hw.Service;

public interface TradeService {
    public void save(Integer instrumentId,double price,int qty,Integer initiatorId,Integer comple,String ini_buy);
}
