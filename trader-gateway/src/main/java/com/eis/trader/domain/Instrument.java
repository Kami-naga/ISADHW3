package com.eis.trader.domain;

/**
 * Created by kaclarpt on 2019/6/9
 */
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Instrument implements Serializable {

    private static final Long SerialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long instrumentId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="productId")
    private Product product;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="brokerId")
    private Broker broker;

    private String periodT;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instrument that = (Instrument) o;
        return instrumentId == that.instrumentId &&
                Objects.equals(this.getProduct().getProductId(), that.getProduct().getProductId()) &&
                Objects.equals(this.getBroker().getBrokerId(), that.getBroker().getBrokerId()) &&
                Objects.equals(periodT, that.periodT);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrumentId, this.getProduct().getProductId(), this.getBroker().getBrokerId(), periodT);
    }
}
