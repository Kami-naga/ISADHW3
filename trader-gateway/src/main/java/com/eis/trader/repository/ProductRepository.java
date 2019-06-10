package com.eis.trader.repository;

import com.eis.trader.domain.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by kaclarpt on 2019/6/9
 */
public interface ProductRepository extends CrudRepository <Product, Long> {
}
