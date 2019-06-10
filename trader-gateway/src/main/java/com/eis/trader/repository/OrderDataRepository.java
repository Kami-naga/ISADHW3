package com.eis.trader.repository;

import com.eis.trader.domain.OrderData;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by kaclarpt on 2019/6/8
 */
public interface OrderDataRepository extends CrudRepository<OrderData, Long> {
}
