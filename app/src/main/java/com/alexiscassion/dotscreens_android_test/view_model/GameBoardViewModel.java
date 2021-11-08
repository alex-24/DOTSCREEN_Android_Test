package com.alexiscassion.dotscreens_android_test.view_model;

import android.os.CountDownTimer;

import com.alexiscassion.dotscreens_android_test.model.GameBoard;
import com.alexiscassion.dotscreens_android_test.model.Player;
import com.alexiscassion.dotscreens_android_test.utils.GameStateListener;

import java.io.Serializable;
import java.util.Locale;
import java.util.Random;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Holds the Tic Tac Toe game logic
 */
public class GameBoardViewModel extends ViewModel implements Serializable {

    private GameStateListener gameStateListener;
    private final Random random = new Random();

    //private boolean gameIsOngoing = false;
    private GameBoard gameBoard;
    private final MutableLiveData<int[]> scores;
    private final MutableLiveData<Player> currentPlayer;
    private final MutableLiveData<Boolean> isGameOver;
    private final MutableLiveData<String> formattedTimerLabel;
    private final CountDownTimer timer;
    private boolean firstMove = true;
    private boolean isSetOver = false;

    public GameBoardViewModel() {
        this.gameBoard = new GameBoard();

        this.currentPlayer = new MutableLiveData<>();

        this.scores = new MutableLiveData<>();
        this.scores.setValue(new int[]{0 , 0});

        this.formattedTimerLabel = new MutableLiveData<>();
        this.formattedTimerLabel.setValue("03:00");

        this.isGameOver = new MutableLiveData<>();
        this.isGameOver.setValue(false);

        this.timer = new CountDownTimer(180000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) ((millisUntilFinished / (1000*60)) % 60);
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                formattedTimerLabel.setValue(String.format(Locale.FRANCE, "%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                isGameOver.setValue(true);
            }
        };
    }

    /**
     * Clears the board.
     */
    public void clearBoard() {
        this.isSetOver = false;
        this.gameBoard = new GameBoard();
        notifyGameBoardListener();
    }

    /**
     * Checks if the cell is empty, if not a mark is placed for the current player at this cell position.
     * If a move was valid the function tests to see if the game is over (player won OR draw).
     * If the game is not over the turn is then given to the next player.
     *
     * At the end after all the changes to the game state all observers are notified so the view can be updated.
     *
     * @param x the horizontal coordinate
     * @param y the vertical coordinate
     */
    public void insertMarkAt(int x, int y) {

        //Log.e("--------------", "(" + x + ", " + y +")");

        if (this.firstMove) {
            this.firstMove = false;
            this.currentPlayer.setValue(Player.ONE);
            this.timer.start();
        }

        // is cell empty?
        if (this.gameBoard.get(x, y) != null || (this.isGameOver.getValue())) {
            return;
        }

        // adding a mark for the current player at (x, y)
        this.gameBoard.set(x, y, currentPlayer.getValue());

        // check if current player won
        calcPlayerWon(x, y);
        if (this.isSetOver) {
            this.currentPlayer.setValue((this.isGameOver.getValue())? null : this.currentPlayer.getValue().next());
            clearBoard();
            return;

        }

        // check if game ended in a draw
        calcDraw();
        if (this.isSetOver) {
            this.currentPlayer.setValue((this.isGameOver.getValue())? null : this.currentPlayer.getValue().next());
            clearBoard();
            return;
        }


        this.currentPlayer.setValue((this.isGameOver.getValue())? null : this.currentPlayer.getValue().next());
        notifyGameBoardListener();
    }

    /**
     * Tests if the current player has won the set.
     *
     * @param x the horizontal plsition where they played.
     * @param y the vertical position where they played.
     */
    private void calcPlayerWon(int x, int y) {
        boolean hasWon;


        // * horizontal
        hasWon = true;
        for (int i = 0; i < 3; i++) {
            if (this.gameBoard.get(i, y) != this.currentPlayer.getValue()) {
                hasWon = false;
                break;
            }
        }
        if (hasWon){
            setCurrentPlayerHasWon();
            return;
        }



        // * vertical
        hasWon = true;
        for (int i = 0; i < 3; i++) {
            if (this.gameBoard.get(x, i) != this.currentPlayer.getValue()) {
                hasWon = false;
                break;
            }
        }
        if (hasWon){
            setCurrentPlayerHasWon();
            return;
        }

        // diagonals
        if (x == y)  {
            hasWon = true;
            for (int i=0; i<3; i++) {
                if (this.gameBoard.get(i, i) != this.currentPlayer.getValue()) {
                    hasWon = false;
                }
            }
            if (hasWon){
                setCurrentPlayerHasWon();
                return;
            }

            hasWon = true;
            for (int i=0; i<3; i++) {
                if (this.gameBoard.get(2-i, i) != this.currentPlayer.getValue()) {
                    hasWon = false;
                }
            }
            if (hasWon){
                setCurrentPlayerHasWon();
            }
        }
    }

    /**
     * Updates the scores and ends the set
     */
    private void setCurrentPlayerHasWon() {
        int[] scores = this.scores.getValue();
        scores[this.currentPlayer.getValue().ordinal()]++;
        this.scores.setValue(scores);
        this.isSetOver = true;
    }

    /**
     * Tests if the set ended in a draw and ends the set if that's the case.
     */
    private void calcDraw(){

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.gameBoard.get(i, j) == null) {
                    return;
                }
            }
        }
        this.isSetOver = true;
    }

    /**
     * Sets a listener that is notified when the grd has been updated.
     *
     * @param listener the listener
     */
    public void setGameBoardListener(GameStateListener listener) {
        this.gameStateListener = listener;
    }

    /**
     *
     * @return the GameBoard
     */
    public GameBoard getGameBoard() {
        return this.gameBoard;
    }

    /**
     *
     * @return the current player
     */
    public MutableLiveData<Player> getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     *
     * @return isGameOver (the timer reached 00:00)
     */
    public MutableLiveData<Boolean> getIsGameOver() {
        return isGameOver;
    }

    /**
     *
     * @return A string representation of the remaining time
     */
    public MutableLiveData<String> getFormattedTimerLabel() {
        return formattedTimerLabel;
    }

    /**
     *
     * @return the score of the game
     */
    public MutableLiveData<int[]> getScores() {
        return scores;
    }

    /**
     * Notifies the listener that the GameBoard has been updated
     */
    public void notifyGameBoardListener() {
        this.gameStateListener.onGameBoardUpdated();
    }
}
