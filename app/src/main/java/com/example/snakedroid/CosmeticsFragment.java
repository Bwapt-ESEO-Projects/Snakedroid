package com.example.snakedroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.snakedroid.databinding.FragmentCosmeticsBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CosmeticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CosmeticsFragment extends Fragment {

    private FragmentCosmeticsBinding binding;

    private static final int COSMETIC_ID = 0;
    private int mCosmeticId;

    public CosmeticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cosmeticName Parameter 1.
     * @param cosmeticId Parameter 2.
     * @return A new instance of fragment CosmeticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CosmeticsFragment newInstance(String cosmeticName, int cosmeticId) {
        CosmeticsFragment fragment = new CosmeticsFragment();
        Bundle args = new Bundle();
        args.putInt(String.valueOf(COSMETIC_ID), cosmeticId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCosmeticId = getArguments().getInt(String.valueOf(COSMETIC_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCosmeticsBinding.inflate(inflater, container, false);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mCosmeticId);
        BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
        drawable.setAntiAlias(false);
        drawable.setFilterBitmap(false);
        binding.cosmeticsImage.setImageDrawable(drawable);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}