package com.eis.hw.Service;

import com.eis.hw.Model.Entity.Orderbook;
import com.eis.hw.Model.RedisEntity.ROrderbook;

public interface OrderbookService {
    public Orderbook construct(ROrderbook rOrderbook);
    public void showOrderbook(Orderbook orderbook);
}
