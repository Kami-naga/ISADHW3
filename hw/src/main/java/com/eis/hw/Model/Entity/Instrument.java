package com.eis.hw.Model.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Instrument {
    private int instrumentId;
    private String period;
    private Broker brokerByBrokerId;

    @Id
    @Column(name = "instrument_id")
    public int getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(int instrumentId) {
        this.instrumentId = instrumentId;
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
                Objects.equals(period, that.period);
    }

    @Override
    public int hashCode() {

        return Objects.hash(instrumentId, period);
    }

    @ManyToOne
    @JoinColumn(name = "broker_id", referencedColumnName = "broker_id")
    public Broker getBrokerByBrokerId() {
        return brokerByBrokerId;
    }

    public void setBrokerByBrokerId(Broker brokerByBrokerId) {
        this.brokerByBrokerId = brokerByBrokerId;
    }
}
