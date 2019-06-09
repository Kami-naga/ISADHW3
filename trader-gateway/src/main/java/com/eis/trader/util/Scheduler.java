package com.eis.trader.util;

import com.alibaba.fastjson.JSONObject;
import com.eis.trader.vo.OrderNodeVO;
import com.eis.trader.vo.OrderbookVO;
import com.eis.trader.websocket.WebSocketServer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kaclarpt on 2019/6/7
 */
@Component
public class Scheduler {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 10000)
    public void test() {
        System.out.println(dateFormat.format((new Date())));
    }

    @Scheduled(fixedRate = 5000)
    public void socketTest() {
        String orderBookId = "B1I1";
        OrderbookVO orderbookVO = new OrderbookVO();
        orderbookVO.setOrderbookId("B1I1");
        Random rand =new Random();
        List<OrderNodeVO> buysFive = new LinkedList<>();
        List<OrderNodeVO> sellsFive = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            OrderNodeVO orderNodeVO = new OrderNodeVO();
            orderNodeVO.setPrice(rand.nextInt(500)+500);
            orderNodeVO.setVolumn(rand.nextInt(500)+500);
            buysFive.add(orderNodeVO);
            buysFive.sort(new Comparator<OrderNodeVO>() {
                @Override
                public int compare(OrderNodeVO o1, OrderNodeVO o2) {
                    return o1.getPrice() - o2.getPrice();
                }
            });
            orderNodeVO = new OrderNodeVO();
            orderNodeVO.setPrice(rand.nextInt(500)+500);
            orderNodeVO.setVolumn(rand.nextInt(500)+500);
            sellsFive.add(orderNodeVO);
            sellsFive.sort(new Comparator<OrderNodeVO>() {
                @Override
                public int compare(OrderNodeVO o1, OrderNodeVO o2) {
                    return o1.getPrice() - o2.getPrice();
                }
            });
        }
        orderbookVO.setBuysFive(buysFive);
        orderbookVO.setSellsFive(sellsFive);
        try {
            WebSocketServer.sendInfo(JSONObject.toJSONString(orderbookVO),orderBookId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
