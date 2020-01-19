package com.example.evidencijatroskova.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.evidencijatroskova.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class AddEditTrosakActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.evidencijatroskova.view.EXTRA_ID";
    public static final String EXTRA_NAZIV =
            "com.example.evidencijatroskova.view.EXTRA_NAZIV";
    public static final String EXTRA_IZNOS =
            "com.example.evidencijatroskova.view.EXTRA_IZNOS";
    public static final String EXTRA_OPIS =
            "com.example.evidencijatroskova.view.EXTRA_OPIS";
    public static final String EXTRA_DATUM =
            "com.example.evidencijatroskova.view.EXTRA_DATUM";

    private TextInputEditText etNaziv;
    private TextInputEditText etIznos;
    private TextInputEditText etOpis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trosak);

        etNaziv = findViewById(R.id.etNaziv);
        etIznos = findViewById(R.id.etIznos);
        etOpis = findViewById(R.id.etOpis);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Uredi trošak");
            etNaziv.setText(intent.getStringExtra(EXTRA_NAZIV));
            etIznos.setText(String.valueOf(intent.getDoubleExtra(EXTRA_IZNOS, 0)));
            etOpis.setText(intent.getStringExtra(EXTRA_OPIS));
        }else{
            setTitle("Dodaj trošak");
        }
    }

    private void saveTrosak(){
        String sNaziv = etNaziv.getText().toString();
        String sIznos = etIznos.getText().toString();
        Double dIznos;
        String sOpis = "";
        sOpis = etOpis.getText().toString();

        if(sNaziv.trim().isEmpty()){
            Toast.makeText(this, "Molimo unesite naziv.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(sIznos.trim().isEmpty()){
            Toast.makeText(this, "Molimo unesite iznos.", Toast.LENGTH_SHORT).show();
            return;
        }

        dIznos = Double.parseDouble(sIznos);
        Intent data = new Intent();
        data.putExtra(EXTRA_NAZIV, sNaziv);
        data.putExtra(EXTRA_IZNOS, dIznos);
        data.putExtra(EXTRA_OPIS, sOpis);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        Date datum = (Date)getIntent().getSerializableExtra(EXTRA_DATUM);
        if(id != -1){
            data.putExtra(EXTRA_ID, id);
            data.putExtra(EXTRA_DATUM, datum);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_trosak, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveTrosak:
                saveTrosak();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
