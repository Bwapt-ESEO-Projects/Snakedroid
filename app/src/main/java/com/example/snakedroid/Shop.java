package com.example.snakedroid;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.snakedroid.databinding.ActivityShopBinding;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Shop extends AppCompatActivity {

    private ActivityShopBinding binding;
    private SharedPreferences DataGame;
    private SharedPreferences DataShop;
    private int currentMoney;
    private int equippedHeadImg;
    private int equippedBodyImg;
    private int equippedTailImg;
    private int equippedAngleImg;
    private int equippedHatImg;

    private HashSet<String> boughtListSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShopBinding.inflate(getLayoutInflater());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        binding = ActivityShopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Context context_shop = binding.getRoot().getContext();
        DataGame = getSharedPreferences("Game", MODE_PRIVATE);
        DataShop = getSharedPreferences("Shop", MODE_PRIVATE);

        Set<String> boughtList = DataShop.getStringSet("BOUGHT", new HashSet<>(Arrays.asList("head0", "body0", "tail0", "angle0", "hat0")));
        boughtListSet = new HashSet<>(boughtList);

        currentMoney = DataGame.getInt("MONEY_VALUE", 0);
        equippedHeadImg = DataGame.getInt("IMG_HEAD", R.drawable.snake_head);
        equippedBodyImg = DataGame.getInt("IMG_BODY", R.drawable.snake_body);
        equippedTailImg = DataGame.getInt("IMG_TAIL", R.drawable.snake_tail);
        equippedAngleImg = DataGame.getInt("IMG_ANGLE", R.drawable.snake_body_angle);
        equippedHatImg = DataGame.getInt("IMG_HAT", R.drawable.wizard_hat);
        
        binding.money.setText("Money : " + currentMoney);

        List<CosmeticsFragment> cosmeticHeads = new ArrayList<>();
        List<CosmeticsFragment> cosmeticBodies = new ArrayList<>();
        List<CosmeticsFragment> cosmeticTails = new ArrayList<>();
        List<CosmeticsFragment> cosmeticAngles = new ArrayList<>();
        List<CosmeticsFragment> cosmeticHats = new ArrayList<>();

        cosmeticHeads.add(CosmeticsFragment.newInstance("0", R.drawable.snake_head));
        cosmeticBodies.add(CosmeticsFragment.newInstance("0", R.drawable.snake_body));
        cosmeticTails.add(CosmeticsFragment.newInstance("0", R.drawable.snake_tail));
        cosmeticAngles.add(CosmeticsFragment.newInstance("0", R.drawable.snake_body_angle));
        cosmeticHats.add(CosmeticsFragment.newInstance("0", R.drawable.wizard_hat));

        cosmeticHeads.add(CosmeticsFragment.newInstance("10", R.drawable.snake_head_ice));
        cosmeticBodies.add(CosmeticsFragment.newInstance("10", R.drawable.snake_body_ice));
        cosmeticTails.add(CosmeticsFragment.newInstance("10", R.drawable.snake_tail_ice));
        cosmeticAngles.add(CosmeticsFragment.newInstance("10", R.drawable.snake_body_ice_angle));
        cosmeticHats.add(CosmeticsFragment.newInstance("10", R.drawable.speed_chronometer));

        cosmeticHeads.add(CosmeticsFragment.newInstance("10", R.drawable.snake_head_desert));
        cosmeticBodies.add(CosmeticsFragment.newInstance("10", R.drawable.snake_body_desert));
        cosmeticTails.add(CosmeticsFragment.newInstance("10", R.drawable.snake_tail_desert));
        cosmeticAngles.add(CosmeticsFragment.newInstance("10", R.drawable.snake_body_desert_angle));
        cosmeticHats.add(CosmeticsFragment.newInstance("10", R.drawable.slow_chronometer));

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

        // Post a Runnable to the main thread's message queue
        binding.getRoot().post(() -> {
            setBoughtList();
            // Call checkIfEquipped for each equipped cosmetic item
            checkIfEquipped(getEquippedTag(equippedHeadImg, "head"));
            checkIfEquipped(getEquippedTag(equippedBodyImg, "body"));
            checkIfEquipped(getEquippedTag(equippedTailImg, "tail"));
            checkIfEquipped(getEquippedTag(equippedAngleImg, "angle"));
            checkIfEquipped(getEquippedTag(equippedHatImg, "hat"));

        });

        Intent intent_main = new Intent(Shop.this,MainActivity.class);
        binding.valider.setOnClickListener(view -> {

            SharedPreferences.Editor gameEditor = DataGame.edit();

            gameEditor.putInt("MONEY_VALUE", currentMoney);
            gameEditor.putInt("IMG_HEAD", equippedHeadImg);
            gameEditor.putInt("IMG_BODY", equippedBodyImg);
            gameEditor.putInt("IMG_TAIL", equippedTailImg);
            gameEditor.putInt("IMG_ANGLE", equippedAngleImg);
            gameEditor.putInt("IMG_HAT", equippedHatImg);
            gameEditor.apply();

            SharedPreferences.Editor shopEditor = DataShop.edit();
            shopEditor.putStringSet("BOUGHT", boughtListSet);
            shopEditor.apply();

            startActivity(intent_main);
        });
    }

    @Override
    public void onResume(){

        super.onResume();

        checkIfEquipped(getEquippedTag(equippedHeadImg, "head"));
        checkIfEquipped(getEquippedTag(equippedBodyImg, "body"));
        checkIfEquipped(getEquippedTag(equippedTailImg, "tail"));
        checkIfEquipped(getEquippedTag(equippedAngleImg, "angle"));
        checkIfEquipped(getEquippedTag(equippedHatImg, "hat"));

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

        binding.money.setText("Money : " + currentMoney);
    }

    private void setBoughtList() {
        for (String tag : boughtListSet) {
            CosmeticsFragment frag = (CosmeticsFragment) getSupportFragmentManager().findFragmentByTag(tag);
            if (frag != null) {
                frag.setBought(true);
                TextView priceView = frag.getView().findViewById(R.id.cosmetics_price);
                priceView.setCompoundDrawables(null, null, null, null);
                priceView.setText("✓");
            }
        }
    }

    private int getEquippedTagNum(int img) {
        if (img == R.drawable.snake_head || img == R.drawable.snake_body || img == R.drawable.snake_tail || img == R.drawable.snake_body_angle || img == R.drawable.wizard_hat) {
            return 0;
        } else if (img == R.drawable.snake_head_ice || img == R.drawable.snake_body_ice || img == R.drawable.snake_tail_ice || img == R.drawable.snake_body_ice_angle || img == R.drawable.speed_chronometer) {
            return 1;
        } else if (img == R.drawable.snake_head_desert || img == R.drawable.snake_body_desert || img == R.drawable.snake_tail_desert || img == R.drawable.snake_body_desert_angle || img == R.drawable.slow_chronometer) {
            return 2;
        } else {
            return -1;
        }
    }

    private String getEquippedTag(int img, String categoryPrefix) {
        int num = getEquippedTagNum(img);
        if (num == -1) {
            return null;
        }
        return categoryPrefix + num;
    }

    private int getEquippedHeadImg(String tag) {
        switch (tag) {
            case "head0":
                return R.drawable.snake_head;
            case "head1":
                return R.drawable.snake_head_ice;
            case "head2":
                return R.drawable.snake_head_desert;
            default:
                return -1;
        }
    }

    private int getEquippedBodyImg(String tag) {
        switch (tag) {
            case "body0":
                return R.drawable.snake_body;
            case "body1":
                return R.drawable.snake_body_ice;
            case "body2":
                return R.drawable.snake_body_desert;
            default:
                return -1;
        }
    }

    private int getEquippedTailImg(String tag) {
        switch (tag) {
            case "tail0":
                return R.drawable.snake_tail;
            case "tail1":
                return R.drawable.snake_tail_ice;
            case "tail2":
                return R.drawable.snake_tail_desert;
            default:
                return -1;
        }
    }

    private int getEquippedAngleImg(String tag) {
        switch (tag) {
            case "angle0":
                return R.drawable.snake_body_angle;
            case "angle1":
                return R.drawable.snake_body_ice_angle;
            case "angle2":
                return R.drawable.snake_body_desert_angle;
            default:
                return -1;
        }
    }

    private int getEquippedHatImg(String tag) {
        switch (tag) {
            case "hat0":
                return R.drawable.wizard_hat;
            case "hat1":
                return R.drawable.speed_chronometer;
            case "hat2":
                return R.drawable.slow_chronometer;
            default:
                return -1;
        }
    }

    private void saveEquippedImg(String tag) {
        String categoryPrefix = tag.substring(0, tag.length() - 1);

        switch (categoryPrefix) {
            case "head":
                equippedHeadImg = getEquippedHeadImg(tag);
                break;
            case "body":
                equippedBodyImg = getEquippedBodyImg(tag);
                break;
            case "tail":
                equippedTailImg = getEquippedTailImg(tag);
                break;
            case "angle":
                equippedAngleImg = getEquippedAngleImg(tag);
                break;
            case "hat":
                equippedHatImg = getEquippedHatImg(tag);
                break;
        }
    }

    private void checkIfEquipped(String tag) {
        CosmeticsFragment frag = (CosmeticsFragment) getSupportFragmentManager().findFragmentByTag(tag);
        if (frag != null && frag.getView() != null) {
            frag.setEquipped(true);
            frag.setBought(true);

            View buttonView = frag.getView().findViewById(R.id.cosmetics_button);
            TextView priceView = frag.getView().findViewById(R.id.cosmetics_price);
            buttonView.setBackgroundColor(getResources().getColor(R.color.green));
            priceView.setCompoundDrawables(null, null, null, null);
            priceView.setText("✓");

        }
    }

    private void setFragmentOnClickListener(String tag) {
        CosmeticsFragment frag = (CosmeticsFragment) getSupportFragmentManager().findFragmentByTag(tag);
        if (frag != null && frag.getView() != null) {
            View cosmeticButtonView = frag.getView().findViewById(R.id.cosmetics_button);
            TextView cosmeticPriceView = frag.getView().findViewById(R.id.cosmetics_price);
            cosmeticButtonView.setOnClickListener(v -> {
                if (!frag.getBought() && currentMoney >= frag.getPrice()) {
                    currentMoney = currentMoney - frag.getPrice();
                    unequip(tag.substring(0, tag.length() - 1));
                    frag.setEquipped(true);
                    frag.setBought(true);
                    saveEquippedImg(tag);
                    boughtListSet.add(tag);
                    cosmeticPriceView.setText("✓");
                    cosmeticPriceView.setCompoundDrawables(null, null, null, null);
                    binding.money.setText("Money : " + currentMoney);
                }
                if (frag.getBought()) {
                    unequip(tag.substring(0, tag.length() - 1));
                    frag.setEquipped(true);
                    resetBackgrounds(tag.substring(0, tag.length() - 1));
                    v.setBackgroundColor(getResources().getColor(R.color.green));
                }
            });
        }
    }

    private void unequip(String categoryPrefix) {
        for (int i = 0; i < 3; i++) { // Assuming there are 3 fragments in each category
            CosmeticsFragment frag = (CosmeticsFragment) getSupportFragmentManager().findFragmentByTag(categoryPrefix + i);
            if (frag != null) {
                frag.setEquipped(false);
            }
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