package com.example.snakedroid;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.snakedroid.databinding.FragmentFormlaireBinding;

//// Fragment pour le formulaire de nom du joueur
public class formlaire extends Fragment {

    private FragmentFormlaireBinding binding;

    public formlaire() {
        // Required empty public constructor
    }



    public static formlaire newInstance() {
        formlaire fragment = new formlaire();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormlaireBinding.inflate(inflater,container,false);

        binding.buttonValid.setOnClickListener(view -> {
            String text = binding.usernameText.getText().toString();
            Intent intent = new Intent(getActivity(), Game.class);
            intent.putExtra("name",text);
            startActivity(intent);
        });

        return binding.getRoot();
    }
}