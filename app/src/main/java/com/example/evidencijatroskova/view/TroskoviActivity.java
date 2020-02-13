package com.example.evidencijatroskova.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.evidencijatroskova.R;
import com.example.evidencijatroskova.model.OnResult;
import com.example.evidencijatroskova.model.entities.Trosak;
import com.example.evidencijatroskova.view.adapters.PrikazTroskovaAdapter;
import com.example.evidencijatroskova.viewModels.TrosakViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TroskoviActivity extends AppCompatActivity {
    public static final int ADD_TROSAK_REQUEST = 1;
    public static final int EDIT_TROSAK_REQUEST = 2;
    public static final String EXTRA_DATUM =
            "com.example.evidencijatroskova.view.EXTRA_DATUM";
    public Date sentMjesec;
    public PrikazTroskovaAdapter prikazTroskovaAdapter;

    private TrosakViewModel trosakViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troskovi);

        FloatingActionButton btnAddTrosak = findViewById(R.id.btnAddTrosak);

        Intent intent = getIntent();
        sentMjesec = (Date)intent.getSerializableExtra(EXTRA_DATUM);

        String mjesec = "0" +(sentMjesec.getMonth() + 1) + ". " + (1900 + sentMjesec.getYear())+ ".";
        setTitle("Troškovi za " + mjesec);

        if ((sentMjesec.getMonth() != new Date().getMonth()) ||  (sentMjesec.getYear() != new Date().getYear())){
            btnAddTrosak.setVisibility(View.GONE);
        }else {
            btnAddTrosak.setVisibility(View.VISIBLE);
        }



        btnAddTrosak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TroskoviActivity.this, AddEditTrosakActivity.class);
                startActivityForResult(intent, ADD_TROSAK_REQUEST);
            }
        });

        RecyclerView rvTroskovi = findViewById(R.id.rvTroskovi);
        rvTroskovi.setLayoutManager(new LinearLayoutManager(this));
        rvTroskovi.setHasFixedSize(true);

        prikazTroskovaAdapter = new PrikazTroskovaAdapter();
        rvTroskovi.setAdapter(prikazTroskovaAdapter);


        trosakViewModel = ViewModelProviders.of(this).get(TrosakViewModel.class);
        trosakViewModel.getAllTroskovi().observe(this, new Observer<List<Trosak>>() {
            @Override
            public void onChanged(List<Trosak> trosaks) {
                trosakViewModel.getTroskoviByMonthSortedByDateDesc(sentMjesec.getMonth(), new OnResult<List<Trosak>>() {
                    @Override
                    public void onSuccess(List<Trosak> result) {
                        prikazTroskovaAdapter.submitList(result);
                    }
                });
            }
        });

        prikazTroskovaAdapter.setOnItemClickListener(new PrikazTroskovaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Trosak trosak) {
                Calendar calendar  = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -7);
                Date datumPrijeSedamDana = calendar.getTime();
                Date datumTroska = trosak.getDatum();

                if(datumTroska.after(datumPrijeSedamDana)) {
                    Intent intent = new Intent(TroskoviActivity.this, AddEditTrosakActivity.class);
                    intent.putExtra(AddEditTrosakActivity.EXTRA_ID, trosak.getIdTroska());
                    intent.putExtra(AddEditTrosakActivity.EXTRA_NAZIV, trosak.getNaziv());
                    intent.putExtra(AddEditTrosakActivity.EXTRA_IZNOS, trosak.getIznos());
                    intent.putExtra(AddEditTrosakActivity.EXTRA_OPIS, trosak.getOpis());
                    intent.putExtra(AddEditTrosakActivity.EXTRA_DATUM, trosak.getDatum());
                    startActivityForResult(intent, EDIT_TROSAK_REQUEST);
                }else{
                    Toast.makeText(TroskoviActivity.this, "Trošak je stariji od 7 dana.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_TROSAK_REQUEST && resultCode == RESULT_OK){
            String naziv = data.getStringExtra(AddEditTrosakActivity.EXTRA_NAZIV);
            Double iznos = data.getDoubleExtra(AddEditTrosakActivity.EXTRA_IZNOS, 0);
            String opis = data.getStringExtra(AddEditTrosakActivity.EXTRA_OPIS);
            Date currentDate = new Date();

            Trosak trosak = new Trosak( naziv, iznos, opis, currentDate);
            trosakViewModel.insert(trosak);

            Toast.makeText(this, "Trošak spremljen", Toast.LENGTH_SHORT).show();
        } else if(requestCode == EDIT_TROSAK_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditTrosakActivity.EXTRA_ID, -1);

            if(id == -1){
                Toast.makeText(this, "Trošak ne može biti promijenjen", Toast.LENGTH_SHORT).show();
                return;
            }

            String naziv = data.getStringExtra(AddEditTrosakActivity.EXTRA_NAZIV);
            Double iznos = data.getDoubleExtra(AddEditTrosakActivity.EXTRA_IZNOS, 0);
            String opis = data.getStringExtra(AddEditTrosakActivity.EXTRA_OPIS);
            Date datum = (Date) data.getSerializableExtra(AddEditTrosakActivity.EXTRA_DATUM);

            Trosak trosak = new Trosak(naziv, iznos, opis, datum);
            trosak.setIdTroska(id);
            trosakViewModel.update(trosak);

            Toast.makeText(this, "Trošak spremljen", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Trošak nije spremljen", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_prikaz_troskova, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnSortDateDesc:
                trosakViewModel.getTroskoviByMonthSortedByDateDesc(sentMjesec.getMonth(), new OnResult<List<Trosak>>() {
                    @Override
                    public void onSuccess(List<Trosak> result) {
                        prikazTroskovaAdapter.submitList(result);
                    }
                });
                return true;
            case R.id.btnSortDateAsc:
                trosakViewModel.getTroskoviByMonthSortedByDateAsc(sentMjesec.getMonth(), new OnResult<List<Trosak>>() {
                    @Override
                    public void onSuccess(List<Trosak> result) {
                        prikazTroskovaAdapter.submitList(result);
                    }
                });
                return true;
            case R.id.btnSortIznosDesc:
                trosakViewModel.getTroskoviByMonthSortedByIznosDesc(sentMjesec.getMonth(), new OnResult<List<Trosak>>() {
                    @Override
                    public void onSuccess(List<Trosak> result) {
                        prikazTroskovaAdapter.submitList(result);
                    }
                });
                return true;
            case R.id.btnSortIznosAsc:
                trosakViewModel.getTroskoviByMonthSortedByIznosAsc(sentMjesec.getMonth(), new OnResult<List<Trosak>>() {
                    @Override
                    public void onSuccess(List<Trosak> result) {
                        prikazTroskovaAdapter.submitList(result);
                    }
                });
                return true;
            case R.id.btnSortNazivDesc:
                trosakViewModel.getTroskoviByMonthSortedByNazivDesc(sentMjesec.getMonth(), new OnResult<List<Trosak>>() {
                    @Override
                    public void onSuccess(List<Trosak> result) {
                        prikazTroskovaAdapter.submitList(result);
                    }
                });
                return true;
            case R.id.btnSortNazivAsc:
                trosakViewModel.getTroskoviByMonthSortedByNazivAsc(sentMjesec.getMonth(), new OnResult<List<Trosak>>() {
                    @Override
                    public void onSuccess(List<Trosak> result) {
                        prikazTroskovaAdapter.submitList(result);
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
