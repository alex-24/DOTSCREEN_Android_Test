package com.alexiscassion.dotscreens_android_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alexiscassion.dotscreens_android_test.model.Player;
import com.alexiscassion.dotscreens_android_test.utils.GameStateListener;
import com.alexiscassion.dotscreens_android_test.utils.GameBoardAdapter;
import com.alexiscassion.dotscreens_android_test.view_model.GameBoardViewModel;

public class MainActivity extends AppCompatActivity implements GameStateListener {


    private GameBoardViewModel gameBoardViewModel;

    private LinearLayout rootLinearLayout;

    private TextView timerTextView;

    private ImageView player1IndicatorImageView;
    private ImageView player2IndicatorImageView;

    private TextView player1ScoreTextView;
    private TextView player2ScoreTextView;

    private GridView gridView;

    public MainActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();



        if (savedInstanceState != null) {
            this.gameBoardViewModel = (GameBoardViewModel) savedInstanceState.getSerializable("gbvm");
        } else {
            this.gameBoardViewModel = new GameBoardViewModel();
        }

        linkActivityWithViewModel();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


        if (savedInstanceState != null) {
            this.gameBoardViewModel = (GameBoardViewModel) savedInstanceState.getSerializable("gbvm");
        } else {
            this.gameBoardViewModel = new GameBoardViewModel();
        }

        linkActivityWithViewModel();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("gbvm", this.gameBoardViewModel);
    }

    /**
     * Attaches this activity with the {@link GameBoardViewModel} and it's callbacks.
     */
    private void linkActivityWithViewModel() {

        // listen for board changes
        this.gameBoardViewModel.setGameBoardListener(this);

        // listen for timer changes
        this.gameBoardViewModel.getFormattedTimerLabel().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                timerTextView.setText(s);
            }
        });

        // listen for score changes
        this.gameBoardViewModel.getScores().observe(this, new Observer<int[]>() {
            @Override
            public void onChanged(int[] scores) {
                player1ScoreTextView.setText(String.valueOf(scores[0]));
                player2ScoreTextView.setText(String.valueOf(scores[1]));
            }
        });

        // listen for active player changes
        this.gameBoardViewModel.getCurrentPlayer().observe(this, new Observer<Player>() {
            @Override
            public void onChanged(Player player) {
                boolean isPlayer1Active = player == Player.ONE;
                boolean isPlayer2Active = player == Player.TWO;
                player1IndicatorImageView.setVisibility(isPlayer1Active? View.VISIBLE : View.INVISIBLE);
                player2IndicatorImageView.setVisibility(isPlayer2Active? View.VISIBLE : View.INVISIBLE);
            }
        });

        // listen for game over event
        this.gameBoardViewModel.getIsGameOver().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isGameOver) {
                if (isGameOver) {
                    announceGameResults();
                }
            }
        });

        this.gridView.setAdapter(new GameBoardAdapter(this, this.gameBoardViewModel));

    }

    @Override
    protected void onResume() {
        super.onResume();
        // adds FlexBox behavior on screen rotation
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.rootLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            this.rootLinearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            this.rootLinearLayout.setOrientation(LinearLayout.VERTICAL);
            this.rootLinearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
    }

    /**
     * Gets the views from the layout file
     */
    private void findViews(){
        this.rootLinearLayout = findViewById(R.id.activity_main_linear_layout_root);
        this.timerTextView = findViewById(R.id.activity_main_text_view_timer);
        this.player1IndicatorImageView = findViewById(R.id.activity_main_image_view_current_player_p1);
        this.player1ScoreTextView = findViewById(R.id.activity_main_text_view_player_1_score);
        this.player2IndicatorImageView = findViewById(R.id.activity_main_image_view_current_player_p2);
        this.player2ScoreTextView = findViewById(R.id.activity_main_text_view_player_2_score);
        this.gridView = findViewById(R.id.activity_main_grid_view);
    }

    /**
     * Redraws the board when it has been updated
     */
    @Override
    public void onGameBoardUpdated() {
        //((GameBoardAdapter) this.gridView.getAdapter()).notifyDataSetChanged();
        this.gridView.setAdapter(new GameBoardAdapter(this, this.gameBoardViewModel));
    }

    /**
     * Shows a popup announcing the results of the game
     */
    private void announceGameResults(){
        String message;
        int scoreP1 = gameBoardViewModel.getScores().getValue()[0];
        int scoreP2 = gameBoardViewModel.getScores().getValue()[1];
        String scoresString = scoreP1 + " - " + scoreP2;

        if (scoreP1 == scoreP2) {
            message = "Draw: " + scoresString;
        } else {
            int winnerIdx = (scoreP1 > scoreP2)? 0 : 1;
            //message = "Winner: Player " + Player.values()[winnerIdx].getSymbolText() + " " + scoresString;
            message = "Winner: Player " + (winnerIdx+1) + " " + scoresString;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("GAME OVER");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }
}