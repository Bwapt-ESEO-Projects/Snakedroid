package com.example.snakedroid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.example.snakedroid.databinding.ActivityMainBinding;
import java.util.List;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //// Configuration de la langue

        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        conf.setLocale((new Locale("fr")));
        res.updateConfiguration(conf, res.getDisplayMetrics());



    }

    @Override
    public void onResume()
    {
        /////// Intent pour les boutons de redirection vers les pages Game, Shop et Leaderboard

        Intent intent_shop = new Intent(MainActivity.this,Shop.class);
        Intent intent_leaderboard = new Intent(MainActivity.this,Leaderboard.class);

        super.onResume();
        binding.buttonPlay.setOnClickListener(view -> {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment_in,formlaire.newInstance());
            ft.commit();
        });

        binding.buttonShop.setOnClickListener(view -> startActivity(intent_shop));

        binding.buttonLeaderboard.setOnClickListener(view -> startActivity(intent_leaderboard));

    }
}