package com.eis.hw.Controller;

import com.eis.hw.DAO.BrokerRepository;
import com.eis.hw.DAO.InstrumentRepository;
import com.eis.hw.DAO.ProductRepository;
import com.eis.hw.Model.Entity.Broker;
import com.eis.hw.Model.Entity.Instrument;
import com.eis.hw.Model.Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MysqlController {
    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private BrokerRepository brokerRepository;

    @Autowired ProductRepository productRepository;

    @GetMapping(value="/mysql")
    @ResponseBody
    public void a(){
        System.out.println(instrumentRepository.findAll().size());
    }

    @GetMapping(value = "mysq")
    @ResponseBody
    public void b(){
        System.out.println(brokerRepository.findAll().size());
        Product product = new Product();
        product.setName("sad");
        productRepository.save(product);
        System.out.println(productRepository.findAll().size());
    }


}
