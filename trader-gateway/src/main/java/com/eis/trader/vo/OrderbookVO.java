package com.eis.trader.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kaclarpt on 2019/6/6
 */
@Data
public class OrderbookVO implements Serializable {

    private static final Long SerialVersionUID = 1L;

    String OrderbookId;

    List<OrderNodeVO> buysFive;

    List<OrderNodeVO> sellsFive;
}
