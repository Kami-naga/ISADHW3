package com.eis.trader.enums;

import lombok.Getter;

/**
 * Created by kaclarpt on 2019/5/20
 */
@Getter
public enum OrderSide {
    BUY(0,"买"), SELL(1,"卖");
    private Integer code;
    private String status;

    OrderSide(Integer code, String status) {
        this.code = code;
        this.status = status;
    }
}
