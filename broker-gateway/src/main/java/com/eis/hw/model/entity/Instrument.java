package com.eis.hw.model.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Instrument {
    @Id
    @GeneratedValue
    private Long instrumentId;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name="productId")
    private Product product;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name="brokerId")
    private Broker broker;

    @OneToMany
    private List<Trade> trades;

    private String period;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instrument that = (Instrument) o;
        return instrumentId == that.instrumentId &&
                Objects.equals(this.getProduct().getProductId(), that.getProduct().getProductId()) &&
                Objects.equals(this.getBroker().getBrokerId(), that.getBroker().getBrokerId()) &&
                Objects.equals(period, that.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrumentId, this.getProduct().getProductId(), this.getBroker().getBrokerId(), period);
    }
}
