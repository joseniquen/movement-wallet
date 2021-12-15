package com.example.walletmovementmicroservice.utils;

public enum MovementType {
    CARGO("CARGO"),
    ABONO("ABONO");

    public final String value;

    @Override
    public String toString() {
        return value;
    }

    public boolean equalsName(String otherValue) {
        return value.equals(otherValue);
    }

    private MovementType(String value) {
        this.value = value;
    }
}
