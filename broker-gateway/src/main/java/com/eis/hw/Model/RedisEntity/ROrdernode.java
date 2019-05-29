package com.eis.hw.Model.RedisEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ROrdernode implements Serializable{
    private int price;
    private int vol;
    private List<Integer> orders = new ArrayList<>();

    public void addOrderitem(Integer orderitemId){
        orders.add(orderitemId);
    }

    public void setVol(int vol) {
        this.vol = vol;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getVol() {
        return vol;
    }

    public int getPrice() {
        return price;
    }

    public void setOrders(List<Integer> orders) {
        this.orders = orders;
    }

    public List<Integer> getOrders() {
        return orders;
    }
}
