package com.eis.hw.Service;

import com.eis.hw.Model.RedisEntity.ROrdernode;

public interface ROrdernodeService {
    public int consume(String nodeId,int price,int vol,int initiatorId,String side);
    public void addVol(String nodeId,int vol,int orderitemId);
    public String produce(String bookId,int price,int vol,int orderitemId);
    public String sProduce(String bookId,int price,int vol,int orderitemId);
    public void save(String s,ROrdernode rOrdernode);
    public int delOrder(String nodeId,Integer orderId);
    public ROrdernode get(String s);
}
