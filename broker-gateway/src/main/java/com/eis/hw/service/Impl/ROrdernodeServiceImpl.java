package com.eis.hw.service.Impl;

import com.eis.hw.dao.OrderitemRepository;
import com.eis.hw.enums.OrderSide;
import com.eis.hw.model.entity.Orderitem;
import com.eis.hw.model.redisentity.ROrderbook;
import com.eis.hw.model.redisentity.ROrdernode;
import com.eis.hw.service.ROrdernodeService;
import com.eis.hw.service.TradeService;
import com.eis.hw.util.ProtostuffUtils;
import com.eis.hw.util.RedisPool;
import com.eis.hw.util.RedisUtils;
import com.eis.hw.util.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ROrdernodeServiceImpl implements ROrdernodeService {

    private final OrderitemRepository orderitemRepository;

    private final TradeService tradeService;

    private final RedisUtils redisUtils;

    @Resource
    private RedisTemplate<String, ROrdernode> redisTemplate;

    @Autowired
    private RedisConnection redisConnection;

    @Autowired
    public ROrdernodeServiceImpl(OrderitemRepository orderitemRepository, TradeService tradeService, RedisUtils redisUtils) {
        this.orderitemRepository = orderitemRepository;
        this.tradeService = tradeService;
        this.redisUtils = redisUtils;
    }

    @Override
    //return rest
    public int consume(String nodeId, int ini_price, int vol, Long initiatorId, OrderSide side) {
        ROrdernode rOrdernode = get(nodeId);
        int nodeVol = rOrdernode.getVol();
        int Iindex = nodeId.indexOf('I');
        int Pindex = nodeId.indexOf('P');
        //Integer brokerId = Integer.valueOf(nodeId.substring(1,Iindex));
        Long instrumentId = Long.valueOf(nodeId.substring(Iindex+1,Pindex));

        double price = (Integer.valueOf(nodeId.substring(Pindex+1))+ini_price)/2.0;
        List<Long> orders = rOrdernode.getOrders();
        int consume = 0;
        int sz = orders.size();
        for(int i=0;i<sz;i++){
            Long orderId = orders.get(0);
            Orderitem orderitem = orderitemRepository.findById(orderId).get();
            if(orderitem==null)continue;
            int orderVol = orderitem.getVol();
            Long compleId = orderitem.getTrader().getTraderId();
            if(orderVol>vol-consume){
                //update orderitem
                orderitem.setVol(orderVol-vol+consume);
                orderitemRepository.save(orderitem);
                rOrdernode.setOrders(orders);
                rOrdernode.setVol(nodeVol-vol);
                save(nodeId,rOrdernode);

                //generate trade
                tradeService.save(instrumentId,price,vol-consume,initiatorId,compleId,side.getStatus());

                return 0;
            }
            else if(orderVol == vol-consume){

                //del orderitem
                orderitemRepository.deleteById(orderId);
                orders.remove(0);
                rOrdernode.setOrders(orders);
                save(nodeId,rOrdernode);

                //generate trade
                tradeService.save(instrumentId,price,orderVol,initiatorId,compleId,side.getStatus());

                return 0;
            }
            else if(orderVol < vol-consume){
                consume+=orderVol;

                //del orderitem
                orderitemRepository.deleteById(orderId);
                orders.remove(0);

                //generate trade
                tradeService.save(instrumentId,price,orderVol,initiatorId,compleId,side.getStatus());

            }
        }
        rOrdernode.setOrders(orders);
        save(nodeId,rOrdernode);
        return vol-consume;
    }

    @Override
    public void addVol(String nodeId, int vol, Long orderitemId) {
        ROrdernode rOrdernode = get(nodeId);
        int nodeVol = rOrdernode.getVol();
        nodeVol += vol;
        rOrdernode.setVol(nodeVol);
        rOrdernode.addOrderitem(orderitemId);
        save(nodeId,rOrdernode);
    }

    @Override
    public String produce(String bookId, int price, int vol, Long orderitemid) {
        ROrdernode rOrdernode = new ROrdernode();
        rOrdernode.setVol(vol);
        rOrdernode.setPrice(price);
        rOrdernode.addOrderitem(orderitemid);
        String nodeId = bookId + "P" + String.valueOf(price);
        save(nodeId,rOrdernode);
        return nodeId;
    }

    @Override
    public String sProduce(String bookId, int price, int vol, Long orderitemid) {
        ROrdernode rOrdernode = new ROrdernode();
        rOrdernode.setVol(vol);
        rOrdernode.setPrice(price);
        rOrdernode.addOrderitem(orderitemid);
        String nodeId = bookId + "P" + String.valueOf(price)+"S";
        save(nodeId,rOrdernode);
        return nodeId;
    }

    @Override
    public void save(String s, ROrdernode rOrdernode) {
        byte[] rOrdernode_byte = SerializeUtil.serialize(rOrdernode);
        redisConnection.set(s.getBytes(), rOrdernode_byte);
    }

    @Override
    public int delOrder(String nodeId,Long orderId) {
        ROrdernode rOrdernode = get(nodeId);
        List<Long> orders = rOrdernode.getOrders();
        for(int i=0;i<orders.size();i++){

            //exist
            if(String.valueOf(orders.get(i)).equals(String.valueOf(orderId))){
                int res = orderitemRepository.findById(orderId).get().getVol();
                orderitemRepository.deleteById(orderId);
                orders.remove(i);
                rOrdernode.setOrders(orders);
                save(nodeId,rOrdernode);

                return res;
            }
        }
        return 0;
    }

    @Override
    public ROrdernode get(String s) {
        JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();
        byte[] rOrdernode_byte = redisConnection.get(s.getBytes());
        ROrdernode rOrdernode = (ROrdernode) serializer.deserialize(rOrdernode_byte);
        return rOrdernode;
    }


}
