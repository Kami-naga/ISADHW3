package com.eis.hw.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Trader {
    private Long traderId;
    private String name;
    private String company;
    private Byte otherSee;

    @Id
    @Column(name = "trader_id")
    public Long getTraderId() {
        return traderId;
    }

    public void setTraderId(Long traderId) {
        this.traderId = traderId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "company")
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Basic
    @Column(name = "other_see")
    public Byte getOtherSee() {
        return otherSee;
    }

    public void setOtherSee(Byte otherSee) {
        this.otherSee = otherSee;
    }

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
