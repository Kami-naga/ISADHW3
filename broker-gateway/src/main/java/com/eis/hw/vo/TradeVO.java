package com.eis.hw.vo;

import com.eis.hw.model.entity.Trader;
import lombok.Data;

/**
 * Created by kaclarpt on 2019/6/10
 */
@Data
public class TradeVO {
    Long tradeId;

    String broker;

    String product;

    Double price;

    Integer qty;

    String periodT;

    TraderVO initiator;

    TraderVO completion;
}
