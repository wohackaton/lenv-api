package com.lenv.lenv_api.repository;

import com.lenv.lenv_api.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

    @Query("SELECT m FROM Medication m WHERE LOWER(m.nameCommercial) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(m.principleActive) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<Medication> search(String q);
}
