package com.eis.trader.domain;

import com.eis.trader.enums.OrderSide;
import com.eis.trader.enums.OrderType;
import lombok.Data;

import javax.persistence.*;


/**
 * Created by kaclarpt on 2019/6/8
 */
@Entity
@Data
public class OrderData {

    @Id
    private String orderId;

    private String bookId;

    private Long brokerId;

    private Long instrumentId;

    private OrderSide orderSide;

    private OrderType orderType;

    private Integer price;

    private Integer qty;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="traderId")
    private Trader trader;
}
