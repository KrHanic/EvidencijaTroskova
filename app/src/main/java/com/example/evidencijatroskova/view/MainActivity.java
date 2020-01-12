package com.example.evidencijatroskova.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.evidencijatroskova.R;
import com.example.evidencijatroskova.view.Fragments.PrikazTroskovaFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PrikazTroskovaFragment prikazTroskovaFragment = new PrikazTroskovaFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                prikazTroskovaFragment)
                .commit();
    }
}
