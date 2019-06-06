package com.eis.hw.service.Impl;

import com.eis.hw.model.entity.Orderbook;
import com.eis.hw.model.entity.Orderitem;
import com.eis.hw.model.entity.Ordernode;
import com.eis.hw.model.redisentity.ROrderbook;
import com.eis.hw.model.redisentity.ROrdernode;
import com.eis.hw.service.OrderbookService;
import com.eis.hw.service.OrdernodeService;
import com.eis.hw.service.ROrdernodeService;
import com.eis.hw.util.ProtostuffUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderbookServiceImpl implements OrderbookService {
    private final ROrdernodeService rOrdernodeService;

    private final OrdernodeService ordernodeService;

    private final RabbitTemplate rabbitTemplate;

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
        showOrdernodes(orderbook.getBuys(), "buys");
        showOrdernodes(orderbook.getSells(), "sells");
        showOrdernodes(orderbook.getStopBuys(), "stopBuys");
        showOrdernodes(orderbook.getStopSells(), "stopSells");
    }

    public void showOrdernodes(List<Ordernode> ordernodes, String info) {
        System.out.println(info+":");
        for(int i=0;i<ordernodes.size();i++){
            Ordernode ordernode = ordernodes.get(i);
            System.out.println("------------------------------------------------------");
            System.out.println(String.valueOf("node "+ordernode.getPrice())+" "+String.valueOf(ordernode.getVol()));
            List<Orderitem> orderitems = ordernode.getOrderitemList();
            for(int j=0;j<orderitems.size();j++){
                Orderitem orderitem = orderitems.get(j);
                System.out.println("trader:"+String.valueOf(orderitem.getTrader().getTraderId())+" "+String.valueOf(orderitem.getVol()));
            }
            System.out.println("------------------------------------------------------");
        }
    }

    @Override
    public boolean transferOrder(byte[] data) {
        String topicExchangeName = "exchange";
        String queueName = "orderBook";
        rabbitTemplate.convertAndSend(topicExchangeName, queueName, data);
        return true;
    }
}
