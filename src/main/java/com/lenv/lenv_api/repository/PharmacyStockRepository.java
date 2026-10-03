package com.lenv.lenv_api.repository;

import com.lenv.lenv_api.model.PharmacyStock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PharmacyStockRepository extends JpaRepository<PharmacyStock, Long> {
    List<PharmacyStock> findByMedicationId(Long medicationId);
}
