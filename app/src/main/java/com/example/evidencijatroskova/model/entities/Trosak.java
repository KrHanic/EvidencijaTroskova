package com.example.evidencijatroskova.model.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "TROSKOVI")
public class Trosak {

    @PrimaryKey(autoGenerate = true)
    private int idTroska;

    private String naziv;

    private BigDecimal iznos;

    private String opis;

    private Calendar datum;

    public Trosak(String naziv, BigDecimal iznos, String opis, Calendar datum) {
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

    public BigDecimal getIznos() {
        return iznos;
    }

    public String getOpis() {
        return opis;
    }

    public Calendar getDatum() {
        return datum;
    }
}
