package com.alexiscassion.dotscreens_android_test.utils;

/**
 * Listener for @{@link com.alexiscassion.dotscreens_android_test.model.GameBoard} changes.
 * In other words the changes to the Tic Tac Toe grid.
 */
public interface GameStateListener {

    void onGameBoardUpdated();
}
