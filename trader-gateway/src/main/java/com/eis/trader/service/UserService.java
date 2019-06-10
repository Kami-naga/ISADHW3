package com.eis.trader.service;

import com.eis.trader.domain.User;

/**
 * Created by kaclarpt on 2019/6/10
 */
public interface UserService {
    User login(String name, String pwd);
}
