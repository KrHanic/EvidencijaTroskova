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

    @Query("SELECT * FROM TROSKOVI WHERE STRFTIME('%m', datum/1000, 'unixepoch') = :mjesec ORDER BY datum DESC")
    List<Trosak> getTroskoviByMonthSortedByDateDesc(String mjesec);

    @Query("SELECT * FROM TROSKOVI WHERE STRFTIME('%m', datum/1000, 'unixepoch') = :mjesec ORDER BY datum ASC")
    List<Trosak> getTroskoviByMonthSortedByDateAsc(String mjesec);

    @Query("SELECT * FROM TROSKOVI WHERE STRFTIME('%m', datum/1000, 'unixepoch') = :mjesec ORDER BY iznos DESC")
    List<Trosak> getTroskoviByMonthSortedByIznosDesc(String mjesec);

    @Query("SELECT * FROM TROSKOVI WHERE STRFTIME('%m', datum/1000, 'unixepoch') = :mjesec ORDER BY iznos ASC")
    List<Trosak> getTroskoviByMonthSortedByIznosAsc(String mjesec);

    @Query("SELECT * FROM TROSKOVI WHERE STRFTIME('%m', datum/1000, 'unixepoch') = :mjesec ORDER BY naziv DESC")
    List<Trosak> getTroskoviByMonthSortedByNazivDesc(String mjesec);

    @Query("SELECT * FROM TROSKOVI WHERE STRFTIME('%m', datum/1000, 'unixepoch') = :mjesec ORDER BY naziv ASC")
    List<Trosak> getTroskoviByMonthSortedByNazivAsc(String mjesec);
}
