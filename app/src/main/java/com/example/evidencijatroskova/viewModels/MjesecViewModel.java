package com.example.evidencijatroskova.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.evidencijatroskova.model.ETRepository;
import com.example.evidencijatroskova.model.entities.Mjesec;
import com.example.evidencijatroskova.model.entities.Budget;
import com.example.evidencijatroskova.model.entities.Trosak;

import java.util.List;

public class MjesecViewModel extends AndroidViewModel {
    private ETRepository repository;
    private LiveData<List<Trosak>> allTroskovi;
    private LiveData<List<Budget>> allBudgeti;
    private LiveData<List<Mjesec>> allMjesci;

    public MjesecViewModel(@NonNull Application application) {
        super(application);
        repository = new ETRepository(application);
        allTroskovi = repository.getAllTroskovi();
        allBudgeti = repository.getAllBudgeti();
        allMjesci = repository.getAllMjeseci();
    }

    public void insert(Budget budget){
        repository.insertBudget(budget);
    }

    public void update(Budget budget){
        repository.updateBudget(budget);
    }

    public LiveData<List<Budget>> getAllBudgeti() {
        return allBudgeti;
    }

    public  LiveData<List<Mjesec>> getAllMjesci(){
        return allMjesci;
    }
}
