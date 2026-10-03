package com.lenv.lenv_api.service;

import com.lenv.lenv_api.model.*;
import com.lenv.lenv_api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository medRepo;
    private final PharmacyStockRepository stockRepo;

    public List<Map<String, Object>> search(String query) {
        List<Medication> meds = medRepo.search(query);
        return meds.stream().map(this::toMap).toList();
    }

    public List<Map<String, Object>> findAll() {
        return medRepo.findAll().stream().map(this::toMap).toList();
    }

    public Map<String, Object> findById(Long id) {
        Medication m = medRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado: " + id));
        return toMap(m);
    }

    public List<Map<String, Object>> pharmaciesForMedication(Long medId) {
        List<PharmacyStock> stocks = stockRepo.findByMedicationId(medId);
        return stocks.stream().map(s -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("pharmacyId", s.getPharmacy().getId());
            m.put("pharmacyName", s.getPharmacy().getName());
            m.put("address", s.getPharmacy().getAddress());
            m.put("latitude", s.getPharmacy().getLatitude());
            m.put("longitude", s.getPharmacy().getLongitude());
            m.put("price", s.getPrice());
            m.put("availability", s.getAvailability().getLabel());
            m.put("canReserve", s.getCanReserve());
            return m;
        }).toList();
    }

    private Map<String, Object> toMap(Medication m) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", m.getId());
        map.put("nameCommercial", m.getNameCommercial());
        map.put("principleActive", m.getPrincipleActive());
        map.put("concentration", m.getConcentration());
        map.put("pharmaceuticalForm", m.getPharmaceuticalForm());
        map.put("laboratory", m.getLaboratory());
        map.put("availability", m.getAvailability().getLabel());
        map.put("hasGeneric", m.getHasGeneric());
        if (Boolean.TRUE.equals(m.getHasGeneric())) {
            map.put("genericPrice", m.getGenericPrice());
            map.put("referencePrice", m.getReferencePrice());
        }
        return map;
    }
}
