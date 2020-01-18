package com.example.evidencijatroskova.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.evidencijatroskova.R;
import com.example.evidencijatroskova.model.entities.Trosak;
import com.example.evidencijatroskova.view.adapters.PrikazTroskovaAdapter;
import com.example.evidencijatroskova.viewModels.TrosakViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

public class TroskoviActivity extends AppCompatActivity {
    public static final int ADD_TROSAK_REQUEST = 1;

    private TrosakViewModel trosakViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troskovi);

        FloatingActionButton btnAddTrosak = findViewById(R.id.btnAddTrosak);
        btnAddTrosak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TroskoviActivity.this, AddTrosakActivity.class);
                startActivityForResult(intent, ADD_TROSAK_REQUEST);
            }
        });

        RecyclerView rvTroskovi = findViewById(R.id.rvTroskovi);
        rvTroskovi.setLayoutManager(new LinearLayoutManager(this));
        rvTroskovi.setHasFixedSize(true);

        final PrikazTroskovaAdapter prikazTroskovaAdapter = new PrikazTroskovaAdapter();
        rvTroskovi.setAdapter(prikazTroskovaAdapter);

        trosakViewModel = ViewModelProviders.of(this).get(TrosakViewModel.class);
        trosakViewModel.getAllTroskovi().observe(this, new Observer<List<Trosak>>() {
            @Override
            public void onChanged(List<Trosak> trosaks) {
                prikazTroskovaAdapter.setTroskovi(trosaks);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_TROSAK_REQUEST && resultCode == RESULT_OK){
            String naziv = data.getStringExtra(AddTrosakActivity.EXTRA_NAZIV);
            Double iznos = data.getDoubleExtra(AddTrosakActivity.EXTRA_IZNOS, 0);
            String opis = data.getStringExtra(AddTrosakActivity.EXTRA_OPIS);
            Date currentDate = new Date();

            Trosak trosak = new Trosak( naziv, iznos, opis, currentDate);
            trosakViewModel.insert(trosak);

            Toast.makeText(this, "Trošak spremljen", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Trošak nije spremljen", Toast.LENGTH_SHORT).show();
        }
    }
}
