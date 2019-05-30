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
    private Integer traderId;

    private Integer brokerId;

    private Integer productId;

    private String period;

    private OrderSide orderSide;

    private OrderType orderType;

    private BigDecimal price;

    private Integer quantity;
}
