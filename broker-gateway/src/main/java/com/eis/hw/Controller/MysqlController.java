package com.eis.hw.Controller;

import com.eis.hw.DAO.BrokerRepository;
import com.eis.hw.DAO.InstrumentRepository;
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
        System.out.println(instrumentRepository.findById(1).get().getPeriod());

    }
}
