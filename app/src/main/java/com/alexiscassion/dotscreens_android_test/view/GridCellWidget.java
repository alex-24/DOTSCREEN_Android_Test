package com.alexiscassion.dotscreens_android_test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.alexiscassion.dotscreens_android_test.R;
import com.alexiscassion.dotscreens_android_test.model.Player;

import androidx.constraintlayout.widget.ConstraintLayout;

public class GridCellWidget extends ConstraintLayout {

    private ImageView playerIconImageView;

    public GridCellWidget(Context context) {
        super(context);
        initView(context, null, -1);
    }

    public GridCellWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, -1);
    }

    public GridCellWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr){
        this.playerIconImageView = findViewById(R.id.image_view_player_icon);
        inflate(context, R.layout.grid_cell, this);
    }

    public void updateCell(Player player) {
        if (player !=  null) {
            this.playerIconImageView.setColorFilter(player.getColor());
            this.playerIconImageView.setImageDrawable(getResources().getDrawable(player.getDrawableID(), getContext().getTheme()));
        }
    }
}
