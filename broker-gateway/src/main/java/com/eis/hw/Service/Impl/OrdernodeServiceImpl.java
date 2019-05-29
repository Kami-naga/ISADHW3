package com.eis.hw.Service.Impl;

import com.eis.hw.DAO.OrderitemRepository;
import com.eis.hw.Model.Entity.Orderitem;
import com.eis.hw.Model.Entity.Ordernode;
import com.eis.hw.Model.RedisEntity.ROrdernode;
import com.eis.hw.Service.OrdernodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdernodeServiceImpl implements OrdernodeService {

    @Autowired
    private OrderitemRepository orderitemRepository;

    @Override
    public Ordernode construct(ROrdernode rOrdernode) {
        Ordernode ordernode = new Ordernode();
        ordernode.setPrice(rOrdernode.getPrice());
        ordernode.setVol(rOrdernode.getVol());
        List<Integer> orders = rOrdernode.getOrders();
        for(int i=0;i<orders.size();i++){
            int orderId = orders.get(i);
            Orderitem orderitem = orderitemRepository.findById(orderId).get();
            ordernode.addOrder(orderitem);
        }
        return ordernode;
    }
}
