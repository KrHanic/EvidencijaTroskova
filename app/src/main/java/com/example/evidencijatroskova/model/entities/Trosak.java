package com.example.evidencijatroskova.model.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.evidencijatroskova.model.DateConverter;

import java.util.Date;

@Entity(tableName = "TROSKOVI")
@TypeConverters(DateConverter.class)
public class Trosak {

    @PrimaryKey(autoGenerate = true)
    private int idTroska;

    private String naziv;

    private Double iznos;

    private String opis;

    private Date datum;

    public Trosak(String naziv, Double iznos, String opis, Date datum) {
        this.naziv = naziv;
        this.iznos = iznos;
        this.opis = opis;
        this.datum = datum;
    }

    public void setIdTroska(int idTroska) {
        this.idTroska = idTroska;
    }

    public int getIdTroska() {
        return idTroska;
    }

    public String getNaziv() {
        return naziv;
    }

    public Double getIznos() {
        return iznos;
    }

    public String getOpis() {
        return opis;
    }

    public Date getDatum() {
        return datum;
    }
}
