package com.example.evidencijatroskova.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.evidencijatroskova.model.DAOs.TrosakDao;
import com.example.evidencijatroskova.model.entities.Trosak;

import java.util.List;

public class ETRepository {
    private TrosakDao trosakDao;
    private LiveData<List<Trosak>> allTroskovi;

    public ETRepository(Application application){
        ETDatabase database = ETDatabase.getInstance(application);
        trosakDao = database.trosakDao();
        allTroskovi = trosakDao.getAllTroskove();
    }

    public void insert(Trosak trosak){

    }

    public void update(Trosak trosak){

    }

    public LiveData<List<Trosak>> getAllTroskovi() {
        return allTroskovi;
    }
}
