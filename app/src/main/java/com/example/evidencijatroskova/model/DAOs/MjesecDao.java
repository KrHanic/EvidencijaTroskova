package com.example.evidencijatroskova.model.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;


import com.example.evidencijatroskova.model.entities.Mjesec;

import java.util.List;

@Dao
public interface MjesecDao {

    @Query("SELECT datum, iznos, " +
            "(SELECT SUM(iznos) FROM TROSKOVI WHERE (STRFTIME('%m', BUDGETI.datum/1000, 'unixepoch'))" +
            " = (STRFTIME('%m', TROSKOVI.datum/1000, 'unixepoch'))) AS potroseno " +
            "FROM BUDGETI ORDER BY datum DESC")
    LiveData<List<Mjesec>> getAllMjeseci();
}
