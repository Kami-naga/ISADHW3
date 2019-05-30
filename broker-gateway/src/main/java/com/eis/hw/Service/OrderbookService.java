package com.eis.hw.Service;

import com.eis.hw.Model.Entity.Orderbook;
import com.eis.hw.Model.RedisEntity.ROrderbook;

public interface OrderbookService {

    Orderbook construct(ROrderbook rOrderbook);

    void showOrderbook(Orderbook orderbook);

    boolean transferOrder(byte[] data);

}
