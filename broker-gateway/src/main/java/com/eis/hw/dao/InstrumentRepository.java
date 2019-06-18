package com.eis.hw.dao;

import com.eis.hw.model.entity.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentRepository extends JpaRepository<Instrument,Long> {
}
