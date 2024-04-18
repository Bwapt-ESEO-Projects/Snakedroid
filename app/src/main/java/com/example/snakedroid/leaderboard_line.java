package com.example.snakedroid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.snakedroid.databinding.FragmentFormlaireBinding;
import com.example.snakedroid.databinding.FragmentLeaderboardLineBinding;


public class leaderboard_line extends Fragment {

    private FragmentLeaderboardLineBinding binding;
    private static final String NOM = "nom";
    private static final String SCORE = "score";


    private String mnom;
    private int mscore;

    public leaderboard_line() {
        // Required empty public constructor
    }


    public static leaderboard_line newInstance(String param1, int param2) {
        leaderboard_line fragment = new leaderboard_line();
        Bundle args = new Bundle();
        args.putString(NOM, param1);
        args.putInt(SCORE, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mnom = getArguments().getString(NOM);
            mscore = getArguments().getInt(SCORE);
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //// Affichage des noms et scores des joueurs dans le leaderboard

        binding = FragmentLeaderboardLineBinding.inflate(inflater,container,false);

        binding.textname.setText("nom : "+ mnom);
        binding.textscore.setText("score : " + mscore);

        return binding.getRoot();
    }
}