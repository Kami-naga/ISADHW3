package com.eis.hw.controller;

import com.eis.hw.dao.BrokerRepository;
import com.eis.hw.dao.OrderitemRepository;
import com.eis.hw.dao.TraderRepository;
import com.eis.hw.dto.OrderDTO;
import com.eis.hw.enums.OrderSide;
import com.eis.hw.model.entity.Orderbook;
import com.eis.hw.model.entity.Orderitem;
import com.eis.hw.model.redisentity.ROrderbook;
import com.eis.hw.model.redisentity.ROrdernode;
import com.eis.hw.service.OrderbookService;
import com.eis.hw.service.ROrderbookService;
import com.eis.hw.util.ProtostuffUtils;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {
    private final ROrderbookService rOrderbookService;

    private final OrderbookService orderbookService;

    private final OrderitemRepository orderitemRepository;

    private final BrokerRepository brokerRepository;

    private final TraderRepository traderRepository;

    @Autowired
    private RedisConnection redisConnection;

    @Autowired
    public OrderController(ROrderbookService rOrderbookService, OrderbookService orderbookService, OrderitemRepository orderitemRepository, BrokerRepository brokerRepository, TraderRepository traderRepository) {
        this.rOrderbookService = rOrderbookService;
        this.orderbookService = orderbookService;
        this.orderitemRepository = orderitemRepository;
        this.brokerRepository = brokerRepository;
        this.traderRepository = traderRepository;
    }

    @PostMapping(value="showDetail")
    @ResponseBody
    public Orderbook showDetail(Long broker_id,Long instrument_id){
        String bookId = "B"+String.valueOf(broker_id)+"I"+String.valueOf(instrument_id);

        ROrderbook rOrderbook = rOrderbookService.get(bookId);
        Orderbook orderbook = orderbookService.construct(rOrderbook);
        //orderbookService.showOrderbook(orderbook);
        return orderbook;
    }

    @PostMapping("/test")
    public void test(Long broker_id,Long instrument_id){
        String bookId = "B"+String.valueOf(broker_id)+"I"+String.valueOf(instrument_id);
        byte[] data = redisConnection.get(bookId.getBytes());
        System.out.println(data);
        System.out.println(ProtostuffUtils.deserialize(data, ROrderbook.class));
    }

    @GetMapping(value = "init")
    @ResponseBody
    public void init(){
        //init
//        ROrderbook rob = new ROrderbook();
//        rOrderbookService.save("B1I1",rob);
        rOrderbookService.init();
    }

    @PostMapping(value="cancel")
    @ResponseBody
    public void getCancelOrder(Long orderId){

        int remain = rOrderbookService.cancel(orderId);
        System.out.println("remain "+remain);

    }

    @PostMapping(value = "stop")
    @ResponseBody
    public void getStopOrder(Long trader_id, int stopPrice, int qty, String side, Long broker_id, Long instrument_id){

        //load orderbook
        String bookId = "B"+String.valueOf(broker_id)+"I"+String.valueOf(instrument_id);
        OrderSide orderSide = side.equals("buy")?OrderSide.BUY:OrderSide.SELL;
        //check and consume order
        if(rOrderbookService.consumeStop(bookId,orderSide,stopPrice,qty,trader_id)){
            return;
        }

        //else insert it
        Orderitem orderitem = new Orderitem();
        orderitem.setBroker(brokerRepository.findById(broker_id).get());
        orderitem.setTrader(traderRepository.findById(trader_id).get());
        orderitem.setVol(qty);

        orderitem = orderitemRepository.save(orderitem);
        rOrderbookService.insertStopOrderitem(bookId,orderSide,stopPrice,qty,orderitem);
        orderitem = orderitemRepository.save(orderitem);

        rOrderbookService.checkStop(bookId);

    }

    @PostMapping(value="market")
    @ResponseBody
    public void getMarketOrder(Long trader_id, int qty, String side, Long broker_id, Long instrument_id){

        //load orderbook
        OrderSide orderSide = side.equals("buy")?OrderSide.BUY:OrderSide.SELL;
        String bookId = "B"+String.valueOf(broker_id)+"I"+String.valueOf(instrument_id);

        int consume = rOrderbookService.consumeMarket(bookId,orderSide,qty,trader_id);

        rOrderbookService.checkStop(bookId);
    }

    @PostMapping(value = "limit")
    @ResponseBody
    public void getLimitOrder(@RequestBody OrderDTO orderDTO){

        //load orderbook
        Long trader_id = orderDTO.getTraderId();
        int price = orderDTO.getPrice();
        int qty = orderDTO.getQty();
        OrderSide side = orderDTO.getOrderSide();
        Long broker_id = orderDTO.getBrokerId();
        Long instrument_id = orderDTO.getInstrumentId();
        System.out.println(orderDTO.toString());
//        Long trader_id, int price, int qty, String side, Long broker_id, Long instrument_id
        String bookId = "B"+String.valueOf(broker_id)+"I"+String.valueOf(instrument_id);
        OrderSide orderSide = side.equals("buy")?OrderSide.BUY:OrderSide.SELL;
        //check and consume order
        int consume = rOrderbookService.consumeLimit(bookId,orderSide,price,qty,trader_id);
        System.out.println("consume:"+String.valueOf(consume));

        //if rest
        int rest = qty-consume;
        if(rest ==0){
            return;
        }
        Orderitem orderitem = new Orderitem();
        orderitem.setBroker(brokerRepository.findById(broker_id).get());
        orderitem.setTrader(traderRepository.findById(trader_id).get());
        orderitem.setVol(rest);

        orderitem = orderitemRepository.save(orderitem);
        rOrderbookService.insertOrderitem(bookId,orderSide,price,rest,orderitem);
        orderitemRepository.save(orderitem);

        rOrderbookService.checkStop(bookId);

    }
}
