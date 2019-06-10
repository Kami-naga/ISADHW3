package com.eis.trader.domain;

/**
 * Created by kaclarpt on 2019/6/9
 */
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Broker {
    @Id
    @GeneratedValue
    private Long brokerId;

    private String name;

    @OneToMany
    private List<Instrument> instruments;

    @OneToMany
    private List<OrderData> orderData;

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
