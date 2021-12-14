package com.bootcamp.movementwallet.utils;

public enum MovementType {
    MOVEMENT_ENVIO("E") {
        @Override
        public boolean equals(String movementType) {
            return value.equals(movementType);
        }
    },
    MOVEMENT_RECIBIR("R") {
        @Override
        public boolean equals(String movementType) {
            return value.equals(movementType);
        }
    };

    public final String value;

    public boolean equals(String movementType) {
        return value.equals(movementType);
    }

    private MovementType(String value) {
        this.value = value;
    }
}
