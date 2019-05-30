package com.eis.trader;

import com.eis.trader.rabbitmq.StreamClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TraderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TraderApplication.class, args);
    }

}
