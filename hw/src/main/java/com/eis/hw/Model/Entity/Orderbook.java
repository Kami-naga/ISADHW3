package com.eis.hw.Model.Entity;

import com.eis.hw.Model.RedisEntity.ROrderbook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Orderbook implements Serializable {
    private List<Ordernode> sells = new ArrayList<>();
    private List<Ordernode> buys = new ArrayList<>();

    public void buyAdd(Ordernode o){
        buys.add(o);
    }

    public void sellAdd(Ordernode o){
        sells.add(o);
    }

    public List<Ordernode> getSells() {
        return sells;
    }

    public List<Ordernode> getBuys() {
        return buys;
    }

    public void sort(){
        Collections.sort(buys,buyComparator);
        Collections.sort(sells,sellComparator);
    }

    public static Comparator<Ordernode> sellComparator = new Comparator<Ordernode>() {
        @Override
        public int compare(Ordernode o1, Ordernode o2) {
            return o1.getPrice()-o2.getPrice();
        }
    };

    public static Comparator<Ordernode> buyComparator = new Comparator<Ordernode>() {
        @Override
        public int compare(Ordernode o1, Ordernode o2) {
            return o2.getPrice()-o1.getPrice();
        }
    };

    public void test(){
    }
}
