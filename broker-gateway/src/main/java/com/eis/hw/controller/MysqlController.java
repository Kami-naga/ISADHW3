package com.eis.hw.controller;

import com.eis.hw.dao.BrokerRepository;
import com.eis.hw.dao.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MysqlController {

    @Autowired
    public BrokerRepository brokerRepository;

    @Autowired
    public InstrumentRepository instrumentRepository;

    @GetMapping(value = "mysq")
    @ResponseBody
    public void a(){
        System.out.println(instrumentRepository.findById(1L).get().getPeriod());

    }
}
