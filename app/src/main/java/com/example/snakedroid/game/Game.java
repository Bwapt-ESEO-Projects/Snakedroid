package com.example.snakedroid.game;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.snakedroid.R;

public class Game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Board board = Board.newInstance(0);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.board_container, board);
        ft.commit();
    }
}