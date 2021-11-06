package com.alexiscassion.dotscreens_android_test.model;

public enum Player {

    X, O;

    public Player next() {
        switch (this) {
            case X:
                return O;

            case O:
            default:
                return X;
        }
    }
}
