package com.eis.hw.service;

import com.eis.hw.enums.OrderSide;
import com.eis.hw.model.entity.Orderitem;
import com.eis.hw.model.redisentity.ROrderbook;

public interface ROrderbookService {
    public void save(String s,ROrderbook rOrderbook);
    public ROrderbook get(String s);
    public void insertOrderitem(String bookId, OrderSide side, int price, int vol, Orderitem orderitem);
    public void insertStopOrderitem(String bookId, OrderSide side,int stopPrice,int vol,Orderitem orderitem);
    public void checkStop(String bookId);
    public int consumeLimit(String bookId, OrderSide side, int price, int vol, Long traderId);
    public int consumeMarket(String bookId, OrderSide side, int vol, Long trader_id);
    public boolean consumeStop(String bookId, OrderSide side, int stopPrice, int vol, Long traderId);
    public int cancel(Long orderId);
}
