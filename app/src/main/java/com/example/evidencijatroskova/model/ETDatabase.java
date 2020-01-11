package com.example.evidencijatroskova.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.evidencijatroskova.model.DAOs.TrosakDao;
import com.example.evidencijatroskova.model.entities.Trosak;

@Database(entities = {Trosak.class}, version = 1)
public abstract class ETDatabase extends RoomDatabase {

    private static ETDatabase instance;

    public abstract TrosakDao trosakDao();

    public static synchronized ETDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ETDatabase.class, "ETDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
