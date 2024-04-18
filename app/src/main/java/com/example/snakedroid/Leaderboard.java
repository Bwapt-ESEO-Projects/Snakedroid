package com.example.snakedroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.snakedroid.databinding.ActivityLeaderboardBinding;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard extends AppCompatActivity {

    private ActivityLeaderboardBinding binding;

    private List<leaderboard_line> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeaderboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fragments = new ArrayList<>();

        // Récupération des scores et noms des joueurs

        SharedPreferences prefs = getSharedPreferences("Game",MODE_PRIVATE);
        for (int i = 1; i <= prefs.getInt("NB_PLAYER",0); i++) {
            String nom = "NAME"+i;
            String score = "SCORE"+i;
            fragments.add(leaderboard_line.newInstance(prefs.getString(nom,"none"), prefs.getInt(score,0)));
        }

        // Ajout des fragments dans la vue
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for(leaderboard_line frag : fragments) {
            ft.add(R.id.fragment,frag);
        }
        ft.commit();

        // Intent pour le bouton de retour vers le menu principal
        Intent intent_main = new Intent(Leaderboard.this,MainActivity.class);
        binding.buttonBack.setOnClickListener(view -> {
            startActivity(intent_main);
            finish();
        });
    }
}