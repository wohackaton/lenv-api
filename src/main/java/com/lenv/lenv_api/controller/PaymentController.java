package com.lenv.lenv_api.controller;

import com.lenv.lenv_api.model.PaymentStatus;
import com.lenv.lenv_api.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class PaymentController {

    private final PaymentService service;

    // RF13: criar pedido (checkout)
    @PostMapping("/orders")
    public Map<String, Object> createOrder(@RequestBody Map<String, Object> body) {
        return service.createOrder(
                (String) body.get("medicationName"),
                (String) body.get("pharmacyName"),
                ((Number) body.get("price")).doubleValue(),
                body.get("distanceMeters") != null ? ((Number) body.get("distanceMeters")).doubleValue() : null,
                (String) body.get("walletPublicKey")
        );
    }

    // RF14: atualizar status (idle→checkout→wallet→signature→processing→confirmed)
    @PutMapping("/orders/{orderCode}/status")
    public Map<String, Object> updateStatus(@PathVariable String orderCode,
                                            @RequestBody Map<String, Object> body) {
        PaymentStatus status = PaymentStatus.valueOf((String) body.get("status"));
        String sig = (String) body.getOrDefault("transactionSignature", null);
        return service.updateStatus(orderCode, status, sig);
    }

    // RF15: confirmar pagamento
    @PostMapping("/orders/{orderCode}/confirm")
    public Map<String, Object> confirm(@PathVariable String orderCode,
                                       @RequestBody Map<String, Object> body) {
        return service.confirm(orderCode, (String) body.get("transactionSignature"));
    }

    // Consultar status
    @GetMapping("/orders/{orderCode}")
    public Map<String, Object> status(@PathVariable String orderCode) {
        return service.getStatus(orderCode);
    }
}
