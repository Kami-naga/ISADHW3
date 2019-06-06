package com.eis.hw.dao;

import com.eis.hw.model.entity.Broker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrokerRepository extends JpaRepository<Broker,Long> {
}
