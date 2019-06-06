package com.eis.trader.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kaclarpt on 2019/6/5
 */

@Data
public class ROrderbook implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    private String orderBookId;

    private List<String> buys = new LinkedList<>();

    private List<String> sells = new LinkedList<>();

    private List<String> stopBuys = new LinkedList<>();

    private List<String> stopSells = new LinkedList<>();

}
