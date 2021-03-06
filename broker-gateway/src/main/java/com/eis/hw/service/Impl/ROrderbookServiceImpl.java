package com.eis.hw.service.Impl;

import com.eis.hw.dao.BrokerRepository;
import com.eis.hw.dao.InstrumentRepository;
import com.eis.hw.dao.OrderitemRepository;
import com.eis.hw.enums.OrderSide;
import com.eis.hw.model.entity.*;
import com.eis.hw.model.redisentity.ROrderbook;
import com.eis.hw.model.redisentity.ROrdernode;
import com.eis.hw.service.OrderbookService;
import com.eis.hw.service.ROrderbookService;
import com.eis.hw.service.ROrdernodeService;
import com.eis.hw.util.ProtostuffUtils;
import com.eis.hw.util.RedisUtils;
import com.eis.hw.util.SerializeUtil;
import com.eis.hw.vo.OrderNodeVO;
import com.eis.hw.vo.OrderbookVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class ROrderbookServiceImpl implements ROrderbookService {

    private final ROrdernodeService rOrdernodeService;


    private final OrderitemRepository orderitemRepository;

    private final InstrumentRepository instrumentRepository;

    private final BrokerRepository brokerRepository;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderbookService orderbookService;

    @Autowired
    @Qualifier("conn")
    RedisConnection redisConnection;

    @Autowired
    public ROrderbookServiceImpl(ROrdernodeService rOrdernodeService, OrderitemRepository orderitemRepository, InstrumentRepository instrumentRepository, BrokerRepository brokerRepository, RabbitTemplate rabbitTemplate) {
        this.rOrdernodeService = rOrdernodeService;
        this.orderitemRepository = orderitemRepository;
        this.instrumentRepository = instrumentRepository;
        this.brokerRepository = brokerRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void init() {
        List<Instrument> instruments = instrumentRepository.findAll();
        for (Instrument instrument: instruments) {
            Broker broker = instrument.getBroker();
            String bookId = "B"+String.valueOf(broker.getBrokerId())+"I"+String.valueOf(instrument.getInstrumentId());
            ROrderbook rOrderbook = new ROrderbook();
            rOrderbook.setOrderBookId(bookId);
            save(bookId, rOrderbook);
            publishOrderBook(bookId);
        }
    }

    @Override
    public void save(String s, ROrderbook rOrderbook) {
//        transferOrder(ProtostuffUtils.serialize(rOrderbook));
//        JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();
        byte[] rOrderbook_byte = SerializeUtil.serialize(rOrderbook);
        redisConnection.set(s.getBytes(), rOrderbook_byte);
//        redisTemplate.opsForValue().set(s.getBytes(), SerializeUtil.serialize(rOrderbook));
    }

    @Override
    public ROrderbook get(String s) {
        JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();
        byte[] rOrderbook_byte = redisConnection.get(s.getBytes());
        ROrderbook rOrderbook = (ROrderbook) serializer.deserialize(rOrderbook_byte);
        return rOrderbook;
    }

    @Override
    public void insertOrderitem(String bookId, OrderSide side, int price, int vol, Orderitem orderitem) {
        Long orderitemid = orderitem.getOrderId();
        int i = 0;
        if (side==OrderSide.BUY) {
            ROrderbook rOrderbook = get(bookId);
            List<String> buys = rOrderbook.getBuys();
            int sz = buys.size();
            if (sz == 0) {
                String nodeId = rOrdernodeService.produce(bookId, price, vol, orderitemid);
                orderitem.setNodeId(nodeId);
                buys.add(nodeId);
                rOrderbook.setBuys(buys);
                save(bookId, rOrderbook);
                return;
            }
            for (; i < sz; i++) {
                String bnodeId = buys.get(i);
                ROrdernode bnode = rOrdernodeService.get(bnodeId);
                int bprice = bnode.getPrice();
                if (price > bprice) {
                    String nodeId = rOrdernodeService.produce(bookId, price, vol, orderitemid);
                    orderitem.setNodeId(nodeId);
                    buys.add(i, nodeId);
                    rOrderbook.setBuys(buys);
                    save(bookId, rOrderbook);
                    return;
                } else if (price < bprice) {
                    continue;
                } else if (price == bprice) {
                    rOrdernodeService.addVol(bnodeId, vol, orderitemid);
                    orderitem.setNodeId(bnodeId);
                    return;
                }
            }
            String nodeId = rOrdernodeService.produce(bookId, price, vol, orderitemid);
            orderitem.setNodeId(nodeId);
            buys.add(nodeId);
            rOrderbook.setBuys(buys);
            save(bookId, rOrderbook);
            return;
        } else if (side==OrderSide.SELL) {
            ROrderbook rOrderbook = get(bookId);
            List<String> sells = rOrderbook.getSells();
            int sz = sells.size();
            if (sz == 0) {
                String nodeId = rOrdernodeService.produce(bookId, price, vol, orderitemid);
                orderitem.setNodeId(nodeId);
                sells.add(nodeId);
                rOrderbook.setSells(sells);
                save(bookId, rOrderbook);
                return;
            }
            for (; i < sz; i++) {
                String snodeId = sells.get(i);
                ROrdernode snode = rOrdernodeService.get(snodeId);
                int sprice = snode.getPrice();
                if (price < sprice) {
                    String nodeId = rOrdernodeService.produce(bookId, price, vol, orderitemid);
                    orderitem.setNodeId(nodeId);
                    sells.add(i, nodeId);
                    rOrderbook.setSells(sells);
                    save(bookId, rOrderbook);
                    return;
                } else if (price > sprice) {
                    continue;
                } else if (price == sprice) {
                    rOrdernodeService.addVol(snodeId, vol, orderitemid);
                    orderitem.setNodeId(snodeId);
                    return;
                }
            }
            String nodeId = rOrdernodeService.produce(bookId, price, vol, orderitemid);
            orderitem.setNodeId(nodeId);
            sells.add(nodeId);
            rOrderbook.setSells(sells);
            save(bookId, rOrderbook);
            return;
        }
    }

    @Override
    public void insertStopOrderitem(String bookId, OrderSide side, int stopPrice, int vol, Orderitem orderitem) {
        Long orderitemid = orderitem.getOrderId();
        int i=0;
        if(side==OrderSide.BUY){
            ROrderbook rOrderbook = get(bookId);
            List<String> stopBuys = rOrderbook.getStopBuys();
            int sz = stopBuys.size();
            if(sz==0){
                String nodeId = rOrdernodeService.sProduce(bookId, stopPrice, vol, orderitemid);
                orderitem.setNodeId(nodeId);
                stopBuys.add(nodeId);
                rOrderbook.setStopBuys(stopBuys);
                save(bookId,rOrderbook);
                return;
            }
            for(;i<sz;i++){
                String bnodeId = stopBuys.get(i);
                ROrdernode bnode = rOrdernodeService.get(bnodeId);
                int bPrice = bnode.getPrice();
                if(stopPrice<bPrice){
                    String nodeId = rOrdernodeService.sProduce(bookId, stopPrice, vol, orderitemid);
                    orderitem.setNodeId(nodeId);
                    stopBuys.add(i, nodeId);
                    rOrderbook.setStopBuys(stopBuys);
                    save(bookId, rOrderbook);
                    return;
                }
                else if(stopPrice>bPrice){
                    continue;
                }
                else{
                    rOrdernodeService.addVol(bnodeId, vol, orderitemid);
                    orderitem.setNodeId(bnodeId);
                    return;
                }
            }
            String nodeId = rOrdernodeService.sProduce(bookId, stopPrice, vol, orderitemid);
            orderitem.setNodeId(nodeId);
            stopBuys.add(nodeId);
            rOrderbook.setStopBuys(stopBuys);
            save(bookId, rOrderbook);
            return;
        }
        else{
            ROrderbook rOrderbook = get(bookId);
            List<String> stopSells = rOrderbook.getStopSells();
            int sz = stopSells.size();
            if(sz==0){
                String nodeId = rOrdernodeService.sProduce(bookId, stopPrice, vol, orderitemid);
                orderitem.setNodeId(nodeId);
                stopSells.add(nodeId);
                rOrderbook.setStopSells(stopSells);
                save(bookId,rOrderbook);
                return;
            }
            for(;i<sz;i++){
                String snodeId = stopSells.get(i);
                ROrdernode snode = rOrdernodeService.get(snodeId);
                int sPrice = snode.getPrice();
                if(stopPrice>sPrice){
                    String nodeId = rOrdernodeService.sProduce(bookId, stopPrice, vol, orderitemid);
                    orderitem.setNodeId(nodeId);
                    stopSells.add(i, nodeId);
                    rOrderbook.setStopSells(stopSells);
                    save(bookId, rOrderbook);
                    return;
                }
                else if(stopPrice<sPrice){
                    continue;
                }
                else{
                    rOrdernodeService.addVol(snodeId, vol, orderitemid);
                    orderitem.setNodeId(snodeId);
                    return;
                }
            }
            String nodeId = rOrdernodeService.sProduce(bookId, stopPrice, vol, orderitemid);
            orderitem.setNodeId(nodeId);
            stopSells.add(nodeId);
            rOrderbook.setStopSells(stopSells);
            save(bookId, rOrderbook);
            return;
        }
    }

    @Override
    public void checkStop(String bookId){
        ROrderbook rOrderbook = get(bookId);
        boolean flag = false;

        //check stopBuys
        List<String> stopBuys = rOrderbook.getStopBuys();
        List<String> sells = rOrderbook.getSells();
        if(stopBuys.size()>0 && sells.size()>0){
            ROrdernode bnode = rOrdernodeService.get(stopBuys.get(0));
            int bPrice = bnode.getPrice();
            int sPrice = rOrdernodeService.get(sells.get(0)).getPrice();
            if(sPrice>=bPrice){
                //changeToMarket
                flag = true;
                Orderitem orderitem = orderitemRepository.findById(bnode.getOrders().get(0)).get();
                OrderSide side = OrderSide.BUY;
                int vol = orderitem.getVol();
                Long initiatorId = orderitem.getTrader().getTraderId();
                int sz = sells.size();
                int i = 0;
                int consume = 0;
                boolean turnout = false;
                for(;i<sz;i++){
                    String snodeId = sells.get(0);
                    ROrdernode snode = rOrdernodeService.get(snodeId);
                    sPrice = snode.getPrice();
                    int sVol = snode.getVol();
                    int rest = vol-consume;
                    if(rest==0){
                        break;
                    }
                    if(rest<sVol){
                        consume = vol;
                        rOrdernodeService.consume(snodeId, sPrice, rest, initiatorId, side);

                        //set rOrderbook
                        rOrderbook.setSells(sells);
                        List<Long> orders = bnode.getOrders();
                        orderitemRepository.deleteById(orders.get(0));
                        if(orders.size()==1){
                            stopBuys.remove(0);
                            rOrderbook.setStopBuys(stopBuys);
                        }
                        else{
                            orders.remove(0);
                            bnode.setOrders(orders);
                            rOrdernodeService.save(stopBuys.get(0),bnode);
                        }
                        save(bookId,rOrderbook);
                        turnout = true;
                    }
                    else if(rest == sVol){
                        consume = vol;
                        rOrdernodeService.consume(snodeId, sPrice, rest, initiatorId, side);

                        sells.remove(0);
                        rOrderbook.setSells(sells);
                        List<Long> orders = bnode.getOrders();
                        orderitemRepository.deleteById(orders.get(0));
                        if(orders.size()==1){
                            stopBuys.remove(0);
                            rOrderbook.setStopBuys(stopBuys);
                        }
                        else{
                            orders.remove(0);
                            bnode.setOrders(orders);
                            rOrdernodeService.save(stopBuys.get(0),bnode);
                        }
                        save(bookId,rOrderbook);
                        turnout = true;
                    }
                    else{
                        consume+=sVol;
                        rOrdernodeService.consume(snodeId,sPrice,rest,initiatorId,side);

                        sells.remove(0);
                    }
                }
                if(!turnout){
                    rOrderbook.setSells(sells);
                    List<Long> orders = bnode.getOrders();
                    orderitemRepository.deleteById(orders.get(0));
                    if(orders.size()==1){
                        stopBuys.remove(0);
                        rOrderbook.setStopBuys(stopBuys);
                    }
                    else{
                        orders.remove(0);
                        bnode.setOrders(orders);
                        rOrdernodeService.save(stopBuys.get(0),bnode);
                    }
                    save(bookId, rOrderbook);
                }
            }
        }

        //another side
        List<String> stopSells = rOrderbook.getStopSells();
        List<String> buys = rOrderbook.getBuys();
        if(stopSells.size()>0 && buys.size()>0){
            ROrdernode snode = rOrdernodeService.get(stopSells.get(0));
            int sPrice = snode.getPrice();
            int bPrice = rOrdernodeService.get(buys.get(0)).getPrice();
            if(bPrice<=sPrice){
                //changeToMarket
                flag = true;
                Orderitem orderitem = orderitemRepository.findById(snode.getOrders().get(0)).get();
                OrderSide side = OrderSide.SELL;
                int vol = orderitem.getVol();
                Long initiatorId = orderitem.getTrader().getTraderId();
                int sz = buys.size();
                int i = 0;
                int consume = 0;
                boolean turnout = false;
                for(;i<sz;i++){
                    String bnodeId = buys.get(0);
                    ROrdernode bnode = rOrdernodeService.get(bnodeId);
                    bPrice = bnode.getPrice();
                    int bVol = bnode.getVol();
                    int rest = vol-consume;
                    if(rest==0){
                        break;
                    }
                    if(rest<bVol){
                        consume = vol;
                        rOrdernodeService.consume(bnodeId, bPrice, rest, initiatorId, side);

                        //set rOrderbook
                        rOrderbook.setBuys(buys);
                        List<Long> orders = snode.getOrders();
                        orderitemRepository.deleteById(orders.get(0));
                        if(orders.size()==1){
                            stopSells.remove(0);
                            rOrderbook.setStopSells(stopSells);
                        }
                        else{
                            orders.remove(0);
                            snode.setOrders(orders);
                            rOrdernodeService.save(stopSells.get(0),snode);
                        }
                        save(bookId,rOrderbook);
                        turnout = true;
                    }
                    else if(rest == bVol){
                        consume = vol;
                        rOrdernodeService.consume(bnodeId, bPrice, rest, initiatorId, side);

                        buys.remove(0);
                        rOrderbook.setBuys(buys);
                        List<Long> orders = snode.getOrders();
                        orderitemRepository.deleteById(orders.get(0));
                        if(orders.size()==1){
                            stopSells.remove(0);
                            rOrderbook.setStopSells(stopSells);
                        }
                        else{
                            orders.remove(0);
                            snode.setOrders(orders);
                            rOrdernodeService.save(stopSells.get(0),snode);
                        }
                        save(bookId,rOrderbook);
                        turnout = true;
                    }
                    else{
                        consume+=bVol;
                        rOrdernodeService.consume(bnodeId,bPrice,rest,initiatorId,side);

                        buys.remove(0);
                    }
                }
                if(!turnout){
                    rOrderbook.setBuys(buys);
                    List<Long> orders = snode.getOrders();
                    orderitemRepository.deleteById(orders.get(0));
                    if(orders.size()==1){
                        stopSells.remove(0);
                        rOrderbook.setStopSells(stopSells);
                    }
                    else{
                        orders.remove(0);
                        snode.setOrders(orders);
                        rOrdernodeService.save(stopSells.get(0),snode);
                    }
                    save(bookId, rOrderbook);
                }
            }
        }
        if(flag){
            checkStop(bookId);
        }
    }

    @Override
    public int consumeLimit(String bookId, OrderSide side, int price, int vol, Long initiatorId) {
        int consume = 0;
        int i = 0;
        ROrderbook rOrderbook = get(bookId);
        if (side==OrderSide.BUY) {
            List<String> sells = rOrderbook.getSells();
            int sz = sells.size();
            for (; i < sz; i++) {
                String snodeId = sells.get(0);
                ROrdernode snode = rOrdernodeService.get(snodeId);
                int sPrice = snode.getPrice();
                if (price < sPrice) {
                    rOrderbook.setSells(sells);
                    save(bookId, rOrderbook);
                    return consume;
                }
                int sVol = snode.getVol();
                int rest = vol - consume;
                if (rest < sVol) {
                    consume = vol;
                    //node consume
                    rOrdernodeService.consume(snodeId, price, rest, initiatorId, side);

                    //set rOrderbook
                    rOrderbook.setSells(sells);
                    save(bookId, rOrderbook);

                    return consume;
                } else if (rest == sVol) {
                    consume = vol;
                    //node consume
                    rOrdernodeService.consume(snodeId, price, rest, initiatorId, side);
                    //del node
                    sells.remove(0);
                    //set rOrderbook
                    rOrderbook.setSells(sells);
                    save(bookId, rOrderbook);

                    return consume;
                } else {
                    consume += sVol;
                    //node consume
                    rOrdernodeService.consume(snodeId, price, rest, initiatorId, side);
                    //del node
                    sells.remove(0);
                }
            }
            //set rOrderbook
            rOrderbook.setSells(sells);
            save(bookId, rOrderbook);
            return consume;
        }
        //else if(side.equals("sell"))
        else {
            List<String> buys = rOrderbook.getBuys();
            int sz = buys.size();
            for (; i < sz; i++) {
                String bnodeId = buys.get(0);
                ROrdernode bnode = rOrdernodeService.get(bnodeId);
                int bPrice = bnode.getPrice();
                if (price > bPrice) {
                    rOrderbook.setBuys(buys);
                    save(bookId, rOrderbook);
                    return consume;
                }
                int bVol = bnode.getVol();
                int rest = vol - consume;
                if (rest < bVol) {
                    consume = vol;
                    //node consume
                    rOrdernodeService.consume(bnodeId, price, rest, initiatorId, side);

                    //set rOrderbook
                    rOrderbook.setBuys(buys);
                    save(bookId, rOrderbook);

                    return consume;
                } else if (rest == bVol) {
                    consume = vol;
                    //node consume
                    rOrdernodeService.consume(bnodeId, price, rest, initiatorId, side);
                    //del node
                    buys.remove(0);
                    //set rOrderbook
                    rOrderbook.setBuys(buys);
                    save(bookId, rOrderbook);

                    return consume;
                } else {
                    consume += bVol;
                    //node consume
                    rOrdernodeService.consume(bnodeId, price, rest, initiatorId, side);
                    //del node
                    buys.remove(0);
                }
            }
            //set rOrderbook
            rOrderbook.setBuys(buys);
            save(bookId, rOrderbook);
            return consume;
        }

    }

    @Override
    public int consumeMarket(String bookId, OrderSide side, int vol, Long initiatorId) {
        int consume = 0;
        int i = 0;
        ROrderbook rOrderbook = get(bookId);
        if (side==OrderSide.BUY) {
            List<String> sells = rOrderbook.getSells();
            int sz = sells.size();
            for (; i < sz; i++) {
                String snodeId = sells.get(0);
                ROrdernode snode = rOrdernodeService.get(snodeId);
                int sPrice = snode.getPrice();
                int sVol = snode.getVol();
                int rest = vol - consume;
                if (rest < sVol) {
                    consume = vol;
                    //node consume
                    rOrdernodeService.consume(snodeId, sPrice, rest, initiatorId, side);

                    //set rOrderbook
                    rOrderbook.setSells(sells);
                    save(bookId, rOrderbook);

                    return consume;
                } else if (rest == sVol) {
                    consume = vol;
                    //node consume
                    rOrdernodeService.consume(snodeId, sPrice, rest, initiatorId, side);
                    //del node
                    sells.remove(0);
                    //set rOrderbook
                    rOrderbook.setSells(sells);
                    save(bookId, rOrderbook);

                    return consume;
                } else {
                    consume += sVol;
                    //node consume
                    rOrdernodeService.consume(snodeId, sPrice, rest, initiatorId, side);
                    //del node
                    sells.remove(0);
                }
            }
            //set rOrderbook
            rOrderbook.setSells(sells);
            save(bookId, rOrderbook);
            return consume;
        }
        //side.equals("sell")
        else {
            List<String> buys = rOrderbook.getBuys();
            int sz = buys.size();
            for (; i < sz; i++) {
                String bnodeId = buys.get(0);
                ROrdernode bnode = rOrdernodeService.get(bnodeId);
                int bPrice = bnode.getPrice();
                int bVol = bnode.getVol();
                int rest = vol - consume;
                if (rest < bVol) {
                    consume = vol;
                    //node consume
                    rOrdernodeService.consume(bnodeId, bPrice, rest, initiatorId, side);

                    //set rOrderbook
                    rOrderbook.setBuys(buys);
                    save(bookId, rOrderbook);

                    return consume;
                } else if (rest == bVol) {
                    consume = vol;
                    //node consume
                    rOrdernodeService.consume(bnodeId, bPrice, rest, initiatorId, side);
                    //del node
                    buys.remove(0);
                    //set rOrderbook
                    rOrderbook.setBuys(buys);
                    save(bookId, rOrderbook);

                    return consume;
                }
                else {
                    consume += bVol;
                    //node consume
                    rOrdernodeService.consume(bnodeId, bPrice, rest, initiatorId, side);
                    //del node
                    buys.remove(0);
                }
            }
            //set rOrderbook
            rOrderbook.setBuys(buys);
            save(bookId, rOrderbook);
            return consume;
        }
    }

    @Override
    public boolean consumeStop(String bookId, OrderSide side, int stopPrice, int vol, Long traderId) {
        boolean flag = false;
        int consume = 0;
        ROrderbook rOrderbook = get(bookId);
        if(side==OrderSide.BUY){
            List<String> sells = rOrderbook.getSells();
            if(sells.size()==0){
                return flag;
            }
            int sPrice = rOrdernodeService.get(sells.get(0)).getPrice();
            if(sPrice >= stopPrice){
                //convert into market order
                consume = consumeMarket(bookId,side,vol,traderId);
                flag = true;
            }
        }
        else{
            List<String> buys = rOrderbook.getBuys();
            if(buys.size()==0){
                return flag;
            }
            int bPrice = rOrdernodeService.get(buys.get(0)).getPrice();
            if(bPrice <= stopPrice){
                //convert into market order
                consume = consumeMarket(bookId,side,vol,traderId);
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public int cancel(Long orderId) {
        String nodeId = orderitemRepository.findById(orderId).get().getNodeId();
        int Iindex = nodeId.indexOf('I');
        int Pindex = nodeId.indexOf('P');
        Integer brokerId = Integer.valueOf(nodeId.substring(1,Iindex));
        Integer instrumentId = Integer.valueOf(nodeId.substring(Iindex+1,Pindex));
        String bookId = "B"+String.valueOf(brokerId)+"I"+String.valueOf(instrumentId);
        ROrderbook rOrderbook = get(bookId);

        char s = nodeId.charAt(nodeId.length()-1);

        if(s=='S'){
            List<String> stopBuys = rOrderbook.getStopBuys();
            List<String> stopSells = rOrderbook.getStopSells();
            for(int i=0;i<stopBuys.size();i++){
                if(nodeId.equals(stopBuys.get(i))){
                    if(rOrdernodeService.get(nodeId).getOrders().size()==1){
                        stopBuys.remove(i);
                        rOrderbook.setStopBuys(stopBuys);
                        save(bookId,rOrderbook);
                    }
                    return rOrdernodeService.delOrder(nodeId,orderId);
                }
            }
            for(int j=0;j<stopSells.size();j++){
                if(nodeId.equals((stopSells.get(j)))){
                    if(rOrdernodeService.get(nodeId).getOrders().size()==1){
                        stopSells.remove(j);
                        rOrderbook.setStopSells(stopSells);
                        save(bookId,rOrderbook);
                    }
                    return rOrdernodeService.delOrder(nodeId,orderId);
                }
            }
        }
        else{
            List<String> buys = rOrderbook.getBuys();
            List<String> sells = rOrderbook.getSells();
            for(int i=0;i<buys.size();i++){
                if(nodeId.equals(buys.get(i))){
                    if(rOrdernodeService.get(nodeId).getOrders().size()==1){
                        buys.remove(i);
                        rOrderbook.setBuys(buys);
                        save(bookId,rOrderbook);
                    }
                    return rOrdernodeService.delOrder(nodeId,orderId);
                }
            }
            for(int j=0;j<sells.size();j++){
                if(nodeId.equals(sells.get(j))){
                    if(rOrdernodeService.get(nodeId).getOrders().size()==1){
                        sells.remove(j);
                        rOrderbook.setSells(sells);
                        save(bookId,rOrderbook);
                    }
                    return rOrdernodeService.delOrder(nodeId,orderId);
                }
            }

        }
        return 0;
    }

    @Override
    public boolean transferOrder(byte[] data) {
        String topicExchangeName = "exchange";
        String queueName = "orderBook";
        rabbitTemplate.convertAndSend(topicExchangeName, queueName, data);
        return true;
    }

    @Override
    public void publishOrderBook(String bookId) {
        byte[] rOrderbook_byte = redisConnection.get(bookId.getBytes());
        ROrderbook rOrderbook = (ROrderbook) SerializeUtil.unserialize(rOrderbook_byte);
        OrderbookVO orderbookVO = new OrderbookVO();
        orderbookVO.setOrderbookId(rOrderbook.getOrderBookId());
        orderbookVO.setBuysFive(getBuyFive(rOrderbook));
        orderbookVO.setSellsFive(getSellFive(rOrderbook));
        System.out.println(Arrays.toString(ProtostuffUtils.serialize(orderbookVO)));
        transferOrder(ProtostuffUtils.serialize(orderbookVO));
    }

    @Override
    public void createOrderBook(String bookId) {
        ROrderbook rOrderbook = new ROrderbook();
        rOrderbook.setOrderBookId(bookId);
        save(bookId, rOrderbook);
        OrderbookVO orderbookVO = new OrderbookVO();
        orderbookVO.setOrderbookId(bookId);
        log.info(orderbookVO.toString());
        transferOrder(ProtostuffUtils.serialize(orderbookVO));
    }

    @Override
    public List<OrderNodeVO> getBuyFive(ROrderbook rOrderbook) {
        List<String> buys = rOrderbook.getBuys();
        List<OrderNodeVO> buysVO = new LinkedList<>();
        return extractFive(buysVO, buys);
    }

    @Override
    public List<OrderNodeVO> getSellFive(ROrderbook rOrderbook) {
        List<String> sells = rOrderbook.getSells();
        List<OrderNodeVO> sellsVO = new LinkedList<>();
        return extractFive(sellsVO, sells);
    }

    public List<OrderNodeVO> extractFive( List<OrderNodeVO> dest, List<String> origin) {
        int length = origin.size();
        if (length == 0)
            return null;
        if (length > 5) {
            for (int i = 0; i < 5; i++) {
                String nodeId = origin.get(i);
                ROrdernode rOrdernode = rOrdernodeService.get(nodeId);
                OrderNodeVO orderNodeVO = new OrderNodeVO();
                orderNodeVO.setPrice(rOrdernode.getPrice());
                orderNodeVO.setVolumn(rOrdernode.getVol());
                dest.add(i, orderNodeVO);
            }
        }
        else {
            int index = 0;
            for (String nodeId: origin) {
                ROrdernode rOrdernode = rOrdernodeService.get(nodeId);
                OrderNodeVO orderNodeVO = new OrderNodeVO();
                orderNodeVO.setPrice(rOrdernode.getPrice());
                orderNodeVO.setVolumn(rOrdernode.getVol());
                dest.add(index, orderNodeVO);
                index++;
            }
        }
        return dest;
    }
}
