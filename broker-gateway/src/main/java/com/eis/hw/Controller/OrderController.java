package com.eis.hw.Controller;

import com.eis.hw.DAO.OrderitemRepository;
import com.eis.hw.Model.Entity.Orderbook;
import com.eis.hw.Model.Entity.Orderitem;
import com.eis.hw.Model.RedisEntity.ROrderbook;
import com.eis.hw.Service.OrderbookService;
import com.eis.hw.Service.ROrderbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Scanner;

@Controller
public class OrderController {
    @Autowired
    private ROrderbookService rOrderbookService;

    @Autowired
    private OrderbookService orderbookService;

    @Autowired
    OrderitemRepository orderitemRepository;

    @GetMapping(value="show")
    @ResponseBody
    public Orderbook show(){
        ROrderbook rOrderbook = rOrderbookService.get("B1I1");
        Orderbook orderbook = orderbookService.construct(rOrderbook);
        //orderbookService.showOrderbook(orderbook);
        return orderbook;
    }

    @GetMapping(value = "init")
    @ResponseBody
    public void init(){
        //init
        ROrderbook rob = new ROrderbook();
        rOrderbookService.save("B1I1",rob);
    }

    @PostMapping(value="cancel")
    @ResponseBody
    public void getCancelOrder(Integer orderId){
        int broker_id = 1;

        int remain = rOrderbookService.cancel(orderId);
        System.out.println("remain "+remain);

    }

    @PostMapping(value = "stop")
    @ResponseBody
    public void getStopOrder(int trader_id,int stopPrice,int qty,String side){
        int broker_id = 1;
        int instrument_id = 1;

        //load orderbook
        String bookId = "B"+String.valueOf(broker_id)+"I"+String.valueOf(instrument_id);

        //check and consume order
        if(rOrderbookService.consumeStop(bookId,side,stopPrice,qty,trader_id)){
            return;
        }

        //else insert it
        Orderitem orderitem = new Orderitem();
        orderitem.setBrokerId(broker_id);
        orderitem.setTraderId(trader_id);
        orderitem.setVol(qty);

        orderitem = orderitemRepository.save(orderitem);
        rOrderbookService.insertStopOrderitem(bookId,side,stopPrice,qty,orderitem);
        orderitem = orderitemRepository.save(orderitem);

        rOrderbookService.checkStop(bookId);

    }

    @PostMapping(value="market")
    @ResponseBody
    public void getMarketOrder(int trader_id,int qty,String side){
        int broker_id = 1;
        int instrument_id = 1;

        //load orderbook
        String bookId = "B"+String.valueOf(broker_id)+"I"+String.valueOf(instrument_id);

        int consume = rOrderbookService.consumeMarket(bookId,side,qty,trader_id);

        rOrderbookService.checkStop(bookId);
    }

    @PostMapping(value = "limit")
    @ResponseBody
    public void getLimitOrder(int trader_id,int price,int qty,String side){

        //get args
        int broker_id = 1;
        int instrument_id = 1;

        //load orderbook
        String bookId = "B"+String.valueOf(broker_id)+"I"+String.valueOf(instrument_id);

        //check and consume order
        int consume = rOrderbookService.consumeLimit(bookId,side,price,qty,trader_id);
        System.out.println("consume:"+String.valueOf(consume));

        //if rest
        int rest = qty-consume;
        if(rest ==0){
            return;
        }
        Orderitem orderitem = new Orderitem();
        orderitem.setBrokerId(broker_id);
        orderitem.setTraderId(trader_id);
        orderitem.setVol(rest);

        orderitem = orderitemRepository.save(orderitem);
        rOrderbookService.insertOrderitem(bookId,side,price,rest,orderitem);
        orderitemRepository.save(orderitem);

        rOrderbookService.checkStop(bookId);

    }
}
