package com.eis.trader.service.impl;

import com.eis.trader.domain.User;
import com.eis.trader.repository.UserRepository;
import com.eis.trader.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kaclarpt on 2019/6/10
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(String email, String pwd) {
        return userRepository.findByEmail(email);
    }
}
