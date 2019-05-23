package com.eis.hw.Service.Impl;

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
    public void insertOrderitem(String bookId, String side, int price, int vol, int orderitemid) {
        int i = 0;
        if (side.equals("buy")) {
            ROrderbook rOrderbook = get(bookId);
            List<String> buys = rOrderbook.getBuys();
            int sz = buys.size();
            if (sz == 0) {
                String nodeId = rOrdernodeService.produce(bookId, price, vol, orderitemid);
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
                    buys.add(i, nodeId);
                    rOrderbook.setBuys(buys);
                    save(bookId, rOrderbook);
                    return;
                } else if (price < bprice) {
                    continue;
                } else if (price == bprice) {
                    rOrdernodeService.addVol(bnodeId, vol, orderitemid);
                    return;
                }
            }
            String nodeId = rOrdernodeService.produce(bookId, price, vol, orderitemid);
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
                    sells.add(i, nodeId);
                    rOrderbook.setSells(sells);
                    save(bookId, rOrderbook);
                    return;
                } else if (price > sprice) {
                    continue;
                } else if (price == sprice) {
                    rOrdernodeService.addVol(snodeId, vol, orderitemid);
                    return;
                }
            }
            String nodeId = rOrdernodeService.produce(bookId, price, vol, orderitemid);
            sells.add(nodeId);
            rOrderbook.setSells(sells);
            save(bookId, rOrderbook);
            return;
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
}
