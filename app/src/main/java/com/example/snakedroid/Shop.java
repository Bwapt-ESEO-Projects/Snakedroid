package com.example.snakedroid;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.snakedroid.databinding.ActivityShopBinding;
import java.util.ArrayList;
import java.util.List;


public class Shop extends AppCompatActivity {

    private ActivityShopBinding binding;
    private int money = 50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShopBinding.inflate(getLayoutInflater());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        com.example.snakedroid.databinding.ActivityShopBinding binding = ActivityShopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<CosmeticsFragment> cosmeticHeads = new ArrayList<>();
        List<CosmeticsFragment> cosmeticBodies = new ArrayList<>();
        List<CosmeticsFragment> cosmeticTails = new ArrayList<>();
        List<CosmeticsFragment> cosmeticAngles = new ArrayList<>();
        List<CosmeticsFragment> cosmeticHats = new ArrayList<>();

        cosmeticHeads.add(CosmeticsFragment.newInstance("0", R.drawable.snake_head));
        cosmeticBodies.add(CosmeticsFragment.newInstance("0", R.drawable.snake_body));
        cosmeticTails.add(CosmeticsFragment.newInstance("0", R.drawable.snake_tail));
        cosmeticAngles.add(CosmeticsFragment.newInstance("0", R.drawable.grappe));
        cosmeticHats.add(CosmeticsFragment.newInstance("0", R.drawable.wizard_hat));

        cosmeticHeads.add(CosmeticsFragment.newInstance("10", R.drawable.snake_head_ice));
        cosmeticBodies.add(CosmeticsFragment.newInstance("10", R.drawable.snake_body_ice));
        cosmeticTails.add(CosmeticsFragment.newInstance("10", R.drawable.snake_tail_ice));
        cosmeticAngles.add(CosmeticsFragment.newInstance("10", R.drawable.grappe));
        cosmeticHats.add(CosmeticsFragment.newInstance("10", R.drawable.wizard_hat_160));

        cosmeticHeads.add(CosmeticsFragment.newInstance("10", R.drawable.snake_head_ice));
        cosmeticBodies.add(CosmeticsFragment.newInstance("10", R.drawable.snake_body_ice));
        cosmeticTails.add(CosmeticsFragment.newInstance("10", R.drawable.snake_tail_ice));
        cosmeticAngles.add(CosmeticsFragment.newInstance("10", R.drawable.grappe));
        cosmeticHats.add(CosmeticsFragment.newInstance("10", R.drawable.apple));

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        int index = 0;
        for(CosmeticsFragment frag : cosmeticHeads) {
            ft.add(R.id.cosmetics_head,frag, "head" + index);
            index++;
        }

        index = 0;
        for(CosmeticsFragment frag : cosmeticBodies) {
            ft.add(R.id.cosmetics_body,frag, "body" + index);
            index++;
        }

        index = 0;
        for(CosmeticsFragment frag : cosmeticTails) {
            ft.add(R.id.cosmetics_tail,frag, "tail" + index);
            index++;
        }

        index = 0;
        for(CosmeticsFragment frag : cosmeticAngles) {
            ft.add(R.id.cosmetics_angle,frag, "angle" + index);
            index++;
        }

        index = 0;
        for(CosmeticsFragment frag : cosmeticHats) {
            ft.add(R.id.cosmetics_hat,frag, "hat" + index);
            index++;
        }
        ft.commit();

        checkIfEquipped(cosmeticHeads, "head");
        checkIfEquipped(cosmeticBodies, "body");
        checkIfEquipped(cosmeticTails, "tail");
        checkIfEquipped(cosmeticAngles, "angle");
        checkIfEquipped(cosmeticHats, "hat");

        Intent intent_main = new Intent(Shop.this,MainActivity.class);
        binding.valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent_main);
            }
        });

    }



    @Override
    public void onResume(){

        super.onResume();

        setFragmentOnClickListener("head0");
        setFragmentOnClickListener("head1");
        setFragmentOnClickListener("head2");
        setFragmentOnClickListener("body0");
        setFragmentOnClickListener("body1");
        setFragmentOnClickListener("body2");
        setFragmentOnClickListener("tail0");
        setFragmentOnClickListener("tail1");
        setFragmentOnClickListener("tail2");
        setFragmentOnClickListener("angle0");
        setFragmentOnClickListener("angle1");
        setFragmentOnClickListener("angle2");
        setFragmentOnClickListener("hat0");
        setFragmentOnClickListener("hat1");
        setFragmentOnClickListener("hat2");
    }

    private void checkIfEquipped(List<CosmeticsFragment> fragments, String tag) {
        for (int i = 0; i < fragments.size(); i++) {
            CosmeticsFragment frag = (CosmeticsFragment) getSupportFragmentManager().findFragmentByTag(tag + i);
            if (frag != null && frag.getEquipped() && frag.getView() != null) {
                View view = frag.getView().findViewById(R.id.cosmetics_button);
                view.setBackgroundColor(getResources().getColor(R.color.green));
            }
        }
    }

    private void setFragmentOnClickListener(String tag) {
        CosmeticsFragment frag = (CosmeticsFragment) getSupportFragmentManager().findFragmentByTag(tag);
        if (frag != null && frag.getView() != null) {
            View cosmeticButtonView = frag.getView().findViewById(R.id.cosmetics_button);
            TextView cosmeticPriceView = frag.getView().findViewById(R.id.cosmetics_price);
            cosmeticButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!frag.getBought() && money >= frag.getPrice()) {
                        money -= frag.getPrice();
                        frag.setBought(true);
                        cosmeticPriceView.setText("✓");
                        cosmeticPriceView.setCompoundDrawables(null, null, null, null);
                    }
                    if (frag.getBought()) {
                        frag.setEquipped(true);
                        resetBackgrounds(tag.substring(0, tag.length() - 1));
                        v.setBackgroundColor(getResources().getColor(R.color.green));
                    }

                }
            });
        }
    }
    private void resetBackgrounds(String categoryPrefix) {
        for (int i = 0; i < 3; i++) { // Assuming there are 3 fragments in each category
            CosmeticsFragment frag = (CosmeticsFragment) getSupportFragmentManager().findFragmentByTag(categoryPrefix + i);
            if (frag != null && frag.getView() != null) {
                View view = frag.getView().findViewById(R.id.cosmetics_button);
                view.setBackgroundColor(Color.TRANSPARENT); // Reset the background color
            }
        }
    }
}