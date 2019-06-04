package com.eis.hw.service.Impl;

import com.eis.hw.dao.OrderitemRepository;
import com.eis.hw.model.entity.Orderitem;
import com.eis.hw.model.entity.Ordernode;
import com.eis.hw.model.redisentity.ROrdernode;
import com.eis.hw.service.OrdernodeService;
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
        List<Long> orders = rOrdernode.getOrders();
        for(int i=0;i<orders.size();i++){
            Long orderId = orders.get(i);
            Orderitem orderitem = orderitemRepository.findById(orderId).get();
            ordernode.addOrder(orderitem);
        }
        return ordernode;
    }
}
