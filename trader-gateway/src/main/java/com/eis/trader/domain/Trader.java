package com.eis.trader.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kaclarpt on 2019/5/20
 */
@Entity
@Data
public class Trader implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long traderId;

    private String name;

    private String company;

    private Boolean other_see;

}
