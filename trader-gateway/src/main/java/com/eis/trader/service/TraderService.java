package com.eis.trader.service;

import com.eis.trader.domain.Trader;

/**
 * Created by kaclarpt on 2019/6/9
 */
public interface TraderService {
    Trader login(String name, String pwd);
}
