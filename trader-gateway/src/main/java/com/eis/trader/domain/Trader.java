package com.eis.trader.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by kaclarpt on 2019/6/8
 */
@Entity
@Data
public class Trader {
    @Id
    @GeneratedValue
    private Long traderId;

    private String name;

    private String company;

    @OneToMany
    private List<OrderData> orderDataList;
}
