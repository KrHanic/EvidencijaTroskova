package com.example.evidencijatroskova.model;

import android.app.Application;
import android.os.AsyncTask;

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
        new InsertTrosakAsyncTask(trosakDao).execute(trosak);
    }

    public void update(Trosak trosak){
        new UpdateTrosakAsyncTask(trosakDao).execute(trosak);
    }

    public LiveData<List<Trosak>> getAllTroskovi() {
        return allTroskovi;
    }

    private static class InsertTrosakAsyncTask extends AsyncTask<Trosak, Void, Void>{
        private TrosakDao trosakDao;

        private InsertTrosakAsyncTask(TrosakDao trosakDao){
            this.trosakDao = trosakDao;
        }

        @Override
        protected Void doInBackground(Trosak... trosaks) {
            trosakDao.insert(trosaks[0]);
            return null;
        }
    }

    private static class UpdateTrosakAsyncTask extends AsyncTask<Trosak, Void, Void>{
        private TrosakDao trosakDao;

        private UpdateTrosakAsyncTask(TrosakDao trosakDao){
            this.trosakDao = trosakDao;
        }

        @Override
        protected Void doInBackground(Trosak... trosaks) {
            trosakDao.update(trosaks[0]);
            return null;
        }
    }
}
