package com.eis.trader.service;

import org.springframework.stereotype.Service;

/**
 * Created by kaclarpt on 2019/5/29
 */

public interface OrderService {
    boolean transferOrder(byte[] data);
}
