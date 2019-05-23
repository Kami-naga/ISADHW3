package com.eis.hw.Model.Entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Instrument {
    private int instrumentId;
    private Integer productId;
    private Integer brokerId;
    private String period;

    @Id
    @Column(name = "instrument_id")
    public int getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(int instrumentId) {
        this.instrumentId = instrumentId;
    }

    @Basic
    @Column(name = "product_id")
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "broker_id")
    public Integer getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Integer brokerId) {
        this.brokerId = brokerId;
    }

    @Basic
    @Column(name = "period")
    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instrument that = (Instrument) o;
        return instrumentId == that.instrumentId &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(brokerId, that.brokerId) &&
                Objects.equals(period, that.period);
    }

    @Override
    public int hashCode() {

        return Objects.hash(instrumentId, productId, brokerId, period);
    }
}
