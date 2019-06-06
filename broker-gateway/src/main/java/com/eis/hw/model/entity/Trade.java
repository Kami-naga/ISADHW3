package com.eis.hw.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
public class Trade {

    @Id
    @GeneratedValue
    private Long tradeId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="instrumentId")
    private Instrument instrument;

    private Double price;

    private Integer qty;

    @ManyToOne
    @JoinColumn(name="initiatorId")
    private Trader initiator;

    private String initiatorBuy;

    @ManyToOne
    @JoinColumn(name="completionId")
    private Trader completion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return tradeId == trade.tradeId &&
                Objects.equals(this.getInstrument().getInstrumentId(), trade.getInstrument().getInstrumentId()) &&
                Objects.equals(price, trade.price) &&
                Objects.equals(qty, trade.qty) &&
                Objects.equals(this.getInitiator().getTraderId(), trade.getInitiator().getTraderId()) &&
                Objects.equals(initiatorBuy, trade.initiatorBuy) &&
                Objects.equals(this.getCompletion().getTraderId(), trade.getCompletion().getTraderId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeId, this.getInstrument().getInstrumentId(), price, qty, this.getInitiator().getTraderId(), initiatorBuy, this.getCompletion().getTraderId());
    }
}
