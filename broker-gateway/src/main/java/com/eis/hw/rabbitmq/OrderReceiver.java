package com.eis.hw.rabbitmq;


import com.eis.hw.dao.BrokerRepository;
import com.eis.hw.dao.OrderitemRepository;
import com.eis.hw.dao.TraderRepository;
import com.eis.hw.dto.OrderDTO;
import com.eis.hw.enums.OrderSide;
import com.eis.hw.enums.OrderType;
import com.eis.hw.form.InstrumentForm;
import com.eis.hw.model.entity.Orderitem;
import com.eis.hw.service.OrderbookService;
import com.eis.hw.service.ROrderbookService;
import com.eis.hw.util.ProtostuffUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by kaclarpt on 2019/5/29
 */
@Component
@Slf4j
public class OrderReceiver {
    private final ROrderbookService rOrderbookService;

    private final OrderitemRepository orderitemRepository;

    private final OrderbookService orderbookService;

    private final BrokerRepository brokerRepository;

    private final TraderRepository traderRepository;

    @Autowired
    public OrderReceiver(ROrderbookService rOrderbookService, OrderitemRepository orderitemRepository, OrderbookService orderbookService, BrokerRepository brokerRepository, TraderRepository traderRepository) {
        this.rOrderbookService = rOrderbookService;
        this.orderitemRepository = orderitemRepository;
        this.orderbookService = orderbookService;
        this.brokerRepository = brokerRepository;
        this.traderRepository = traderRepository;
    }

    @RabbitListener(queues = "trader")
    public void consumeOrder(byte[] data) throws Exception {
        OrderDTO orderDTO = ProtostuffUtils.deserialize(data, OrderDTO.class);
        log.info(orderDTO.toString());
        OrderType orderType = orderDTO.getOrderType();
        String bookId = String.valueOf(orderDTO.getBookId());
        OrderSide side = orderDTO.getOrderSide();
        int price = orderDTO.getPrice();
        int qty = orderDTO.getQty();
        Long trader_id = orderDTO.getTraderId();
        Long broker_id = orderDTO.getBrokerId();
        if (orderType == OrderType.MARKETORDER) {
            rOrderbookService.consumeMarket(bookId, side, qty, trader_id);
            rOrderbookService.checkStop(bookId);
        }
        else if (orderType == OrderType.LIMITORDER) {
            //check and consume order
            int consume = rOrderbookService.consumeLimit(bookId, side, price, qty, trader_id);
            System.out.println("consume:" + String.valueOf(consume));
            //if rest
            int rest = qty - consume;
            if (rest == 0) {
                return;
            }
            Orderitem orderitem = new Orderitem();
            orderitem.setBroker(brokerRepository.findById(broker_id).get());
            orderitem.setTrader(traderRepository.findById(trader_id).get());
            orderitem.setVol(rest);
            orderitem = orderitemRepository.save(orderitem);
            rOrderbookService.insertOrderitem(bookId, side, price, rest, orderitem);
            orderitemRepository.save(orderitem);
            rOrderbookService.checkStop(bookId);
        }
        else if (orderType == OrderType.STOPMARKETORDER) {
            if (rOrderbookService.consumeStop(bookId, side, price, qty, trader_id)) {
                return;
            }
            //else insert it
            Orderitem orderitem = new Orderitem();
            orderitem.setBroker(brokerRepository.findById(broker_id).get());
            orderitem.setTrader(traderRepository.findById(trader_id).get());
            orderitem.setVol(qty);
            orderitem = orderitemRepository.save(orderitem);
            rOrderbookService.insertStopOrderitem(bookId, side, price, qty, orderitem);
            orderitem = orderitemRepository.save(orderitem);
            rOrderbookService.checkStop(bookId);
        }
        else{
        }
        return;
    }
}
