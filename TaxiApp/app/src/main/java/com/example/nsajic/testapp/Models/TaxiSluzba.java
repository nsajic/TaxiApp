package com.example.nsajic.testapp.Models;

import android.widget.CheckBox;

/**
 * Created by nsajic on 4/17/2018.
 */

public class TaxiSluzba {
    private String ime;
    private String brojTelefona;
    private Double ocena;
    private Double cenaPoKilometru;
    private Integer brojAutomobila;
    private Boolean favouriteChecked;

    public TaxiSluzba() {}

    public TaxiSluzba(String ime, String brojTelefona, Double ocena, Double cenaPoKilometru, int brojAutomobila, Boolean favouriteChecked) {
        this.ime = ime;
        this.brojTelefona = brojTelefona;
        this.ocena = ocena;
        this.brojAutomobila = brojAutomobila;
        this.cenaPoKilometru = cenaPoKilometru;
        this.favouriteChecked = favouriteChecked;
    }
    public TaxiSluzba(String ime, String brojTelefona, Double ocena) {
        this.ime = ime;
        this.brojTelefona = brojTelefona;
        this.ocena = ocena;
        this.brojAutomobila = 10;
        this.cenaPoKilometru = 390.3;
    }

    public void setBrojAutomobila(Integer brojAutomobila) {
        this.brojAutomobila = brojAutomobila;
    }

    public Double getCenaPoKilometru() { return cenaPoKilometru; }

    public Integer getBrojAutomobila() { return brojAutomobila; }

    public void setCenaPoKilometru(Double cenaPoKilometru) { this.cenaPoKilometru = cenaPoKilometru; }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public Double getOcena() {
        return ocena;
    }

    public void setOcena(Double ocena) {
        this.ocena = ocena;
    }

    public Boolean getFavouriteChecked() {
        return favouriteChecked;
    }

    public void setFavouriteChecked(Boolean favouriteChecked) {
        this.favouriteChecked = favouriteChecked;
    }
}
