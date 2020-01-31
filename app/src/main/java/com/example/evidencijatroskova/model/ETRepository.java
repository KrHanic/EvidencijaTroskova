package com.example.evidencijatroskova.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.evidencijatroskova.model.DAOs.BudgetDao;
import com.example.evidencijatroskova.model.DAOs.MjesecDao;
import com.example.evidencijatroskova.model.DAOs.TrosakDao;
import com.example.evidencijatroskova.model.entities.Budget;
import com.example.evidencijatroskova.model.entities.Mjesec;
import com.example.evidencijatroskova.model.entities.Trosak;

import java.util.Date;
import java.util.List;

public class ETRepository {
    ETDatabase database;
    private TrosakDao trosakDao;
    private LiveData<List<Trosak>> allTroskovi;
    private BudgetDao budgetDao;
    private LiveData<List<Budget>> allBudgeti;
    private MjesecDao mjesecDao;
    private LiveData<List<Mjesec>> allMjeseci;

    public ETRepository(Application application){
        database = ETDatabase.getInstance(application);
        trosakDao = database.trosakDao();
        allTroskovi = trosakDao.getAllTroskove();
        budgetDao = database.budgetDao();
        allBudgeti = budgetDao.getAllBudgete();
        mjesecDao = database.mjesecDao();
        allMjeseci = mjesecDao.getAllMjeseci();
    }

    public void insertTrosak(Trosak trosak){
        new InsertTrosakAsyncTask(trosakDao).execute(trosak);
    }

    public void updateTrosak(Trosak trosak){
        new UpdateTrosakAsyncTask(trosakDao).execute(trosak);
    }

    public void nukeTableTrosak(){
        new NukeTableAsyncTask(trosakDao).execute();
    }

    public LiveData<List<Trosak>> getAllTroskovi() {
        return allTroskovi;
    }

    public List<Trosak> getTroskoviByMonth(int mjesec){
        TroskoviByMonthParams params = new TroskoviByMonthParams(trosakDao, mjesec);
        new GetTroskoviByMonthAsyncTask(params).execute(params);

    }

    public void insertBudget(Budget budget){
        new InsertBudgetAsyncTask(budgetDao).execute(budget);
    }

    public void updateBudget(Budget budget){
        new UpdateBudgetAsyncTask(budgetDao).execute(budget);
    }

    public LiveData<List<Budget>> getAllBudgeti() {
        return allBudgeti;
    }

    public LiveData<List<Mjesec>> getAllMjeseci() {
        return allMjeseci;
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

    private static class NukeTableAsyncTask extends AsyncTask<Void, Void, Void>{
        private TrosakDao trosakDao;

        private NukeTableAsyncTask(TrosakDao trosakDao){
            this.trosakDao = trosakDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            trosakDao.nukeTable();
            return null;
        }
    }

    private static class InsertBudgetAsyncTask extends  AsyncTask<Budget, Void, Void>{
        private BudgetDao budgetDao;

        private InsertBudgetAsyncTask(BudgetDao budgetDao){
            this.budgetDao = budgetDao;
        }

        @Override
        protected Void doInBackground(Budget... budgets) {
            budgetDao.insert(budgets[0]);
            return null;
        }
    }

    private static class UpdateBudgetAsyncTask extends  AsyncTask<Budget, Void, Void>{
        private BudgetDao budgetDao;

        private UpdateBudgetAsyncTask(BudgetDao budgetDao){
            this.budgetDao = budgetDao;
        }

        @Override
        protected Void doInBackground(Budget... budgets) {
            budgetDao.update(budgets[0]);
            return null;
        }
    }

    private static class TroskoviByMonthParams {
        TrosakDao trosakDao;
        int mjesec;

        TroskoviByMonthParams(TrosakDao trosakDao, int date) {
            this.trosakDao = trosakDao;
            this.mjesec = date;
        }
    }

    private static class GetTroskoviByMonthAsyncTask extends AsyncTask<TroskoviByMonthParams, Void, List<Trosak>>{
        private TrosakDao trosakDao;

        private GetTroskoviByMonthAsyncTask(TroskoviByMonthParams params){
            this.trosakDao = params.trosakDao;
        }

        @Override
        protected List<Trosak> doInBackground(TroskoviByMonthParams... troskoviByMonthParams) {

            return trosakDao.getTroskoviByMonth(troskoviByMonthParams[0].mjesec);
        }

        @Override
        protected void onPostExecute(List<Trosak> trosaks) {
            super.onPostExecute(trosaks);
        }
    }
}
