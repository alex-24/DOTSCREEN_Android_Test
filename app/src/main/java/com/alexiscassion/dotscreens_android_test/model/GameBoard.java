package com.alexiscassion.dotscreens_android_test.model;

import android.util.Log;

import java.util.Arrays;

public class GameBoard {


    private Player[][] gameBoard = new Player[3][3];

    public Player get(int i, int j){
        return this.gameBoard[i][j];
    }

    public Player set(int i, int j, Player p){
        return this.gameBoard[i][j] = p;
    }

    public void logDebugData(){
        Log.e("GameBoard", "V--------------------V");
        for (int i = 0; i < 3; i++) {
            Log.e("GameBoard", Arrays.toString(gameBoard[i]));
        }
    }
}
