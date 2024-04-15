package com.example.snakedroid;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snakedroid.databinding.ActivityShopBinding;
import java.util.ArrayList;
import java.util.List;


public class Shop extends AppCompatActivity {

    private ActivityShopBinding binding;
    private List<CosmeticsFragment> cosmeticHeads;
    private List<CosmeticsFragment> cosmeticBodies;
    private List<CosmeticsFragment> cosmeticTails;

    private List<CosmeticsFragment> cosmeticHats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        binding = ActivityShopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cosmeticHeads = new ArrayList<>();
        cosmeticBodies = new ArrayList<>();
        cosmeticTails = new ArrayList<>();
        cosmeticHats = new ArrayList<>();

        cosmeticHeads.add(CosmeticsFragment.newInstance("snake_head", R.drawable.snake_head));
        cosmeticBodies.add(CosmeticsFragment.newInstance("snake_body", R.drawable.snake_body));
        cosmeticTails.add(CosmeticsFragment.newInstance("snake_tail", R.drawable.snake_tail));
        cosmeticHats.add(CosmeticsFragment.newInstance("snake_hat", R.drawable.banana));

        cosmeticHeads.add(CosmeticsFragment.newInstance("snake_head_ice", R.drawable.snake_head_ice));
        cosmeticBodies.add(CosmeticsFragment.newInstance("snake_body_ice", R.drawable.snake_body_ice));
        cosmeticTails.add(CosmeticsFragment.newInstance("snake_tail_ice", R.drawable.snake_tail_ice));
        cosmeticHats.add(CosmeticsFragment.newInstance("snake_hat_ice", R.drawable.apple));

        cosmeticHeads.add(CosmeticsFragment.newInstance("snake_head_ice", R.drawable.snake_head_ice));
        cosmeticBodies.add(CosmeticsFragment.newInstance("snake_body_ice", R.drawable.snake_body_ice));
        cosmeticTails.add(CosmeticsFragment.newInstance("snake_tail_ice", R.drawable.snake_tail_ice));
        cosmeticHats.add(CosmeticsFragment.newInstance("snake_hat_ice", R.drawable.apple));

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for(CosmeticsFragment frag : cosmeticHeads) {
            ft.add(R.id.cosmetics_head,frag);
        }

        for(CosmeticsFragment frag : cosmeticBodies) {
            ft.add(R.id.cosmetics_body,frag);
        }

        for(CosmeticsFragment frag : cosmeticTails) {
            ft.add(R.id.cosmetics_tail,frag);
        }

        for(CosmeticsFragment frag : cosmeticHats) {
            ft.add(R.id.cosmetics_hat,frag);
        }

        ft.commit();
    }

    @Override
    public void onResume(){

        super.onResume();

    }

}