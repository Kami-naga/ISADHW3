package com.eis.hw.controller;

import com.eis.hw.enums.OrderSide;
import com.eis.hw.model.entity.Instrument;
import com.eis.hw.model.entity.Trade;
import com.eis.hw.model.entity.Trader;
import com.eis.hw.service.TradeService;
import com.eis.hw.vo.TradeVO;
import com.eis.hw.vo.TraderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaclarpt on 2019/6/10
 */
@RestController
@RequestMapping("/api/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;


    @GetMapping("/all")
    @ResponseBody
    public List<TradeVO> getAllTrades() {
        List<Trade> trades = tradeService.getAllTrades();
        List<TradeVO> tradeVOList = new ArrayList<>();
        for (Trade trade: trades) {
            TradeVO tradeVO = new TradeVO();
            Instrument instrument = trade.getInstrument();
            tradeVO.setBroker(instrument.getBroker().getName());
            tradeVO.setProduct(instrument.getProduct().getName());
            tradeVO.setTradeId(trade.getTradeId());
            Trader trader1 = trade.getInitiator();
            TraderVO traderVO1 = new TraderVO();
            BeanUtils.copyProperties(trader1, traderVO1);
            traderVO1.setId(trader1.getTraderId());
            traderVO1.setOrderSide(trade.getInitiatorBuy().equals("b")? OrderSide.BUY:OrderSide.SELL);
            Trader trader2 = trade.getInitiator();
            TraderVO traderVO2 = new TraderVO();
            BeanUtils.copyProperties(trader2, traderVO2);
            traderVO2.setId(trader1.getTraderId());
            traderVO2.setOrderSide(trade.getInitiatorBuy().equals("s")? OrderSide.BUY:OrderSide.SELL);
            tradeVO.setInitiator(traderVO1);
            tradeVO.setCompletion(traderVO2);
            tradeVO.setPeriodT(trade.getInstrument().getPeriodT());
            tradeVO.setPrice(trade.getPrice());
            tradeVO.setQty(trade.getQty());
            tradeVOList.add(tradeVO);
        }
        return tradeVOList;
    }

}
