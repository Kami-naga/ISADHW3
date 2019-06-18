package com.eis.hw.controller;

import com.eis.hw.dao.BrokerRepository;
import com.eis.hw.dao.InstrumentRepository;
import com.eis.hw.dao.TradeRepository;
import com.eis.hw.dao.TraderRepository;
import com.eis.hw.model.entity.Trader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MysqlController {

    @Autowired
    public BrokerRepository brokerRepository;

    @Autowired
    public InstrumentRepository instrumentRepository;

    @Autowired
    public TraderRepository traderRepository;

    @GetMapping(value = "mysq")
    @ResponseBody
    public void a(){
        System.out.println(instrumentRepository.findById(1L).get().getPeriodT());

    }

    @GetMapping("/changeTrader")
    public void test() {
        Trader trader = traderRepository.findById(1L).orElse(null);
        trader.setOtherSee(!trader.getOtherSee());
        traderRepository.save(trader);
    }
}
