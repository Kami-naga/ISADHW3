package com.eis.hw.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Trader {

    @Id
    @GeneratedValue
    private Long traderId;

    private String name;

    private String company;

    private Byte otherSee;

    @OneToMany
    private List<Orderitem> orderitems;

    @OneToMany
    private List<Trade> init_trades;

    @OneToMany
    private List<Trade> comple_trades;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trader trader = (Trader) o;
        return traderId == trader.traderId &&
                Objects.equals(name, trader.name) &&
                Objects.equals(company, trader.company) &&
                Objects.equals(otherSee, trader.otherSee);
    }

    @Override
    public int hashCode() {

        return Objects.hash(traderId, name, company, otherSee);
    }
}
