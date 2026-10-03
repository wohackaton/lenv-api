package com.lenv.lenv_api.controller;

import com.lenv.lenv_api.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/medications")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class MedicationController {

    private final MedicationService service;

    @GetMapping
    public List<Map<String, Object>> list() {
        return service.findAll();
    }

    @GetMapping("/search")
    public List<Map<String, Object>> search(@RequestParam String q) {
        return service.search(q);
    }

    @GetMapping("/{id}")
    public Map<String, Object> get(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/{id}/pharmacies")
    public List<Map<String, Object>> pharmacies(@PathVariable Long id) {
        return service.pharmaciesForMedication(id);
    }
}
