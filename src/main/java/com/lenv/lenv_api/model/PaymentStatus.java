package com.lenv.lenv_api.model;

public enum PaymentStatus {
    IDLE,
    CHECKOUT,
    WALLET_CONNECT,
    AWAITING_SIGNATURE,
    PROCESSING,
    CONFIRMED,
    REJECTED,
    FAILED,
    EXPIRED,
    CANCELLED
}
