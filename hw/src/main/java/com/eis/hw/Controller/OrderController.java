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
    public void show(){
        ROrderbook rOrderbook = rOrderbookService.get("B1I1");
        Orderbook orderbook = orderbookService.construct(rOrderbook);
        orderbookService.showOrderbook(orderbook);
    }

    @GetMapping(value = "init")
    @ResponseBody
    public void init(){
        //init
        ROrderbook rob = new ROrderbook();
        rOrderbookService.save("B1I1",rob);
    }

    @GetMapping(value="cancel")
    @ResponseBody
    public void getCancelOrder(){
        int broker_id = 1;
        Integer orderId = 0;
        Scanner scanner = new Scanner(System.in);
        String read = "";

        System.out.println("order id");
        read = scanner.nextLine();
        orderId = Integer.valueOf(read);

        int remain = rOrderbookService.cancel(orderId);
        System.out.println("remain "+remain);

    }

    @GetMapping(value = "stop")
    @ResponseBody
    public void getStopOrder(){
        int broker_id = 1;
        int trader_id = 1;
        int instrument_id = 1;
        int stopPrice = 1544;
        int qty = 50;
        String side = "buy";

        Scanner scanner = new Scanner(System.in);
        String read = "";

        System.out.println("trader id");
        read = scanner.nextLine();
        trader_id = Integer.valueOf(read);

        System.out.println("stopPrice");
        read = scanner.nextLine();
        stopPrice = Integer.valueOf(read);

        System.out.println("qty");
        read = scanner.nextLine();
        qty = Integer.valueOf(read);

        System.out.println("side");
        read = scanner.nextLine();
        side = read;

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

    @GetMapping(value="market")
    @ResponseBody
    public void getMarketOrder(){
        int broker_id = 1;
        int trader_id = 1;
        int instrument_id = 1;
        int qty = 50;
        String side = "buy";

        Scanner scanner = new Scanner(System.in);
        String read = "";

        System.out.println("trader id");
        read = scanner.nextLine();
        trader_id = Integer.valueOf(read);

        System.out.println("qty");
        read = scanner.nextLine();
        qty = Integer.valueOf(read);

        System.out.println("side");
        read = scanner.nextLine();
        side = read;

        //load orderbook
        String bookId = "B"+String.valueOf(broker_id)+"I"+String.valueOf(instrument_id);

        int consume = rOrderbookService.consumeMarket(bookId,side,qty,trader_id);

        rOrderbookService.checkStop(bookId);
    }

    @GetMapping(value = "limit")
    @ResponseBody
    public void getLimitOrder(){

        //get args
        int broker_id = 1;
        int trader_id = 1;
        int instrument_id = 1;
        int qty = 50;
        int price = 1244;
        String side = "buy";

        Scanner scanner = new Scanner(System.in);
        String read = "";

        System.out.println("trader id");
        read = scanner.nextLine();
        trader_id = Integer.valueOf(read);

        System.out.println("price");
        read = scanner.nextLine();
        price = Integer.valueOf(read);

        System.out.println("qty");
        read = scanner.nextLine();
        qty = Integer.valueOf(read);

        System.out.println("side");
        read = scanner.nextLine();
        side = read;


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
