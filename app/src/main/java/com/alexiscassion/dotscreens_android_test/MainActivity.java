package com.alexiscassion.dotscreens_android_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import com.alexiscassion.dotscreens_android_test.utils.GameStateListener;
import com.alexiscassion.dotscreens_android_test.utils.GameBoardAdapter;
import com.alexiscassion.dotscreens_android_test.view_model.GameBoardViewModel;

public class MainActivity extends AppCompatActivity implements GameStateListener {


    private GameBoardViewModel gameBoardViewModel;

    private GridView gridView;
    private TextView timerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.gameBoardViewModel = new GameBoardViewModel();
        this.gameBoardViewModel.addGameBoardListener(this);
        this.gameBoardViewModel.getFormattedTimerLabel().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                timerTextView.setText(s);
            }
        });

        findViews();

        this.gridView.setAdapter(new GameBoardAdapter(this, this.gameBoardViewModel));


    }

    private void findViews(){
        this.gridView = findViewById(R.id.activity_main_grid_view);
        this.timerTextView = findViewById(R.id.activity_main_text_view_timer);
    }

    @Override
    public void onGameBoardUpdated() {
        ((GameBoardAdapter) this.gridView.getAdapter()).notifyDataSetChanged();
        //this.gridView.setAdapter(new GameBoardAdapter(this, this.gameBoardViewModel));
    }
}