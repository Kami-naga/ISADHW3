package com.eis.hw.vo;

import com.eis.hw.enums.OrderSide;
import com.eis.hw.enums.OrderType;
import lombok.Data;

/**
 * Created by kaclarpt on 2019/6/10
 */
@Data
public class TraderVO {
    Long id;

    String name;

    String company;

    OrderSide orderSide;

    Boolean otherSee;
}
