package com.eis.trader.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by kaclarpt on 2019/6/10
 */
@Entity
@Data
public class User {
    @Id
    @GeneratedValue
    private Long userId;

    private String password;

    private String email;

    private String name;

    private Integer role;

    private Long id;
}
