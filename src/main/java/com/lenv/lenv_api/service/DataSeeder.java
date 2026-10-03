package com.lenv.lenv_api.service;

import com.lenv.lenv_api.model.*;
import com.lenv.lenv_api.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final MedicationRepository medRepo;
    private final PharmacyRepository pharmRepo;
    private final PharmacyStockRepository stockRepo;

    public DataSeeder(MedicationRepository medRepo, PharmacyRepository pharmRepo, PharmacyStockRepository stockRepo) {
        this.medRepo = medRepo;
        this.pharmRepo = pharmRepo;
        this.stockRepo = stockRepo;
    }

    @Override
    public void run(String... args) {
        if (medRepo.count() > 0) return;

        // ── Medicamentos ──
        List<Medication> meds = List.of(
            Medication.builder().nameCommercial("Novalgina 500mg").principleActive("Dipirona monoidratada")
                .concentration("500 mg").pharmaceuticalForm("Comprimido").laboratory("Sanofi")
                .hasGeneric(true).genericPrice(8.90).referencePrice(22.50)
                .availability(Availability.AVAILABLE).build(),
            Medication.builder().nameCommercial("Cozaar 50mg").principleActive("Losartana potássica")
                .concentration("50 mg").pharmaceuticalForm("Comprimido").laboratory("MSD")
                .hasGeneric(true).genericPrice(12.90).referencePrice(35.00)
                .availability(Availability.LIMITED).build(),
            Medication.builder().nameCommercial("Losec 20mg").principleActive("Omeprazol")
                .concentration("20 mg").pharmaceuticalForm("Cápsula").laboratory("AstraZeneca")
                .hasGeneric(true).genericPrice(7.50).referencePrice(19.90)
                .availability(Availability.AVAILABLE).build(),
            Medication.builder().nameCommercial("Amoxil 500mg").principleActive("Amoxicilina")
                .concentration("500 mg").pharmaceuticalForm("Cápsula").laboratory("GSK")
                .hasGeneric(true).genericPrice(15.40).referencePrice(28.90)
                .availability(Availability.AVAILABLE).build(),
            Medication.builder().nameCommercial("Diovan 160mg").principleActive("Valsartana")
                .concentration("160 mg").pharmaceuticalForm("Comprimido").laboratory("Novartis")
                .hasGeneric(true).genericPrice(22.00).referencePrice(48.90)
                .availability(Availability.UNAVAILABLE).build(),
            Medication.builder().nameCommercial("Puran 40mcg").principleActive("Levotiroxina sódica")
                .concentration("40 mcg").pharmaceuticalForm("Comprimido").laboratory("Sanofi")
                .hasGeneric(true).genericPrice(18.90).referencePrice(32.90)
                .availability(Availability.AVAILABLE).build()
        );
        medRepo.saveAll(meds);

        // ── Farmácias (São Paulo centro) ──
        List<Pharmacy> pharmacies = List.of(
            Pharmacy.builder().name("Drogaria São Paulo").tradeName("DSP")
                .address("Av. Paulista, 1000").neighborhood("Bela Vista").city("São Paulo").state("SP")
                .latitude(-23.5614).longitude(-46.6559).build(),
            Pharmacy.builder().name("Pague Menos").tradeName("Pague Menos")
                .address("Rua Augusta, 500").neighborhood("Consolação").city("São Paulo").state("SP")
                .latitude(-23.5559).longitude(-46.6623).build(),
            Pharmacy.builder().name("Drogasil").tradeName("Drogasil")
                .address("Rua Oscar Freire, 200").neighborhood("Jardins").city("São Paulo").state("SP")
                .latitude(-23.5618).longitude(-46.6747).build(),
            Pharmacy.builder().name("Drogaria Panvel").tradeName("Panvel")
                .address("Rua da Consolação, 1500").neighborhood("Consolação").city("São Paulo").state("SP")
                .latitude(-23.5489).longitude(-46.6577).build(),
            Pharmacy.builder().name("Drogaria Pacheco").tradeName("Pacheco")
                .address("Rua Haddock Lobo, 300").neighborhood("Cerqueira César").city("São Paulo").state("SP")
                .latitude(-23.5548).longitude(-46.6630).build()
        );
        pharmRepo.saveAll(pharmacies);

        // ── Estoque: cada medicamento em 2-3 farmácias ──
        for (int mi = 0; mi < meds.size(); mi++) {
            Medication med = meds.get(mi);
            int[] pharmacyIdx = {mi % 5, (mi + 1) % 5, (mi + 2) % 5};
            Availability[] avails = {Availability.AVAILABLE, Availability.LIMITED, Availability.UNAVAILABLE};
            double basePrice = med.getReferencePrice();

            for (int pi = 0; pi < pharmacyIdx.length; pi++) {
                Pharmacy ph = pharmacies.get(pharmacyIdx[pi]);
                double price = basePrice + (mi * 0.5) + (pi * 1.2);
                stockRepo.save(PharmacyStock.builder()
                        .medication(med)
                        .pharmacy(ph)
                        .availability(avails[pi])
                        .price(Math.round(price * 100.0) / 100.0)
                        .quantity(pi == 0 ? 25 : pi == 1 ? 5 : 0)
                        .canReserve(pi < 2)
                        .build());
            }
        }

        System.out.println("✅ LENV DataSeeder: " + medRepo.count() + " medicamentos, "
                + pharmRepo.count() + " farmácias, " + stockRepo.count() + " registros de estoque");
    }
}
