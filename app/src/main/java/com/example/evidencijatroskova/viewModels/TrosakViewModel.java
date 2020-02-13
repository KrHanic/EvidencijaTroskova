package com.example.evidencijatroskova.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.evidencijatroskova.model.ETRepository;
import com.example.evidencijatroskova.model.OnResult;
import com.example.evidencijatroskova.model.entities.Trosak;

import java.util.Date;
import java.util.List;

public class TrosakViewModel extends AndroidViewModel {
    private ETRepository repository;
    private LiveData<List<Trosak>> allTroskovi;
    private List<Trosak> troskoviMjeseca;

    public TrosakViewModel(@NonNull Application application) {
        super(application);
        repository = new ETRepository(application);
        allTroskovi = repository.getAllTroskovi();
    }

    public void insert(Trosak trosak){
        repository.insertTrosak(trosak);
    }

    public void update(Trosak trosak){
        repository.updateTrosak(trosak);
    }

    public void delete(Trosak trosak){
        repository.deleteTrosak(trosak);
    }

    public LiveData<List<Trosak>> getAllTroskovi() {
        return allTroskovi;
    }

    public void getTroskoviByMonthSortedByDateDesc(int mjesec, OnResult<List<Trosak>> onResultListener){
        repository.getTroskoviByMonthSortedByDateDesc(mjesec, onResultListener);
    }

    public void getTroskoviByMonthSortedByDateAsc(int mjesec, OnResult<List<Trosak>> onResultListener){
        repository.getTroskoviByMonthSortedByDateAsc(mjesec, onResultListener);
    }

    public void getTroskoviByMonthSortedByIznosDesc(int mjesec, OnResult<List<Trosak>> onResultListener){
        repository.getTroskoviByMonthSortedByIznosDesc(mjesec, onResultListener);
    }

    public void getTroskoviByMonthSortedByIznosAsc(int mjesec, OnResult<List<Trosak>> onResultListener){
        repository.getTroskoviByMonthSortedByIznosAsc(mjesec, onResultListener);
    }

    public void getTroskoviByMonthSortedByNazivDesc(int mjesec, OnResult<List<Trosak>> onResultListener){
        repository.getTroskoviByMonthSortedByNazivDesc(mjesec, onResultListener);
    }

    public void getTroskoviByMonthSortedByNazivAsc(int mjesec, OnResult<List<Trosak>> onResultListener){
        repository.getTroskoviByMonthSortedByNazivAsc(mjesec, onResultListener);
    }
}
