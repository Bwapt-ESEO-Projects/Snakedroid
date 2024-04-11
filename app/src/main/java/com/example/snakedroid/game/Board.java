package com.example.snakedroid.game;

import static com.example.snakedroid.game.cell.CellType.*;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import com.example.snakedroid.databinding.FragmentBoardBinding;
import com.example.snakedroid.game.cell.Cell;
import com.example.snakedroid.game.cell.CellType;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Board#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Board extends Fragment {

    private static final int DEFAULT_HEIGHT = 10;
    private static final int DEFAULT_WIDTH = 10;

    private static final CellType[][] LEVEL1 = {
        {CORNER_TOP_LEFT, WALL_TOP, WALL_TOP, WALL_TOP, WALL_TOP, WALL_TOP, WALL_TOP, WALL_TOP, WALL_TOP, CORNER_TOP_RIGHT},
        {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
        {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
        {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
        {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
        {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
        {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
        {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
        {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
        {CORNER_BOTTOM_LEFT, WALL_BOTTOM, WALL_BOTTOM, WALL_BOTTOM, WALL_BOTTOM, WALL_BOTTOM, WALL_BOTTOM, WALL_BOTTOM, WALL_BOTTOM, CORNER_BOTTOM_RIGHT},
    };

    private static final CellType[][] LEVEL2 = {
            {CORNER_TOP_LEFT, WALL_TOP, WALL_TOP, WALL_TOP, WALL_TOP, WALL_TOP, WALL_TOP, WALL_TOP, WALL_TOP, CORNER_TOP_RIGHT},
            {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
            {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
            {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
            {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
            {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
            {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
            {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
            {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
            {CORNER_BOTTOM_LEFT, WALL_BOTTOM, WALL_BOTTOM, WALL_BOTTOM, WALL_BOTTOM, WALL_BOTTOM, WALL_BOTTOM, WALL_BOTTOM, WALL_BOTTOM, CORNER_BOTTOM_RIGHT},
    };

    private static final CellType[][] LEVEL3 = {
            {CORNER_TOP_LEFT, WALL_TOP, WALL_TOP, WALL_TOP, WALL_TOP, WALL_TOP, WALL_TOP, WALL_TOP, WALL_TOP, CORNER_TOP_RIGHT},
            {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
            {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
            {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
            {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
            {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
            {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
            {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
            {WALL_LEFT, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, WALL_RIGHT},
            {CORNER_BOTTOM_LEFT, WALL_BOTTOM, WALL_BOTTOM, WALL_BOTTOM, WALL_BOTTOM, WALL_BOTTOM, WALL_BOTTOM, WALL_BOTTOM, WALL_BOTTOM, CORNER_BOTTOM_RIGHT},
    };

    private int mHeight;
    private int mWidth;

    public Cell[][] mBoard;

    private int mLevel;

    public Board() {
        mBoard = new Cell[DEFAULT_HEIGHT][DEFAULT_WIDTH];
    }

    public static Board newInstance(int level) {
        Board fragment = new Board();
        Bundle args = new Bundle();
        args.putSerializable("level",level);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHeight = getArguments().getInt("HEIGHT", DEFAULT_HEIGHT);
            mWidth = getArguments().getInt("WIDTH", DEFAULT_WIDTH);
            mLevel = (int) getArguments().getSerializable("level");
        }

        switch (mLevel) {
            case 0:
                setBoard(LEVEL1);
                break;
            case 1:
                setBoard(LEVEL2);
                break;
            case 2:
                setBoard(LEVEL3);
                break;
            default:
                break;
        }

    }

    public void setLevel(int level) {
        this.mLevel = level;
    }

    public int getLevel() {
        return this.mLevel;
    }

    public void setBoard(CellType[][] level) {
        for(int i = 0; i < level.length; i++) {
            for(int j = 0; j < level[i].length; j++) {
                this.mBoard[i][j] = Cell.newInstance(level[i][j]);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        com.example.snakedroid.databinding.FragmentBoardBinding binding = FragmentBoardBinding.inflate(inflater, container, false);

        // Create a new GridLayout
        GridLayout gridLayout = new GridLayout(getContext());
        gridLayout.setColumnCount(mWidth);
        gridLayout.setRowCount(mHeight);
        gridLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        // Add each Cell fragment to a separate cell in the GridLayout
        for(int i = 0; i < mHeight; i++) {
            for(int j = 0; j < mWidth; j++) {
                FrameLayout frameLayout = new FrameLayout(getContext());
                frameLayout.setId(View.generateViewId());
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 0;
                params.columnSpec = GridLayout.spec(j, 1f);
                params.rowSpec = GridLayout.spec(i, 1f);
                frameLayout.setLayoutParams(params);
                gridLayout.addView(frameLayout);

                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.add(frameLayout.getId(), mBoard[i][j]);
                ft.commit();
            }
        }

        // Add the GridLayout to the root view
        binding.gridContainer.addView(gridLayout);

        return binding.getRoot();
    }
}