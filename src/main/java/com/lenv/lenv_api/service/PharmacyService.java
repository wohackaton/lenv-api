package com.lenv.lenv_api.service;

import com.lenv.lenv_api.model.Pharmacy;
import com.lenv.lenv_api.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PharmacyService {

    private final PharmacyRepository repo;

    public List<Map<String, Object>> findAll() {
        return repo.findAll().stream().map(this::toMap).toList();
    }

    public Map<String, Object> findById(Long id) {
        Pharmacy p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Farmácia não encontrada: " + id));
        return toMap(p);
    }

    public List<Map<String, Object>> nearby(double lat, double lon, double maxKm) {
        return repo.findAll().stream()
                .map(p -> {
                    double dist = haversine(lat, lon, p.getLatitude(), p.getLongitude());
                    Map<String, Object> m = toMap(p);
                    m.put("distanceMeters", Math.round(dist * 1000));
                    return Map.entry(dist, m);
                })
                .filter(e -> e.getKey() <= maxKm)
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .toList();
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    private Map<String, Object> toMap(Pharmacy p) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id", p.getId());
        m.put("name", p.getName());
        m.put("tradeName", p.getTradeName());
        m.put("address", p.getAddress());
        m.put("neighborhood", p.getNeighborhood());
        m.put("city", p.getCity());
        m.put("latitude", p.getLatitude());
        m.put("longitude", p.getLongitude());
        return m;
    }
}
