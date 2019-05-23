package com.eis.hw.DAO;

import com.eis.hw.Model.Entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade,Integer> {
}
