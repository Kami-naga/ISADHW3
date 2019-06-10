package com.eis.trader.service;

import com.eis.trader.domain.Instrument;
import com.eis.trader.domain.Product;

import java.util.List;

/**
 * Created by kaclarpt on 2019/6/9
 */
public interface ProductService {
    void addInstrument(Instrument instrument);

    List<Product> getAllProducts();

    Product findProductById(Long productId);
}
