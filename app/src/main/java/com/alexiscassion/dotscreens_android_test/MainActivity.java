package com.alexiscassion.dotscreens_android_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.alexiscassion.dotscreens_android_test.view_model.GameViewModel;

public class MainActivity extends AppCompatActivity {


    private ImageView cell1;
    private ImageView cell2;
    private ImageView cell3;
    private ImageView cell4;
    private ImageView cell5;
    private ImageView cell6;
    private ImageView cell7;
    private ImageView cell8;
    private ImageView cell9;

    private ImageView[] cells = new ImageView[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GameViewModel gameViewModel = new GameViewModel();



        this.cells[0] = this.cell1;
        this.cells[1] = this.cell2;
        this.cells[2] = this.cell3;
        this.cells[3] = this.cell4;
        this.cells[4] = this.cell5;
        this.cells[5] = this.cell6;
        this.cells[6] = this.cell7;
        this.cells[7] = this.cell8;
        this.cells[8] = this.cell8;

        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                int finalI = i;
                int finalJ = j;
                this.cells[i * 3 + j].setOnClickListener(view -> {
                    gameViewModel.insertMarkAt(finalI, finalJ);
                });
            }
        }


    }

    private void findViews(){
        this.cell1 = findViewById(R.id.b1);
        this.cell2 = findViewById(R.id.b2);
        this.cell3 = findViewById(R.id.b3);
        this.cell4 = findViewById(R.id.b4);
        this.cell5 = findViewById(R.id.b5);
        this.cell6 = findViewById(R.id.b6);
        this.cell7 = findViewById(R.id.b7);
        this.cell8 = findViewById(R.id.b8);
        this.cell8 = findViewById(R.id.b8);
    }
}