package com.example.evidencijatroskova.model.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.evidencijatroskova.model.entities.Budget;

import java.util.List;

@Dao
public interface BudgetDao {
    @Insert
    void insert(Budget budget);

    @Update
    void update(Budget budget);

    @Query("SELECT * FROM BUDGETI ORDER BY datum DESC")
    LiveData<List<Budget>> getAllBudgete();
}
