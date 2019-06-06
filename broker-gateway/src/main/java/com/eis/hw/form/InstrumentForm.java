package com.eis.hw.form;

import com.eis.hw.enums.OrderSide;
import com.eis.hw.enums.OrderType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by kaclarpt on 2019/5/20
 */

@Data
public class InstrumentForm {
    private String bookId;

    private Long traderId;

    private Long brokerId;

    private Long productId;

    private String period;

    private OrderSide orderSide;

    private OrderType orderType;

    private Integer price;

    private Integer qty;
}
