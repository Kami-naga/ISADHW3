package com.eis.hw.Component;

import com.eis.hw.Enums.OrderSide;
import com.eis.hw.Enums.OrderType;
import com.eis.hw.Model.Entity.Product;
import com.eis.hw.Model.Entity.Trader;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kaclarpt on 2019/5/29
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
