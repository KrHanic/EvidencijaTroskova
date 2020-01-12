package com.example.evidencijatroskova.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.evidencijatroskova.model.ETRepository;
import com.example.evidencijatroskova.model.entities.Trosak;

import java.util.List;

public class TrosakViewModel extends AndroidViewModel {
    private ETRepository repository;
    private LiveData<List<Trosak>> allTroskovi;

    public TrosakViewModel(@NonNull Application application) {
        super(application);
        repository = new ETRepository(application);
        allTroskovi = repository.getAllTroskovi();
    }

    public void insert(Trosak trosak){
        repository.insert(trosak);
    }

    public void update(Trosak trosak){
        repository.update(trosak);
    }

    public LiveData<List<Trosak>> getAllTroskovi() {
        return allTroskovi;
    }
}
