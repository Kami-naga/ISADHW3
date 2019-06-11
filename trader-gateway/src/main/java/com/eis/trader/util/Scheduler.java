package com.eis.trader.util;

import com.alibaba.fastjson.JSONObject;
import com.eis.trader.domain.Instrument;
import com.eis.trader.dto.OrderDTO;
import com.eis.trader.enums.OrderSide;
import com.eis.trader.enums.OrderType;
import com.eis.trader.repository.InstrumentRepository;
import com.eis.trader.service.OrderService;
import com.eis.trader.vo.OrderNodeVO;
import com.eis.trader.vo.OrderbookVO;
import com.eis.trader.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kaclarpt on 2019/6/7
 */
@Slf4j
@Component
public class Scheduler {
    @Autowired
    private OrderService orderService;


    @Autowired
    private InstrumentRepository instrumentRepository;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 10000)
    public void test() {
        System.out.println(dateFormat.format((new Date())));
    }

    @Scheduled(fixedRate = 2000)
    public void orderMock() {
        List<Instrument> instrumentList = instrumentRepository.findAll();
        int length = instrumentList.size();
        Random rand =new Random();
        OrderDTO orderDTO = new OrderDTO();
        if (WebSocketServer.getSid().equals(""))
            return;
        Instrument instrument = instrumentList.get(rand.nextInt(length));
        orderDTO.setBookId(WebSocketServer.getSid());
        orderDTO.setPrice(rand.nextInt(500)+500);
        orderDTO.setQty(rand.nextInt(500)+500);
        orderDTO.setInstrumentId(instrument.getInstrumentId());
        orderDTO.setTraderId(Long.parseLong(String.valueOf(1+rand.nextInt(6))));
        orderDTO.setBrokerId(instrument.getBroker().getBrokerId());
        orderDTO.setOrderSide(OrderSide.values()[rand.nextInt(2)]);
        orderDTO.setOrderType(OrderType.LIMITORDER);
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        orderDTO.setTimeSign(sdf.format(date));
        log.info(orderDTO.toString());
        orderService.transferOrder(ProtostuffUtils.serialize(orderDTO));
    }

//    @Scheduled(fixedRate = 5000)
//    public void socketTest() {
//        String orderBookId = "B1I1";
//        OrderbookVO orderbookVO = new OrderbookVO();
//        orderbookVO.setOrderbookId("B1I1");
//        Random rand =new Random();
//        List<OrderNodeVO> buysFive = new LinkedList<>();
//        List<OrderNodeVO> sellsFive = new LinkedList<>();
//        for (int i = 0; i < 5; i++) {
//            OrderNodeVO orderNodeVO = new OrderNodeVO();
//            orderNodeVO.setPrice(rand.nextInt(500)+500);
//            orderNodeVO.setVolumn(rand.nextInt(500)+500);
//            buysFive.add(orderNodeVO);
//            buysFive.sort(new Comparator<OrderNodeVO>() {
//                @Override
//                public int compare(OrderNodeVO o1, OrderNodeVO o2) {
//                    return o1.getPrice() - o2.getPrice();
//                }
//            });
//            orderNodeVO = new OrderNodeVO();
//            orderNodeVO.setPrice(rand.nextInt(500)+500);
//            orderNodeVO.setVolumn(rand.nextInt(500)+500);
//            sellsFive.add(orderNodeVO);
//            sellsFive.sort(new Comparator<OrderNodeVO>() {
//                @Override
//                public int compare(OrderNodeVO o1, OrderNodeVO o2) {
//                    return o1.getPrice() - o2.getPrice();
//                }
//            });
//        }
//        orderbookVO.setBuysFive(buysFive);
//        orderbookVO.setSellsFive(sellsFive);
//        try {
//            WebSocketServer.sendInfo(JSONObject.toJSONString(orderbookVO),orderBookId);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
