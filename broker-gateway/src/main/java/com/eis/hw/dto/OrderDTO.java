package com.eis.hw.dto;

import com.eis.hw.enums.OrderSide;
import com.eis.hw.enums.OrderType;
import lombok.Data;

/**
 * Created by kaclarpt on 2019/6/5
 */
@Data
public class OrderDTO {

    private String bookId;

    private Long traderId;

    private Long brokerId;

    private Long instrumentId;

    private OrderSide orderSide;

    private OrderType orderType;

    private Integer price;

    private Integer qty;
}

