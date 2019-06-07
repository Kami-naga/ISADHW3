package com.eis.hw.Model.RedisEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ROrderbook implements Serializable{
    private List<String> buys = new ArrayList<>();
    private List<String> sells = new ArrayList<>();

    private List<String> stopBuys = new ArrayList<>();
    private List<String> stopSells = new ArrayList<>();

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

}