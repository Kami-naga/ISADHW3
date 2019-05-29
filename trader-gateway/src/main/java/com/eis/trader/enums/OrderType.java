package com.eis.trader.enums;

import lombok.Getter;

/**
 * Created by kaclarpt on 2019/5/20
 */
@Getter
public enum OrderType {
    MARKETORDER(0,"市价单"), LIMITORDER(1,"限价单"), STOPMARKETORDER(2,"止损市价单"), STOPLIMITORDER(3,"止损限价单");
    private Integer code;
    private String name;

    OrderType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
