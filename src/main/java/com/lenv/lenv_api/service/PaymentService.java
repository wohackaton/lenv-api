package com.lenv.lenv_api.service;

import com.lenv.lenv_api.model.Order;
import com.lenv.lenv_api.model.PaymentStatus;
import com.lenv.lenv_api.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepo;
    private long counter = 0;

    // RF13: cria pedido (BACKEND controla pedido, valor e destinatário)
    public Map<String, Object> createOrder(String medicationName, String pharmacyName,
                                            Double price, Double distanceMeters, String walletKey) {
        counter++;
        Order order = Order.builder()
                .orderCode(String.format("LENV-%04d", counter))
                .medicationName(medicationName)
                .pharmacyName(pharmacyName)
                .price(price)
                .distanceMeters(distanceMeters)
                .status(PaymentStatus.CHECKOUT)
                .walletPublicKey(walletKey)
                .build();
        order = orderRepo.save(order);
        return toMap(order);
    }

    // RF14: atualiza status do pagamento
    public Map<String, Object> updateStatus(String orderCode, PaymentStatus status, String signature) {
        Order order = orderRepo.findByOrderCode(orderCode)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + orderCode));
        order.setStatus(status);
        if (signature != null) order.setTransactionSignature(signature);
        order.setUpdatedAt(LocalDateTime.now());
        order = orderRepo.save(order);
        return toMap(order);
    }

    public Map<String, Object> getStatus(String orderCode) {
        Order order = orderRepo.findByOrderCode(orderCode)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + orderCode));
        return toMap(order);
    }

    public Map<String, Object> confirm(String orderCode, String signature) {
        return updateStatus(orderCode, PaymentStatus.CONFIRMED, signature);
    }

    private Map<String, Object> toMap(Order o) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("orderCode", o.getOrderCode());
        m.put("medicationName", o.getMedicationName());
        m.put("pharmacyName", o.getPharmacyName());
        m.put("price", o.getPrice());
        m.put("distanceMeters", o.getDistanceMeters());
        m.put("status", o.getStatus().name());
        m.put("transactionSignature", o.getTransactionSignature());
        m.put("walletPublicKey", o.getWalletPublicKey());
        m.put("createdAt", o.getCreatedAt());
        m.put("updatedAt", o.getUpdatedAt());
        return m;
    }
}
