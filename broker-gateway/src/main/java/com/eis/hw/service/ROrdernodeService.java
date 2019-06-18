package com.eis.hw.service;

import com.eis.hw.enums.OrderSide;
import com.eis.hw.model.redisentity.ROrdernode;

public interface ROrdernodeService {
    public int consume(String nodeId, int price, int vol, Long initiatorId, OrderSide side);
    public void addVol(String nodeId, int vol, Long orderitemId);
    public String produce(String bookId, int price, int vol, Long orderitemId);
    public String sProduce(String bookId, int price, int vol, Long orderitemId);
    public void save(String s,ROrdernode rOrdernode);
    public int delOrder(String nodeId,Long orderId);
    public ROrdernode get(String s);
}
