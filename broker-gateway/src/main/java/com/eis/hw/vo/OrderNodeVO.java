package com.eis.hw.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by kaclarpt on 2019/6/6
 */
@Data
public class OrderNodeVO implements Serializable {

    private static final Long SerialVersionUID = 1L;

    int price;

    int volumn;
}
