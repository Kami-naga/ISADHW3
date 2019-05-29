package com.eis.hw.DAO;

import com.eis.hw.Model.Entity.Orderitem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderitemRepository extends JpaRepository<Orderitem,Integer> {
}
