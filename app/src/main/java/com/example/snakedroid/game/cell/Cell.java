package com.example.snakedroid.game.cell;

import static com.example.snakedroid.game.cell.CellType.*;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.snakedroid.R;
import com.example.snakedroid.databinding.FragmentCellBinding;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Cell#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cell extends Fragment {

    private FragmentCellBinding binding;
    private int mBackground;

    private boolean isSolid;

    private static CellType mCellType;

    public Cell() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cellType Parameter 1.
     * @return A new instance of fragment Cell.
     */
    // TODO: Rename and change types and number of parameters
    public static Cell newInstance(CellType cellType) {
        Cell fragment = new Cell();
        Bundle args = new Bundle();

        args.putSerializable("cellType", cellType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCellType = (CellType) getArguments().getSerializable("cellType");
        }

        switch(Objects.requireNonNull(mCellType)) {
            case WALL_TOP:
                setSolidState(true);
                setBackground(R.drawable.wall_bp);
            case WALL_BOTTOM:
                setSolidState(true);
                setBackground(R.drawable.wall_bp);
            case WALL_LEFT:
                setSolidState(true);
                setBackground(R.drawable.wall_bp);
            case WALL_RIGHT:
                setSolidState(true);
                setBackground(R.drawable.wall_bp);
            case CORNER_TOP_LEFT:
                setSolidState(true);
                setBackground(R.drawable.corner_bp);
            case CORNER_TOP_RIGHT:
                setSolidState(true);
                setBackground(R.drawable.corner_bp);
            case CORNER_BOTTOM_LEFT:
                setSolidState(true);
                setBackground(R.drawable.corner_bp);
            case CORNER_BOTTOM_RIGHT:
                setSolidState(true);
                setBackground(R.drawable.corner_bp);
            case OBSTACLE_1:
                setSolidState(true);
                setBackground(R.drawable.obstacle_bp);
            case OBSTACLE_2:
                setSolidState(true);
                setBackground(R.drawable.obstacle_bp);
            case OBJECT_APPLE:
                setSolidState(false);
                setBackground(R.drawable.object_bp);
            case OBJECT_GRAPE:
                setSolidState(false);
                setBackground(R.drawable.object_bp);
            case OBJECT_BANANA:
                setSolidState(false);
                setBackground(R.drawable.object_bp);
            case SNAKE_HEAD:
                setSolidState(true);
                setBackground(R.drawable.snake_bp);
            case SNAKE_BODY:
                setSolidState(true);
                setBackground(R.drawable.snake_bp);
            case SNAKE_BODY_CORNER_TOP_LEFT:
                setSolidState(true);
                setBackground(R.drawable.snake_bp);
            case SNAKE_BODY_CORNER_TOP_RIGHT:
                setSolidState(true);
                setBackground(R.drawable.snake_bp);
            case SNAKE_BODY_CORNER_BOTTOM_LEFT:
                setSolidState(true);
                setBackground(R.drawable.snake_bp);
            case SNAKE_BODY_CORNER_BOTTOM_RIGHT:
                setSolidState(true);
                setBackground(R.drawable.snake_bp);
            case SNAKE_TAIL:
                setSolidState(true);
                setBackground(R.drawable.snake_bp);
            case EMPTY:
                setSolidState(false);
        }
    }

    public void setBackground(int background) {
        this.mBackground = background;
    }

    public int getBackground() {
        return this.mBackground;
    }

    public void setSolidState(boolean state) {
        this.isSolid = state;
    }

    public boolean getSolidState() {
        return this.isSolid;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCellBinding.inflate(inflater, container, false);
        // replace with your drawable resource ID
        Drawable drawable = null;
        if (this.mBackground != 0) {
            drawable = ContextCompat.getDrawable(getContext(), this.mBackground);
        }

        binding.fragmentCell.setBackground(drawable);
        return binding.getRoot();
    }
}