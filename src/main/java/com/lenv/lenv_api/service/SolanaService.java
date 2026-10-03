package com.lenv.lenv_api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class SolanaService {

    @Value("${lenv.solana.rpc-url:https://api.devnet.solana.com}")
    private String rpcUrl;

    private final RestTemplate rest = new RestTemplate();

    // Verifica saúde do cluster Devnet
    public Map<String, Object> getHealth() {
        return rpc("getHealth", List.of());
    }

    // Slot atual
    public Map<String, Object> getSlot() {
        return rpc("getSlot", List.of());
    }

    // Saldo de uma carteira
    public Map<String, Object> getBalance(String publicKey) {
        return rpc("getBalance", List.of(publicKey));
    }

    // Blockhash recente (getLatestBlockhash substitui getRecentBlockhash)
    public Map<String, Object> getRecentBlockhash() {
        return rpc("getLatestBlockhash", List.of());
    }

    // Enviar transação assinada (base64)
    public Map<String, Object> sendTransaction(String signedTxBase64) {
        Map<String, Object> opts = Map.of("encoding", "base64");
        return rpc("sendTransaction", List.of(signedTxBase64, opts));
    }

    // Status de assinatura (confirmação)
    public Map<String, Object> getSignatureStatus(String signature) {
        return rpc("getSignatureStatuses", List.of(List.of(signature)));
    }

    // Airdrop (faucet devnet)
    public Map<String, Object> requestAirdrop(String publicKey, long lamports) {
        return rpc("requestAirdrop", List.of(publicKey, lamports));
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> rpc(String method, Object params) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("jsonrpc", "2.0");
        body.put("id", 1);
        body.put("method", method);
        body.put("params", params);
        try {
            return rest.postForObject(rpcUrl, body, Map.class);
        } catch (Exception e) {
            Map<String, Object> err = new LinkedHashMap<>();
            err.put("error", e.getMessage());
            return err;
        }
    }
}
