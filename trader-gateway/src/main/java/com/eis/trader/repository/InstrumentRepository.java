package com.eis.trader.repository;

import com.eis.trader.domain.Broker;
import com.eis.trader.domain.Instrument;
import com.eis.trader.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by kaclarpt on 2019/6/9
 */
public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
    List<Instrument> findAllByProduct(Product product);

    List<Instrument> findAllByBroker(Broker broker);

    List<Instrument> findAllByProductAndBroker(Product product, Broker broker);
}
