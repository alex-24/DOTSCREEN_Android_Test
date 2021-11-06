package com.alexiscassion.dotscreens_android_test.model;

public class GameBoard {


    private Player[][] gameState = new Player[3][3];

    public Player get(int i, int j){
        return this.gameState[i][j];
    }

    public Player set(int i, int j, Player p){
        return this.gameState[i][j] = p;
    }
}
