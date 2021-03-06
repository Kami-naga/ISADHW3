package com.eis.trader.form;

import com.eis.trader.enums.OrderSide;
import com.eis.trader.enums.OrderType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by kaclarpt on 2019/5/20
 */

@Data
public class InstrumentForm {
    private Long bookId;

    private Long traderId;

    private Long brokerId;

    private Long productId;

    private String periodT;

    private OrderSide orderSide;

    private OrderType orderType;

    private Integer price;

    private Integer qty;
}
