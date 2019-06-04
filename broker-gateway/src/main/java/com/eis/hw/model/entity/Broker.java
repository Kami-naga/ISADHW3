package com.eis.hw.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Broker {
    private Long brokerId;
    private String name;

    @Id
    @Column(name = "broker_id")
    public Long getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Long brokerId) {
        this.brokerId = brokerId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Broker broker = (Broker) o;
        return brokerId == broker.brokerId &&
                Objects.equals(name, broker.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(brokerId, name);
    }
}
