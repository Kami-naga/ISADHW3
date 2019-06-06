package com.eis.hw.service;

import com.eis.hw.model.entity.Orderbook;
import com.eis.hw.model.redisentity.ROrderbook;

public interface OrderbookService {

    Orderbook construct(ROrderbook rOrderbook);

    void showOrderbook(Orderbook orderbook);

    boolean transferOrder(byte[] data);

}
