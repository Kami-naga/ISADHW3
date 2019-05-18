package com.eis.hw.Model.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Trade {
    private int tradeId;
    private String period;
    private Integer price;
    private Integer qty;
    private Byte initiatorSide;
    private Byte completionSide;
    private Broker brokerByBrokerId;
    private Product productByProductId;

    @Id
    @Column(name = "trade_id")
    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    @Basic
    @Column(name = "period")
    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Basic
    @Column(name = "price")
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
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
    @Column(name = "initiator_side")
    public Byte getInitiatorSide() {
        return initiatorSide;
    }

    public void setInitiatorSide(Byte initiatorSide) {
        this.initiatorSide = initiatorSide;
    }

    @Basic
    @Column(name = "completion_side")
    public Byte getCompletionSide() {
        return completionSide;
    }

    public void setCompletionSide(Byte completionSide) {
        this.completionSide = completionSide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return tradeId == trade.tradeId &&
                Objects.equals(period, trade.period) &&
                Objects.equals(price, trade.price) &&
                Objects.equals(qty, trade.qty) &&
                Objects.equals(initiatorSide, trade.initiatorSide) &&
                Objects.equals(completionSide, trade.completionSide);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tradeId, period, price, qty, initiatorSide, completionSide);
    }

    @ManyToOne
    @JoinColumn(name = "broker_id", referencedColumnName = "broker_id")
    public Broker getBrokerByBrokerId() {
        return brokerByBrokerId;
    }

    public void setBrokerByBrokerId(Broker brokerByBrokerId) {
        this.brokerByBrokerId = brokerByBrokerId;
    }

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    public Product getProductByProductId() {
        return productByProductId;
    }

    public void setProductByProductId(Product productByProductId) {
        this.productByProductId = productByProductId;
    }
}
