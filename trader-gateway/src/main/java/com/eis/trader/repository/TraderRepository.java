package com.eis.trader.repository;

import com.eis.trader.domain.Trader;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by kaclarpt on 2019/6/8
 */
public interface TraderRepository extends CrudRepository<Trader, Long> {
}
