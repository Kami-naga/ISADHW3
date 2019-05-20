package com.eis.trader.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by kaclarpt on 2019/5/20
 */
@Data
@Entity
public class Broker implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long brokerId;

    private String name;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "broker")
    private Set<Instrument> instruments;
}
