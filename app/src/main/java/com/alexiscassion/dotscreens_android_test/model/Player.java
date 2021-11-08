package com.alexiscassion.dotscreens_android_test.model;

import android.graphics.Color;

import com.alexiscassion.dotscreens_android_test.R;

/**
 * Represents a Tic Tac Toe player
 */
public enum Player {

    ONE, TWO;

    /**
     *
     * @return The player whose turn it should be next
     */
    public Player next() {
        switch (this) {
            case ONE:
                return TWO;

            case TWO:
            default:
                return ONE;
        }
    }

    /**
     *
     * @return A color associated with the player
     */
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

    /**
     *
     * @return An image representing the player
     */
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

    /**
     *
     * @return The player represented as a character
     */
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
