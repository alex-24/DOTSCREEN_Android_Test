package com.alexiscassion.dotscreens_android_test.model;

import android.graphics.Color;

import com.alexiscassion.dotscreens_android_test.R;

public enum Player {

    ONE, TWO;

    public Player next() {
        switch (this) {
            case ONE:
                return TWO;

            case TWO:
            default:
                return ONE;
        }
    }

    public int getColor() {
        switch (this) {
            case ONE:
                return Color.RED;

            case TWO:
                return Color.BLUE;

            default:
                return Color.WHITE;
        }
    }

    public int getDrawableID() {
        switch (this) {
            case ONE:
                return R.drawable.x;

            case TWO:
                return R.drawable.o;

            default:
                return R.drawable.blank;
        }
    }

    public Character getSymbolText() {
        switch (this) {
            case ONE:
                return 'X';

            case TWO:
                return 'O';

            default:
                return null;
        }
    }

}
