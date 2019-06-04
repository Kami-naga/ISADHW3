package com.eis.hw.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Orderitem {
    private Long orderId;
    private Long brokerId;
    private Long traderId;
    private Integer vol;
    private String nodeId;

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "broker_id")
    public Long getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Long brokerId) {
        this.brokerId = brokerId;
    }

    @Basic
    @Column(name = "trader_id")
    public Long getTraderId() {
        return traderId;
    }

    public void setTraderId(Long traderId) {
        this.traderId = traderId;
    }

    @Basic
    @Column(name = "vol")
    public Integer getVol() {
        return vol;
    }

    public void setVol(Integer vol) {
        this.vol = vol;
    }

    @Basic
    @Column(name = "node_id")
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orderitem orderitem = (Orderitem) o;
        return orderId == orderitem.orderId &&
                Objects.equals(brokerId, orderitem.brokerId) &&
                Objects.equals(traderId, orderitem.traderId) &&
                Objects.equals(vol, orderitem.vol) &&
                Objects.equals(nodeId, orderitem.nodeId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(orderId, brokerId, traderId, vol, nodeId);
    }
}
