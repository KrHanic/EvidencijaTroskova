package com.example.evidencijatroskova.model.entities;

import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

import com.example.evidencijatroskova.model.DateConverter;

import java.util.Date;

public class Mjesec {
    @TypeConverters(DateConverter.class)
    private Date datum;

    private Double iznos;

    private Double potroseno;

    public Mjesec(Date datum, Double iznos, Double potroseno) {
        this.datum = datum;
        this.iznos = iznos;
        this.potroseno = potroseno;
    }

    public Date getDatum() {
        return datum;
    }

    public Double getIznos() {
        return iznos;
    }

    public Double getPotroseno() {
        return potroseno;
    }
}
