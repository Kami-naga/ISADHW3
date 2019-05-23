package com.eis.hw.Model.RedisEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ROrderbook implements Serializable{
    private List<String> buys = new ArrayList<>();
    private List<String> sells = new ArrayList<>();

    public List<String> getBuys() {
        return buys;
    }

    public List<String> getSells() {
        return sells;
    }

    public void setBuys(List<String> buys) {
        this.buys = buys;
    }

    public void setSells(List<String> sells) {
        this.sells = sells;
    }

}
