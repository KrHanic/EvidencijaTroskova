package com.example.evidencijatroskova.model.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;


import com.example.evidencijatroskova.model.entities.Mjesec;

import java.util.List;

@Dao
public interface MjesecDao {

    @Query("SELECT datum, iznos, " +
            "(SELECT SUM(iznos) FROM TROSKOVI WHERE (STRFTIME('%m', TROSKOVI.datum)) = (STRFTIME('%m', datum))) AS potroseno " +
            "FROM BUDGETI")
    LiveData<List<Mjesec>> getAllMjeseci();
}
