package com.eis.hw.dao;

import com.eis.hw.model.entity.Trader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraderRepository extends JpaRepository<Trader,Long> {
}
