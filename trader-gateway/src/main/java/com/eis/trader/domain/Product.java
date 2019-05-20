package com.eis.trader.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by kaclarpt on 2019/5/20
 */
@Entity
@Data
public class Product implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long productId;

    private String name;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "product")
    private Set<Instrument> instruments;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "product")
    private Set<OrderMain> orders;
}
