package com.eis.hw.controller;

/**
 * Created by kaclarpt on 2019/6/9
 */

import com.eis.hw.model.entity.Instrument;
import com.eis.hw.service.InstrumentService;
import com.eis.hw.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/instrument")
public class InstrumentController {
    @Autowired
    InstrumentService instrumentService;

    @Autowired
    ProductService productService;

    @PostMapping("/publish")
    public void publishInstrument(@RequestBody Instrument instrument) {
        Long uuid = UUID.randomUUID().node();
        instrument.setInstrumentId(uuid);
        productService.addInstrument(instrument);
        instrumentService.publishInstrument(instrument);
    }

    @PostMapping("/addInstrument")
    public void addInstrument(Long productId, Long brokerId, String periodT) {
        instrumentService.addInstrument(productId, brokerId, periodT);
    }
}
