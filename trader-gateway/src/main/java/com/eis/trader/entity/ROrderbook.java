package com.eis.trader.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kaclarpt on 2019/6/5
 */

public class ROrderbook implements Serializable {

    private String orderBookId;
    private List<String> buys = new LinkedList<>();

    private List<String> sells = new LinkedList<>();

    private List<String> stopBuys = new LinkedList<>();

    private List<String> stopSells = new LinkedList<>();

    public List<String> getStopBuys() {
        return stopBuys;
    }

    public List<String> getStopSells() {
        return stopSells;
    }

    public List<String> getBuys() {
        return buys;
    }

    public List<String> getSells() {
        return sells;
    }

    public void setStopBuys(List<String> stopBuys) {
        this.stopBuys = stopBuys;
    }

    public void setStopSells(List<String> stopSells) {
        this.stopSells = stopSells;
    }

    public void setBuys(List<String> buys) {
        this.buys = buys;
    }

    public void setSells(List<String> sells) {
        this.sells = sells;
    }

    public String getOrderBookId() {
        return orderBookId;
    }

    public void setOrderBookId(String orderBookId) {
        this.orderBookId = orderBookId;
    }
}
