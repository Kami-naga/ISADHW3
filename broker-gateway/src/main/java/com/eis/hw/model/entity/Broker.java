package com.eis.hw.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Broker implements Serializable {
    @Id
    @GeneratedValue
    private Long brokerId;

    private String name;

    @OneToMany
    private List<Instrument> instruments;

    @OneToMany
    private List<Orderitem> orderitems;

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
