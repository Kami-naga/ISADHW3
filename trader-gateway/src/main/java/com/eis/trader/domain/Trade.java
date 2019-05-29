//package com.eis.trader.domain;
//
//import lombok.Data;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
///**
// * Created by kaclarpt on 2019/5/20
// */
//@Data
//@Entity
//public class Trade implements Serializable {
//
//    private static final Long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue
//    private Long tradeId;
//
//    @JoinColumn(name = "brokerId")
//    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    private Broker broker;
//
//    @JoinColumn(name="productId")
//    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    private Product product;
//
//    private String period;
//}