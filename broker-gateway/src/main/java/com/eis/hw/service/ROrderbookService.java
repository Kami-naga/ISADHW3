package com.eis.hw.service;

import com.eis.hw.enums.OrderSide;
import com.eis.hw.model.entity.Orderitem;
import com.eis.hw.model.redisentity.ROrderbook;

public interface ROrderbookService {
    void init();
    void save(String s,ROrderbook rOrderbook);
    ROrderbook get(String s);
    void insertOrderitem(String bookId, OrderSide side, int price, int vol, Orderitem orderitem);
    void insertStopOrderitem(String bookId, OrderSide side,int stopPrice,int vol,Orderitem orderitem);
    void checkStop(String bookId);
    int consumeLimit(String bookId, OrderSide side, int price, int vol, Long traderId);
    int consumeMarket(String bookId, OrderSide side, int vol, Long trader_id);
    boolean consumeStop(String bookId, OrderSide side, int stopPrice, int vol, Long traderId);
    int cancel(Long orderId);
    boolean transferOrder(byte[] data);
}
