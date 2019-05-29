package com.eis.trader.domain;

import com.eis.trader.enums.OrderSide;
import com.eis.trader.enums.OrderType;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kaclarpt on 2019/5/20
 */
@Entity
@Data
public class OrderMain implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long orderId;

    @JoinColumn(name="traderId")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Trader trader;

    @JoinColumn(name="productId")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Product product;

    private String period;

    private OrderSide orderSide;

    private OrderType orderType;

    public Long getOrderId() {
        return orderId;
    }
}
