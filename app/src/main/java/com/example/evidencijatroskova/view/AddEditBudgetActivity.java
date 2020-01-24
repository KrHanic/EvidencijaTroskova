package com.example.evidencijatroskova.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.evidencijatroskova.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class AddEditBudgetActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.evidencijatroskova.view.EXTRA_ID";
    public static final String EXTRA_IZNOS =
            "com.example.evidencijatroskova.view.EXTRA_IZNOS";
    public static final String EXTRA_DATUM =
            "com.example.evidencijatroskova.view.EXTRA_DATUM";

    private TextInputEditText etIznos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_budget);

        etIznos = findViewById(R.id.etIznos);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Uredi budget");
            etIznos.setText(String.valueOf(intent.getDoubleExtra(EXTRA_IZNOS, 0)));
        }else{
            setTitle("Dodaj budget");
        }
    }

    private void saveBudget(){
        String sIznos = etIznos.getText().toString();
        Double dIznos;

        if(sIznos.trim().isEmpty()){
            Toast.makeText(this, "Molimo unesite iznos.", Toast.LENGTH_SHORT).show();
            return;
        }

        dIznos = Double.parseDouble(sIznos);
        Intent data = new Intent();
        data.putExtra(EXTRA_IZNOS, dIznos);

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
        menuInflater.inflate(R.menu.menu_add_edit_budget, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveBudget:
                saveBudget();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
