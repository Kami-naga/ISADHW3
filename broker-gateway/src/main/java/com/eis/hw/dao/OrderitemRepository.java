package com.eis.hw.dao;

import com.eis.hw.model.entity.Orderitem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderitemRepository extends JpaRepository<Orderitem,Long> {
}
