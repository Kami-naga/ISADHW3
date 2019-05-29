package com.eis.hw.Model.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Trade {
    private int tradeId;
    private Integer instrumentId;
    private Double price;
    private Integer qty;
    private Integer initiatorId;
    private String initiatorBuy;
    private Integer completionId;

    @Id
    @Column(name = "trade_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    @Basic
    @Column(name = "instrument_id")
    public Integer getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Integer instrumentId) {
        this.instrumentId = instrumentId;
    }

    @Basic
    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "qty")
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Basic
    @Column(name = "initiator_id")
    public Integer getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(Integer initiatorId) {
        this.initiatorId = initiatorId;
    }

    @Basic
    @Column(name = "initiator_buy")
    public String getInitiatorBuy() {
        return initiatorBuy;
    }

    public void setInitiatorBuy(String initiatorBuy) {
        this.initiatorBuy = initiatorBuy;
    }

    @Basic
    @Column(name = "completion_id")
    public Integer getCompletionId() {
        return completionId;
    }

    public void setCompletionId(Integer completionId) {
        this.completionId = completionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return tradeId == trade.tradeId &&
                Objects.equals(instrumentId, trade.instrumentId) &&
                Objects.equals(price, trade.price) &&
                Objects.equals(qty, trade.qty) &&
                Objects.equals(initiatorId, trade.initiatorId) &&
                Objects.equals(initiatorBuy, trade.initiatorBuy) &&
                Objects.equals(completionId, trade.completionId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tradeId, instrumentId, price, qty, initiatorId, initiatorBuy, completionId);
    }
}
