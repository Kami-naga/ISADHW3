package com.eis.trader.dto;

import com.eis.trader.enums.OrderSide;
import com.eis.trader.enums.OrderType;
import lombok.Data;
import lombok.ToString;

/**
 * Created by kaclarpt on 2019/6/5
 */
@Data
@ToString
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
