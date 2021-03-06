package com.eis.hw.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Ordernode {
    private int vol;
    private int price;
    private List<Orderitem> orderitemList = new ArrayList<>();

    public List<Orderitem> getOrderitemList() {
        return orderitemList;
    }

    public int getPrice() {
        return price;
    }

    public int getVol() {
        return vol;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setVol(int vol) {
        this.vol = vol;
    }

    public void addOrder(Orderitem orderitem){
        orderitemList.add(orderitem);
    }
}
