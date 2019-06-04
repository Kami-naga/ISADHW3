package com.eis.trader.service;

import com.eis.trader.entity.Orderbook;
import org.springframework.stereotype.Service;

/**
 * Created by kaclarpt on 2019/5/29
 */

public interface OrderService {
    boolean transferOrder(byte[] data);

    Orderbook getOrderBook();
}
