package com.alexiscassion.dotscreens_android_test.model;

import android.util.Log;

import java.util.Arrays;

/**
 * Represents a Tic Tac Toe grid
 */
public class GameBoard {


    private final Player[][] gameBoard = new Player[3][3];

    /**
     * Returns the player who placed a mark at (i, j) or null
     * @param i the horizontal position
     * @param j the vertical position
     * @return the Player at location (i, j) or null
     */
    public Player get(int i, int j){
        return this.gameBoard[i][j];
    }

    /**
     * Places a mark at location (i, j) for the given player
     * @param i the horizontal position
     * @param j the vertical position
     * @param p the player
     */
    public void set(int i, int j, Player p){
        this.gameBoard[i][j] = p;
    }

    /**
     * Prints the board state for debugging
     */
    public void logDebugData(){
        Log.e("GameBoard", "V--------------------V");
        for (int i = 0; i < 3; i++) {
            Log.e("GameBoard", Arrays.toString(gameBoard[i]));
        }
    }
}
