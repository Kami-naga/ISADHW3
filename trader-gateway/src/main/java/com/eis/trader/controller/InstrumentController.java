package com.eis.trader.controller;

import com.eis.trader.domain.Instrument;
import com.eis.trader.domain.Product;
import com.eis.trader.service.InstrumentService;
import com.eis.trader.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by kaclarpt on 2019/6/9
 */
@RestController
@RequestMapping("/api/instrument")
public class InstrumentController {

    @Autowired
    private InstrumentService instrumentService;

    @PostMapping("/byProduct")
    @ResponseBody
    public List<Instrument> getInstrumentByProduct(Long productId) {
        return instrumentService.findInstrumentsByProductId(productId);
    }

    @PostMapping("/byProductAndBroker")
    @ResponseBody
    public List<Instrument> getInstrumentByProductAndBroker(Long productId, Long brokerId) {
        return instrumentService.findInstrumentsByProductIdAndBrokerId(productId, brokerId);
    }
}
