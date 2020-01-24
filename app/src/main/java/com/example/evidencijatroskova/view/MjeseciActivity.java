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
import android.widget.Toast;

import com.example.evidencijatroskova.R;
import com.example.evidencijatroskova.model.entities.Budget;
import com.example.evidencijatroskova.model.entities.Mjesec;
import com.example.evidencijatroskova.model.entities.Trosak;
import com.example.evidencijatroskova.view.adapters.PrikazMjeseciAdapter;
import com.example.evidencijatroskova.viewModels.MjesecViewModel;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class MjeseciActivity extends AppCompatActivity {
    public static int ADD_BUDGET_REQUEST = 1;
    public static int EDIT_BUDGET_REQUEST = 2;
    public static int BUDGET_REQUEST = 1;

    private MjesecViewModel mjesecViewModel;
    private Budget latestBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mjeseci);

        RecyclerView rvMjeseci = findViewById(R.id.rvMjeseci);
        rvMjeseci.setLayoutManager(new LinearLayoutManager(this));
        rvMjeseci.setHasFixedSize(true);

        final PrikazMjeseciAdapter prikazMjeseciAdapter = new PrikazMjeseciAdapter();
        rvMjeseci.setAdapter(prikazMjeseciAdapter);

        mjesecViewModel = ViewModelProviders.of(this).get(MjesecViewModel.class);
        mjesecViewModel.getAllMjesci().observe(this, new Observer<List<Mjesec>>() {
            @Override
            public void onChanged(List<Mjesec> mjesecs) {
                prikazMjeseciAdapter.setMjeseci(mjesecs);
            }
        });

        prikazMjeseciAdapter.setOnItemClickListener(new PrikazMjeseciAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Mjesec mjesec) {
                Intent intent = new Intent(MjeseciActivity.this, TroskoviActivity.class);
                intent.putExtra(TroskoviActivity.EXTRA_DATUM, mjesec.getDatum());
                startActivity(intent);
            }
        });

        mjesecViewModel.getAllBudgeti().observe(this, new Observer<List<Budget>>() {
            @Override
            public void onChanged(List<Budget> budgets) {
                if(budgets.size() != 0) {
                    latestBudget = budgets.get(0);
                    Date currentDate = new Date();
                    if (budgets.get(0).getDatum().getMonth() == currentDate.getMonth()) {
                        BUDGET_REQUEST = 2;
                    } else {
                        BUDGET_REQUEST = 1;
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_BUDGET_REQUEST && resultCode == RESULT_OK) {
            Double iznos = data.getDoubleExtra(AddEditBudgetActivity.EXTRA_IZNOS, 0);
            Date currentDate = new Date();

            Budget budget = new Budget(iznos, currentDate);
            mjesecViewModel.insert(budget);

            Toast.makeText(this, "Budget spremljen", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_BUDGET_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditBudgetActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Budget ne može biti promijenjen", Toast.LENGTH_SHORT).show();
                return;
            }

            Double iznos = data.getDoubleExtra(AddEditTrosakActivity.EXTRA_IZNOS, 0);
            Date datum = (Date) data.getSerializableExtra(AddEditTrosakActivity.EXTRA_DATUM);

            Budget budget = new Budget(iznos, datum);
            budget.setIdBudgeta(id);
            mjesecViewModel.update(budget);

            Toast.makeText(this, "Budget spremljen", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Budget nije spremljen", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_prikaz_mjeseci, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnAddEditBudget:
                if(BUDGET_REQUEST == ADD_BUDGET_REQUEST){
                    Intent intent = new Intent(MjeseciActivity.this, AddEditBudgetActivity.class);
                    startActivityForResult(intent, ADD_BUDGET_REQUEST);
                }else{
                    Intent intent = new Intent(MjeseciActivity.this, AddEditBudgetActivity.class);
                    intent.putExtra(AddEditBudgetActivity.EXTRA_ID, latestBudget.getIdBudgeta());
                    intent.putExtra(AddEditBudgetActivity.EXTRA_IZNOS, latestBudget.getIznos());
                    intent.putExtra(AddEditBudgetActivity.EXTRA_DATUM, latestBudget.getDatum());
                    startActivityForResult(intent, EDIT_BUDGET_REQUEST);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
