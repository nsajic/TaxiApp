package com.example.nsajic.testapp.Models;

/**
 * Created by nsajic on 4/17/2018.
 */

public class TaxiSluzba {
    private String ime;
    private String brojTelefona;
    private String ocena;

    public TaxiSluzba(String ime, String brojTelefona, String ocena) {
        this.ime = ime;
        this.brojTelefona = brojTelefona;
        this.ocena = ocena;
    }

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

    public String getOcena() {
        return ocena;
    }

    public void setOcena(String ocena) {
        this.ocena = ocena;
    }
}
