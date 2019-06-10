package com.eis.hw.service.Impl;

import com.eis.hw.dao.ProductRepository;
import com.eis.hw.model.entity.Instrument;
import com.eis.hw.model.entity.Product;
import com.eis.hw.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kaclarpt on 2019/6/9
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void addInstrument(Instrument instrument) {
        Long productId = instrument.getProduct().getProductId();
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null)
            return;
        List<Instrument> instrumentList = product.getInstruments();
        instrumentList.add(instrument);
        product.setInstruments(instrumentList);
        productRepository.save(product);
    }
}
