package com.eis.hw.Service.Impl;

import com.eis.hw.DAO.OrderitemRepository;
import com.eis.hw.Model.Entity.Orderitem;
import com.eis.hw.Model.RedisEntity.ROrderbook;
import com.eis.hw.Model.RedisEntity.ROrdernode;
import com.eis.hw.Service.ROrderbookService;
import com.eis.hw.Service.ROrdernodeService;
import com.eis.hw.Util.RedisPool;
import com.eis.hw.Util.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ROrderbookServiceImpl implements ROrderbookService {

    @Autowired
    private ROrdernodeService rOrdernodeService;

    @Autowired
    private OrderitemRepository orderitemRepository;

    @Override
    public void save(String s, ROrderbook rOrderbook) {
        RedisPool.getJedis().set(s.getBytes(), SerializeUtil.serialize(rOrderbook));
    }

    @Override
    public ROrderbook get(String s) {
        byte[] rob = RedisPool.getJedis().get(s.getBytes());
        return (ROrderbook) SerializeUtil.unserialize(rob);
    }

    @Override
    public void insertOrderitem(String bookId, String side, int price, int vol, Orderitem orderitem) {
        Integer orderitemid = orderitem.getOrderId();
        int i = 0;
        if (side.equals("buy")) {
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
        } else if (side.equals("sell")) {
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
    public void insertStopOrderitem(String bookId, String side, int stopPrice, int vol, Orderitem orderitem) {
        Integer orderitemid = orderitem.getOrderId();
        int i=0;
        if(side.equals("buy")){
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
                String side = "buy";
                int vol = orderitem.getVol();
                int initiatorId = orderitem.getTraderId();
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
                        List<Integer> orders = bnode.getOrders();
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
                        List<Integer> orders = bnode.getOrders();
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
                    List<Integer> orders = bnode.getOrders();
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
                String side = "sell";
                int vol = orderitem.getVol();
                int initiatorId = orderitem.getTraderId();
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
                        List<Integer> orders = snode.getOrders();
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
                        List<Integer> orders = snode.getOrders();
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
                    List<Integer> orders = snode.getOrders();
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
    public int consumeLimit(String bookId, String side, int price, int vol, Integer initiatorId) {
        int consume = 0;
        int i = 0;
        ROrderbook rOrderbook = get(bookId);
        if (side.equals("buy")) {
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
    public int consumeMarket(String bookId, String side, int vol, Integer initiatorId) {
        int consume = 0;
        int i = 0;
        ROrderbook rOrderbook = get(bookId);
        if (side.equals("buy")) {
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
    public boolean consumeStop(String bookId, String side, int stopPrice, int vol, Integer traderId) {
        boolean flag = false;
        int consume = 0;
        ROrderbook rOrderbook = get(bookId);
        if(side.equals("buy")){
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
    public int cancel(Integer orderId) {
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
}
