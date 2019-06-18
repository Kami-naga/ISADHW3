package com.eis.trader.controller;

import com.eis.trader.domain.Product;
import com.eis.trader.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by kaclarpt on 2019/6/9
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/all")
    @ResponseBody
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}
