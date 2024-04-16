package com.example.snakedroid;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.snakedroid.databinding.ActivityShopBinding;
import java.util.ArrayList;
import java.util.List;


public class Shop extends AppCompatActivity {

    private ActivityShopBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityShopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




    }

    @Override
    public void onResume(){

        super.onResume();

        Intent intent_menu = new Intent(Shop.this,MainActivity.class);


        ArrayAdapter<String> adapter =new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item

        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        binding.spinnerMenu.setAdapter(adapter);
        binding.buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent_menu);
            }
        });



    }

}