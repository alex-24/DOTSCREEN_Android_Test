package com.alexiscassion.dotscreens_android_test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.alexiscassion.dotscreens_android_test.R;
import com.alexiscassion.dotscreens_android_test.model.Player;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Represents a Tic Tac Toe grid cell in the UI.
 * It can display the symbol icon of a player or a blank square.
 */
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
        inflate(context, R.layout.grid_cell, this);
        this.playerIconImageView = findViewById(R.id.grid_cell_image_view_player_icon);
    }

    /**
     * Updates the player for this cell and displays his icon.
     *
     * @param player the player to update.
     */
    public void updateCell(Player player) {
        if (player !=  null) {
            //this.playerIconImageView.setColorFilter(player.getColor());
            this.playerIconImageView.setImageDrawable(getResources().getDrawable(player.getDrawableID(), getContext().getTheme()));
        }
    }
}
