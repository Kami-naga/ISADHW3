package com.eis.trader.repository;

import com.eis.trader.domain.Broker;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by kaclarpt on 2019/6/10
 */
public interface BrokerRepository extends JpaRepository<Broker, Long> {

}
