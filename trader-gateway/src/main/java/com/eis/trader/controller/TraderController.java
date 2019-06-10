package com.eis.trader.controller;

import com.eis.trader.domain.Trader;
import com.eis.trader.service.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by kaclarpt on 2019/6/9
 */


@RestController
public class TraderController {

    @Autowired
    private TraderService traderService;

    @RequestMapping("/login")
    public boolean checkUser(@RequestBody Map map, HttpServletRequest request) {
        Trader u = traderService.login((String)map.get("name"), (String)map.get("pwd"));
        return true;
    }
}
