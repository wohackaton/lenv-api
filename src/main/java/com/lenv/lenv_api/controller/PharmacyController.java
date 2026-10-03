package com.lenv.lenv_api.controller;

import com.lenv.lenv_api.service.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/pharmacies")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class PharmacyController {

    private final PharmacyService service;

    @GetMapping
    public List<Map<String, Object>> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Map<String, Object> get(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/nearby")
    public List<Map<String, Object>> nearby(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "5.0") double maxKm) {
        return service.nearby(lat, lon, maxKm);
    }
}
