package com.alexiscassion.dotscreens_android_test.view_model;

import android.os.CountDownTimer;
import android.util.Log;

import com.alexiscassion.dotscreens_android_test.model.GameBoard;
import com.alexiscassion.dotscreens_android_test.model.Player;
import com.alexiscassion.dotscreens_android_test.utils.GameStateListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameBoardViewModel extends ViewModel {

    private final List<GameStateListener> gameStateListeners = new ArrayList<>();
    private final Random random = new Random();

    //private boolean gameIsOngoing = false;
    private GameBoard gameBoard;
    private final int[] scores = new int[2];
    private Player currentPlayer;
    private Player winner;
    private boolean isGameOver = false;
    private MutableLiveData<String> formattedTimerLabel;
    private CountDownTimer timer;
    private boolean firstMove = true;

    public GameBoardViewModel() {
        this.gameBoard = new GameBoard();

        this.scores[0] = 0;
        this.scores[1] = 0;
        this.formattedTimerLabel = new MutableLiveData<>();
        this.formattedTimerLabel.setValue("03:00");

        this.timer = new CountDownTimer(180000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) ((millisUntilFinished / (1000*60)) % 60);
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                formattedTimerLabel.setValue(String.format(Locale.FRANCE, "%2d:%2d", minutes, seconds));
            }

            @Override
            public void onFinish() {

            }
        };
    }

    public GameBoard getGameBoard() {
        return this.gameBoard;
    }

    public void resetGame(boolean startNewGame) {
        this.gameBoard = new GameBoard();
        this.winner = null;

        if (startNewGame) {
            this.currentPlayer = Player.X;
        } else {
            this.currentPlayer = null;
        }
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
            this.currentPlayer = Player.values()[this.random.nextInt(2)];
            this.timer.start();
        }

        // is cell empty?
        if (this.gameBoard.get(x, y) != null || this.isGameOver) {
            return;
        }

        // adding a mark for the current player at (x, y)
        this.gameBoard.set(x, y, currentPlayer);

        // check if current player won
        hasPlayerWon(x, y);
        Log.e("---", "" + this.winner);

        // check if game ended in a draw
        calcGameOver();

        this.currentPlayer = (this.isGameOver)? null : this.currentPlayer.next();
        notifyBoardListeners();
    }

    private void hasPlayerWon(int x, int y) {
        boolean hasWon;


        // * horizontal
        hasWon = true;
        for (int i = 0; i < 3; i++) {
            if (this.gameBoard.get(i, y) != this.currentPlayer) {
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
            if (this.gameBoard.get(x, i) != this.currentPlayer) {
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
                if (this.gameBoard.get(i, i) != this.currentPlayer) {
                    hasWon = false;
                }
            }
            if (hasWon){
                setCurrentPlayerHasWon();
                return;
            }

            hasWon = true;
            for (int i=0; i<3; i++) {
                if (this.gameBoard.get(2-i, i) != this.currentPlayer) {
                    hasWon = false;
                }
            }
            if (hasWon){
                setCurrentPlayerHasWon();
            }
        }
    }

    private void setCurrentPlayerHasWon() {
        this.winner = this.currentPlayer;
    }

    private void calcGameOver(){

        if (this.winner == null) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (this.gameBoard.get(i, j) == null) {
                        this.isGameOver = false;
                        return;
                    }
                }
            }
        }
        this.isGameOver = true;
        this.timer.cancel();
    }

    public void addGameBoardListener(GameStateListener listener) {
        this.gameStateListeners.add(listener);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public MutableLiveData<String> getFormattedTimerLabel() {
        return formattedTimerLabel;
    }

    public void notifyBoardListeners() {
        for (GameStateListener listener : this.gameStateListeners) {
            listener.onGameBoardUpdated();
        }
    }
}
