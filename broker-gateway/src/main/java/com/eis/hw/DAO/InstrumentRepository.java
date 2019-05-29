package com.eis.hw.DAO;

import com.eis.hw.Model.Entity.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentRepository extends JpaRepository<Instrument,Integer> {
}
