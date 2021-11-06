package com.alexiscassion.dotscreens_android_test.model;

import android.graphics.Color;

import com.alexiscassion.dotscreens_android_test.R;

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

    public int getDrawableID() {
        switch (this) {
            case X:
                return R.drawable.x;

            case O:
                return R.drawable.o;
        }

        return -1;
    }

}
