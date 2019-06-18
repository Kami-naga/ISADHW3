package com.eis.hw.dao;

import com.eis.hw.model.entity.Orderitem;
import com.eis.hw.model.entity.Trader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderitemRepository extends JpaRepository<Orderitem,Long> {
    public List<Orderitem> findByTrader(Trader trader);
}
