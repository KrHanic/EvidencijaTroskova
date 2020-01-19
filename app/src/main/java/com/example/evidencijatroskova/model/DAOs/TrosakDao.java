package com.example.evidencijatroskova.model.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.evidencijatroskova.model.entities.Trosak;

import java.util.List;

@Dao
public interface TrosakDao {
    @Insert
    void insert(Trosak trosak);

    @Update
    void update(Trosak trosak);

    @Query("DELETE FROM TROSKOVI")
    void nukeTable();

    @Query("SELECT * FROM TROSKOVI ORDER BY datum DESC")
    LiveData<List<Trosak>> getAllTroskove();
}
