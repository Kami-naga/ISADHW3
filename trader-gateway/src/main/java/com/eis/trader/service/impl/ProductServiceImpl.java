package com.eis.trader.service.impl;

import com.eis.trader.domain.Instrument;
import com.eis.trader.domain.Product;
import com.eis.trader.repository.InstrumentRepository;
import com.eis.trader.repository.ProductRepository;
import com.eis.trader.service.ProductService;
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

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Override
    public void addInstrument(Instrument instrument) {
        Long productId = instrument.getProduct().getProductId();
//        Product product = productRepository.findById(productId).orElse(null);
//        if (product == null)
//            return;
//        List<Instrument> instrumentList = product.getInstruments();
//        instrumentList.add(instrument);
//        product.setInstruments(instrumentList);
//        productRepository.save(product);
        instrumentRepository.save(instrument);
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = (List<Product>) productRepository.findAll();
        return products;
    }

    @Override
    public Product findProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

}
