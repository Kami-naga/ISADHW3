package com.eis.hw.model.redisentity;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
public class ROrderbook implements Serializable {

    private static final Long SerialVersionUID = 1L;

    private String orderBookId;

    private List<String> buys = new LinkedList<>();

    private List<String> sells = new LinkedList<>();

    private List<String> stopBuys = new LinkedList<>();

    private List<String> stopSells = new LinkedList<>();

}