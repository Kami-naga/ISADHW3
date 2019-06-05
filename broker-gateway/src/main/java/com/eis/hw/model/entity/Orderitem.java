package com.eis.hw.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
public class Orderitem {

    @Id
    @GeneratedValue
    private Long orderId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="brokerId")
    private Broker broker;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="traderId")
    private Trader trader;

    private Integer vol;

    private String nodeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orderitem orderitem = (Orderitem) o;
        return orderId == orderitem.orderId &&
                Objects.equals(this.getBroker().getBrokerId(), orderitem.getBroker().getBrokerId()) &&
                Objects.equals(this.getTrader().getTraderId(), orderitem.getTrader().getTraderId()) &&
                Objects.equals(vol, orderitem.vol) &&
                Objects.equals(nodeId, orderitem.nodeId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(orderId, this.getBroker().getBrokerId(), this.getTrader().getTraderId(), vol, nodeId);
    }
}
