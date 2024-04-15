package com.example.snakedroid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.snakedroid.databinding.ActivityMainBinding;
import java.util.ArrayList;
import java.util.List;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    // private List<item_shop> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        conf.setLocale((new Locale("fr")));
        res.updateConfiguration(conf, res.getDisplayMetrics());



    }

    @Override
    public void onResume()
    {
        Intent intent_game = new Intent(MainActivity.this,Game.class);
        Intent intent_shop = new Intent(MainActivity.this,Shop.class);
        super.onResume();
        binding.buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               startActivity(intent_game);


            }
        });

        binding.buttonShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent_shop);
            }
        });

    }
}