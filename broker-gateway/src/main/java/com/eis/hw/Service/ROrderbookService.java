package com.eis.hw.Service;

import com.eis.hw.Model.Entity.Orderitem;
import com.eis.hw.Model.RedisEntity.ROrderbook;

public interface ROrderbookService {
    public void save(String s,ROrderbook rOrderbook);
    public ROrderbook get(String s);
    public void insertOrderitem(String bookId,String side,int price,int vol,Orderitem orderitem);
    public void insertStopOrderitem(String bookId,String side,int stopPrice,int vol,Orderitem orderitem);
    public void checkStop(String bookId);
    public int consumeLimit(String bookId,String side,int price,int vol,Integer traderId);
    public int consumeMarket(String bookId,String side,int vol,Integer trader_id);
    public boolean consumeStop(String bookId,String side,int stopPrice,int vol,Integer traderId);
    public int cancel(Integer orderId);
}
