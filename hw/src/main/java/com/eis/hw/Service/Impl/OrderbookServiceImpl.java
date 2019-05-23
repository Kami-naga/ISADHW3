package com.eis.hw.Service.Impl;

import com.eis.hw.Model.Entity.Orderbook;
import com.eis.hw.Model.Entity.Orderitem;
import com.eis.hw.Model.Entity.Ordernode;
import com.eis.hw.Model.RedisEntity.ROrderbook;
import com.eis.hw.Model.RedisEntity.ROrdernode;
import com.eis.hw.Service.OrderbookService;
import com.eis.hw.Service.OrdernodeService;
import com.eis.hw.Service.ROrderbookService;
import com.eis.hw.Service.ROrdernodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderbookServiceImpl implements OrderbookService {
    @Autowired
    private ROrdernodeService rOrdernodeService;

    @Autowired
    private OrdernodeService ordernodeService;

    @Override
    public Orderbook construct(ROrderbook rOrderbook) {
        List<String> buys = rOrderbook.getBuys();
        List<String> sells = rOrderbook.getSells();
        Orderbook orderbook = new Orderbook();
        for(int i=0;i<buys.size();i++){
            String bnodeId = buys.get(i);
            ROrdernode bnode = rOrdernodeService.get(bnodeId);
            orderbook.buyAdd(ordernodeService.construct(bnode));
        }
        for(int j=0;j<sells.size();j++){
            String snodeId = sells.get(j);
            ROrdernode snode = rOrdernodeService.get(snodeId);
            orderbook.sellAdd(ordernodeService.construct(snode));
        }
        orderbook.sort();
        return orderbook;
    }

    @Override
    public void showOrderbook(Orderbook orderbook) {
        System.out.println("buys:");
        List<Ordernode> buys = orderbook.getBuys();
        for(int i=0;i<buys.size();i++){
            Ordernode ordernode = buys.get(i);
            System.out.println("------------------------------------------------------");
            System.out.println(String.valueOf("node "+ordernode.getPrice())+" "+String.valueOf(ordernode.getVol()));
            List<Orderitem> orderitems = ordernode.getOrderitemList();
            for(int j=0;j<orderitems.size();j++){
                Orderitem orderitem = orderitems.get(j);
                System.out.println("trader:"+String.valueOf(orderitem.getTraderId())+" "+String.valueOf(orderitem.getVol()));
            }
            System.out.println("------------------------------------------------------");
        }
        System.out.println("sells:");
        List<Ordernode> sells = orderbook.getSells();
        for(int i=0;i<sells.size();i++){
            Ordernode ordernode = sells.get(i);
            System.out.println("------------------------------------------------------");
            System.out.println(String.valueOf("node "+ordernode.getPrice())+" "+String.valueOf(ordernode.getVol()));
            List<Orderitem> orderitems = ordernode.getOrderitemList();
            for(int j=0;j<orderitems.size();j++){
                Orderitem orderitem = orderitems.get(j);
                System.out.println("trader:"+String.valueOf(orderitem.getTraderId())+" "+String.valueOf(orderitem.getVol()));
            }
            System.out.println("------------------------------------------------------");
        }
    }


}
