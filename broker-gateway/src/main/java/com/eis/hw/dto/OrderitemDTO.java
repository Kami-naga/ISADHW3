package com.eis.hw.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by kaclarpt on 2019/6/11
 */
@Data
@ToString
public class OrderitemDTO {
    Long orderId;

    String broker;

    String product;

    int vol;

    String periodT;

    String node_id;

    String timeSign;

}
