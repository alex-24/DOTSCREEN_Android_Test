package com.alexiscassion.dotscreens_android_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.alexiscassion.dotscreens_android_test.utils.GameStateListener;
import com.alexiscassion.dotscreens_android_test.utils.GameBoardAdapter;
import com.alexiscassion.dotscreens_android_test.view_model.GameBoardViewModel;

public class MainActivity extends AppCompatActivity implements GameStateListener {


    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GameBoardViewModel gameBoardViewModel = new GameBoardViewModel();
        gameBoardViewModel.addGameBoardListener(this);

        findViews();

        this.gridView.setAdapter(new GameBoardAdapter(this, gameBoardViewModel));


    }

    private void findViews(){
        this.gridView = findViewById(R.id.activity_main_grid_view);
    }

    @Override
    public void onGameBoardUpdated() {
        ((GameBoardAdapter) this.gridView.getAdapter()).notifyDataSetChanged();
    }
}