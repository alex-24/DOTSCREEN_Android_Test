package com.alexiscassion.dotscreens_android_test.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.alexiscassion.dotscreens_android_test.model.Player;
import com.alexiscassion.dotscreens_android_test.view.GridCellWidget;
import com.alexiscassion.dotscreens_android_test.view_model.GameBoardViewModel;

public class GameBoardAdapter extends BaseAdapter {

    private final Context context;
    private GameBoardViewModel gameBoardViewModel;

    public GameBoardAdapter(Context context, GameBoardViewModel gameBoardViewModel) {
        this.context = context;
        this.gameBoardViewModel = gameBoardViewModel;
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public Object getItem(int i) {
        return this.gameBoardViewModel.getGameBoard().get(i%3, i/3);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View res = view;

        if (res == null) {;
            res = new GridCellWidget(this.context);
        }
        ((GridCellWidget) res).updateCell((Player) getItem(i));
        ((GridCellWidget) res).setOnClickListener(view1 -> gameBoardViewModel.insertMarkAt(i%3, i/3));

        return  res;
    }
}
