package com.eis.hw.Service.Impl;

import com.eis.hw.DAO.OrderitemRepository;
import com.eis.hw.Model.Entity.Orderitem;
import com.eis.hw.Model.RedisEntity.ROrdernode;
import com.eis.hw.Service.ROrdernodeService;
import com.eis.hw.Service.TradeService;
import com.eis.hw.Util.RedisPool;
import com.eis.hw.Util.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ROrdernodeServiceImpl implements ROrdernodeService {

    @Autowired
    private OrderitemRepository orderitemRepository;

    @Autowired
    private TradeService tradeService;

    @Override
    //return rest
    public int consume(String nodeId,int ini_price, int vol, int initiatorId, String side) {
        ROrdernode rOrdernode = get(nodeId);
        int nodeVol = rOrdernode.getVol();
        int Iindex = nodeId.indexOf('I');
        int Pindex = nodeId.indexOf('P');
        //Integer brokerId = Integer.valueOf(nodeId.substring(1,Iindex));
        Integer instrumentId = Integer.valueOf(nodeId.substring(Iindex+1,Pindex));

        double price = (Integer.valueOf(nodeId.substring(Pindex+1))+ini_price)/2.0;
        List<Integer> orders = rOrdernode.getOrders();
        int consume = 0;
        int sz = orders.size();
        for(int i=0;i<sz;i++){
            int orderId = orders.get(0);
            Orderitem orderitem = orderitemRepository.findById(orderId).get();
            int orderVol = orderitem.getVol();
            Integer compleId = orderitem.getTraderId();
            if(orderVol>vol-consume){
                //update orderitem
                orderitem.setVol(orderVol-vol+consume);
                orderitemRepository.save(orderitem);
                rOrdernode.setOrders(orders);
                rOrdernode.setVol(nodeVol-vol);
                save(nodeId,rOrdernode);

                //generate trade
                tradeService.save(instrumentId,price,vol-consume,initiatorId,compleId,side);

                return 0;
            }
            else if(orderVol == vol-consume){

                //del orderitem
                orderitemRepository.deleteById(orderId);
                orders.remove(0);
                rOrdernode.setOrders(orders);
                save(nodeId,rOrdernode);

                //generate trade
                tradeService.save(instrumentId,price,orderVol,initiatorId,compleId,side);

                return 0;
            }
            else if(orderVol < vol-consume){
                consume+=orderVol;

                //del orderitem
                orderitemRepository.deleteById(orderId);
                orders.remove(0);

                //generate trade
                tradeService.save(instrumentId,price,orderVol,initiatorId,compleId,side);

            }
        }
        rOrdernode.setOrders(orders);
        save(nodeId,rOrdernode);
        return vol-consume;
    }

    @Override
    public void addVol(String nodeId, int vol, int orderitemId) {
        ROrdernode rOrdernode = get(nodeId);
        int nodeVol = rOrdernode.getVol();
        nodeVol += vol;
        rOrdernode.setVol(nodeVol);
        rOrdernode.addOrderitem(orderitemId);
        save(nodeId,rOrdernode);
    }

    @Override
    public String produce(String bookId, int price, int vol, int orderitemid) {
        ROrdernode rOrdernode = new ROrdernode();
        rOrdernode.setVol(vol);
        rOrdernode.setPrice(price);
        rOrdernode.addOrderitem(orderitemid);
        String nodeId = bookId + "P" + String.valueOf(price);
        save(nodeId,rOrdernode);
        return nodeId;
    }

    @Override
    public void save(String s, ROrdernode rOrdernode) {
        RedisPool.getJedis().set(s.getBytes(), SerializeUtil.serialize(rOrdernode));
    }

    @Override
    public ROrdernode get(String s) {
        byte[] rob = RedisPool.getJedis().get(s.getBytes());
        return (ROrdernode)SerializeUtil.unserialize(rob);
    }



}
