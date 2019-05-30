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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderbookServiceImpl implements OrderbookService {
    private final ROrdernodeService rOrdernodeService;

    private final OrdernodeService ordernodeService;

    private final RabbitTemplate rabbitTemplate;

    private final String topicExchangeName="exchange";

    @Autowired
    public OrderbookServiceImpl(ROrdernodeService rOrdernodeService, OrdernodeService ordernodeService, RabbitTemplate rabbitTemplate) {
        this.rOrdernodeService = rOrdernodeService;
        this.ordernodeService = ordernodeService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Orderbook construct(ROrderbook rOrderbook) {
        List<String> buys = rOrderbook.getBuys();
        List<String> sells = rOrderbook.getSells();
        List<String> stopBuys = rOrderbook.getStopBuys();
        List<String> stopSells = rOrderbook.getStopSells();
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
        for(int m=0;m<stopBuys.size();m++){
            String bnodeId = stopBuys.get(m);
            ROrdernode bnode = rOrdernodeService.get(bnodeId);
            orderbook.sBuyAdd(ordernodeService.construct(bnode));
        }
        for(int n=0;n<stopSells.size();n++){
            String snodeId = stopSells.get(n);
            ROrdernode snode = rOrdernodeService.get(snodeId);
            orderbook.sSellAdd(ordernodeService.construct(snode));
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
        System.out.println("------------------------------------------------------");
        System.out.println("stopBuys:");
        List<Ordernode> stopBuys = orderbook.getStopBuys();
        for(int i=0;i<stopBuys.size();i++){
            Ordernode ordernode = stopBuys.get(i);
            System.out.println("------------------------------------------------------");
            System.out.println(String.valueOf("node "+ordernode.getPrice()));
            List<Orderitem> orderitems = ordernode.getOrderitemList();
            for(int j=0;j<orderitems.size();j++){
                Orderitem orderitem = orderitems.get(j);
                System.out.println("trader:"+String.valueOf(orderitem.getTraderId())+" "+String.valueOf(orderitem.getVol()));
            }
            System.out.println("------------------------------------------------------");
        }
        System.out.println("stopSells:");
        List<Ordernode> stopSells = orderbook.getStopSells();
        for(int i=0;i<stopSells.size();i++){
            Ordernode ordernode = stopSells.get(i);
            System.out.println("------------------------------------------------------");
            System.out.println(String.valueOf("node "+ordernode.getPrice()));
            List<Orderitem> orderitems = ordernode.getOrderitemList();
            for(int j=0;j<orderitems.size();j++){
                Orderitem orderitem = orderitems.get(j);
                System.out.println("trader:"+String.valueOf(orderitem.getTraderId())+" "+String.valueOf(orderitem.getVol()));
            }
            System.out.println("------------------------------------------------------");
        }
    }

    @Override
    public boolean transferOrder(byte[] data) {
        rabbitTemplate.convertAndSend(topicExchangeName, "broker", data);
        return true;
    }


}
