package com.example.snakedroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.snakedroid.databinding.FragmentCosmeticsBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//// Fragment pour les cosmétiques comporant l'image et le prix
public class CosmeticsFragment extends Fragment {

    private static final int COSMETIC_ID = 0;
    private static final String COSMETIC_PRICE = "cosmeticPrice";
    private int mCosmeticId;
    private String mCosmeticPrice;

    private boolean isBought = false;
    private boolean isEquipped = false;

    public CosmeticsFragment() {
        // Required empty public constructor
    }
    public static CosmeticsFragment newInstance(String cosmeticPrice, int cosmeticId) {

        //// Création d'un nouveau fragment pour les cosmétiques

        CosmeticsFragment fragment = new CosmeticsFragment();
        Bundle args = new Bundle();
        args.putInt(String.valueOf(COSMETIC_ID), cosmeticId);
        args.putString(COSMETIC_PRICE, cosmeticPrice);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCosmeticId = getArguments().getInt(String.valueOf(COSMETIC_ID));
            mCosmeticPrice = getArguments().getString(COSMETIC_PRICE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Binding du fragment Cosmetics

        @NonNull FragmentCosmeticsBinding fragmentBinding = FragmentCosmeticsBinding.inflate(inflater, container, false);

        //// Tentative de redimensionnement de l'image des cosmétiques

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mCosmeticId);
        BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
        drawable.setAntiAlias(false);
        drawable.setFilterBitmap(false);


        fragmentBinding.cosmeticsButton.setImageDrawable(drawable);
        fragmentBinding.cosmeticsPrice.setText(mCosmeticPrice);

        // Inflate the layout for this fragment
        return fragmentBinding.getRoot();
    }

    public void setBought(boolean bought) { //// Setter pour l'achat des cosmétiques
        this.isBought = bought;
    }

    public boolean getBought() {
        return this.isBought;
    } //// Getter pour l'achat des cosmétiques

    public void setEquipped(boolean equipped) {     //// Setter pour l'équipement des cosmétiques
        this.isEquipped = equipped;
    }

    public int getPrice() {
        return Integer.parseInt(mCosmeticPrice);
    }   //// Getter pour le prix des cosmétiques
}