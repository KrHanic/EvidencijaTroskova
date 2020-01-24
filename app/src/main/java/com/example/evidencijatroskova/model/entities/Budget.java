package com.example.evidencijatroskova.model.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.evidencijatroskova.model.DateConverter;

import java.util.Date;

@Entity(tableName = "BUDGETI")
public class Budget {
    @PrimaryKey(autoGenerate = true)
    private int idBudgeta;

    private Double iznos;

    @TypeConverters(DateConverter.class)
    private Date datum;

    public Budget(Double iznos, Date datum) {
        this.iznos = iznos;
        this.datum = datum;
    }

    public void setIdBudgeta(int idBudgeta) {
        this.idBudgeta = idBudgeta;
    }

    public int getIdBudgeta() {
        return idBudgeta;
    }

    public Double getIznos() {
        return iznos;
    }

    public Date getDatum() {
        return datum;
    }
}
