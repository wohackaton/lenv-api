package com.lenv.lenv_api.model;

public enum Availability {
    AVAILABLE("Disponível"),
    LIMITED("Estoque limitado"),
    UNAVAILABLE("Indisponível");

    private final String label;

    Availability(String label) { this.label = label; }
    public String getLabel() { return label; }
}
