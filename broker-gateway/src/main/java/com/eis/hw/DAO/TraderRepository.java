package com.eis.hw.DAO;

import com.eis.hw.Model.Entity.Trader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraderRepository extends JpaRepository<Trader,Integer> {
}
