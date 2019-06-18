package com.eis.trader.controller;

import com.eis.trader.domain.Trader;
import com.eis.trader.repository.TraderRepository;
import com.eis.trader.service.TraderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private TraderRepository traderRepository;

    @GetMapping("/changeTrader")
    public void test() {
        Trader trader = traderRepository.findById(1L).orElse(null);
        trader.setOtherSee(!trader.getOtherSee());
        traderRepository.save(trader);
    }


}
