package com.lenv.lenv_api.repository;

import com.lenv.lenv_api.model.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
}
