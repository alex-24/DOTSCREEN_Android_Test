package com.alexiscassion.dotscreens_android_test.model;

import android.graphics.Color;

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

    public int getColor() {
        switch (this) {
            case X:
                return Color.RED;

            case O:
                return Color.BLUE;

            default:
                return Color.WHITE;
        }
    }
}
