package com.lenv.lenv_api.controller;

import com.lenv.lenv_api.service.SolanaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/solana")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class SolanaController {

    private final SolanaService solana;

    @GetMapping("/health")
    public Map<String, Object> health() {
        return solana.getHealth();
    }

    @GetMapping("/slot")
    public Map<String, Object> slot() {
        return solana.getSlot();
    }

    @GetMapping("/balance/{publicKey}")
    public Map<String, Object> balance(@PathVariable String publicKey) {
        return solana.getBalance(publicKey);
    }

    @GetMapping("/blockhash")
    public Map<String, Object> blockhash() {
        return solana.getRecentBlockhash();
    }

    @PostMapping("/send")
    public Map<String, Object> send(@RequestBody Map<String, String> body) {
        return solana.sendTransaction(body.get("signedTransaction"));
    }

    @PostMapping("/airdrop")
    public Map<String, Object> airdrop(@RequestBody Map<String, Object> body) {
        String pk = (String) body.get("publicKey");
        long lamports = body.get("lamports") != null ? ((Number) body.get("lamports")).longValue() : 1_000_000_000L;
        return solana.requestAirdrop(pk, lamports);
    }
}
