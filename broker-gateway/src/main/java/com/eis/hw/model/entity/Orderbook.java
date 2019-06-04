package com.eis.hw.model.entity;

import java.io.Serializable;
import java.util.*;


public class Orderbook implements Serializable {
    private List<Ordernode> sells = new LinkedList<>();
    private List<Ordernode> buys = new LinkedList<>();
    private List<Ordernode> stopSells = new LinkedList<>();
    private List<Ordernode> stopBuys = new LinkedList<>();

    public void buyAdd(Ordernode o){
        buys.add(o);
    }

    public void sellAdd(Ordernode o){
        sells.add(o);
    }

    public void sBuyAdd(Ordernode o){ stopBuys.add(o);}

    public void sSellAdd(Ordernode o){ stopSells.add(o);}

    public List<Ordernode> getSells() {
        return sells;
    }

    public List<Ordernode> getBuys() {
        return buys;
    }

    public List<Ordernode> getStopBuys() {
        return stopBuys;
    }

    public List<Ordernode> getStopSells() {
        return stopSells;
    }

    public void sort(){
        Collections.sort(buys,buyComparator);
        Collections.sort(sells,sellComparator);
        Collections.sort(stopSells,buyComparator);
        Collections.sort(stopBuys,sellComparator);
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
