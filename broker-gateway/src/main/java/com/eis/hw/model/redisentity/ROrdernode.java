package com.eis.hw.model.redisentity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ROrdernode implements Serializable{
    private int price;
    private int vol;
    private List<Long> orders = new ArrayList<Long>();

    public void addOrderitem(Long orderitemId){
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

    public void setOrders(List<Long> orders) {
        this.orders = orders;
    }

    public List<Long> getOrders() {
        return orders;
    }
}
