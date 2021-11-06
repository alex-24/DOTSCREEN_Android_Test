package com.alexiscassion.dotscreens_android_test.view_model;

import android.os.CountDownTimer;

import com.alexiscassion.dotscreens_android_test.model.Board;
import com.alexiscassion.dotscreens_android_test.model.Player;
import com.alexiscassion.dotscreens_android_test.utils.GameStateListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameViewModel extends ViewModel {

    private List<GameStateListener> gameStateListeners = new ArrayList<>();

    //private boolean gameIsOngoing = false;
    private Board board;
    private int[] scores = new int[2];
    private Player currentPlayer;
    private Player winner;
    private boolean gameOver = false;
    private MutableLiveData<String> formattedTimerLabel;
    private CountDownTimer timer;
    private boolean firstMove = true;

    public GameViewModel() {
        this.board = new Board();

        this.scores[0] = 0;
        this.scores[1] = 0;

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

    public Board getBoard() {
        return this.board;
    }

    public void resetGame(boolean startNewGame) {
        this.board = new Board();
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

        if (this.firstMove) {
            this.firstMove = false;
            this.timer.start();
        }

        // is cell empty?
        if (this.board.get(x, y) == null || this.gameOver) {
            return;
        }

        // adding a mark for the current player at (x, y)
        this.board.set(x, y, currentPlayer);

        // check if current player won
        calcWinner(x, y);

        // check if game ended in a draw
        calcGameOver();

        this.currentPlayer = (this.gameOver)? null : this.currentPlayer.next();
    }

    private void calcWinner(int x, int y) {
        // check if the current player has won
        this.winner = currentPlayer;
        for (int i = 0; i < 3; i++) {
            // * horizontal
            if (board.get(i, y) != currentPlayer) {
                this.winner = null;
                break;
            }

            // * vertical
            if (board.get(x, i) != currentPlayer) {
                this.winner = null;
                break;
            }
        }

        // diagonals
        if (x == y && this.winner == null)  {
            for (int i=0; i<3; i++) {
                if (this.board.get(i, i) != currentPlayer) {
                    this.winner = null;
                }
            }
            for (int i=0; i<3; i++) {
                if (this.board.get(2-i, i) != currentPlayer) {
                    this.winner = null;
                }
            }
        }
    }

    private void calcGameOver(){

        if (this.winner == null) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (this.board.get(i, j) == null) {
                        this.gameOver = false;
                        return;
                    }
                }
            }
        }
        this.gameOver = true;
        this.timer.cancel();
    }

    public void addGameStateListener(GameStateListener listener) {
        this.gameStateListeners.add(listener);
    }
}
