package com.example.snakedroid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.snakedroid.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


}
    @Override
    public void onResume()
    {
        Intent intent_game= new Intent(MainActivity.this,Game.class);
        Intent intent_shop=new Intent(MainActivity.this,Shop.class);
        super.onResume();
        binding.buttonPlay.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                startActivity(intent_game);


            }
        });

        binding.buttonShop.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startActivity(intent_shop);
            }
        });

    }
}