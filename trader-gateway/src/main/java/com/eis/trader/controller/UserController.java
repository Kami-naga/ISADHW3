package com.eis.trader.controller;

import com.eis.trader.domain.Trader;
import com.eis.trader.domain.User;
import com.eis.trader.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by kaclarpt on 2019/6/10
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public User checkUser(String email, String password) {
        return userService.login(email, password);
    }
}
