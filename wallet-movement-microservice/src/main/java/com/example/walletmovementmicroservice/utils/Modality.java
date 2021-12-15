package com.example.walletmovementmicroservice.utils;

public enum Modality {
    // VENTANILLA
    VENTANILLA("VENTANILLA"),
    // POS
    POS("POS"),
    // CAJERO-AUTOMATICO
    CAJERO("CAJERO"),
    // EFECTIVO-MOVIL
    EFECTIVO_MOVIL("EFECTIVO-MOVIL"),
    //BANCA-MOVIL
    BANCA_MOVIL("BANCA-MOVIL");

    public final String value;

    @Override
    public String toString() {
        return value;
    }

    public boolean equalsName(String otherValue) {
        return value.equals(otherValue);
    }

    private Modality(String value) {
        this.value = value;
    }
}
