package com.example.nsajic.testapp.Models;

/**
 * Created by nsajic on 4/17/2018.
 */

public class TaxiSluzba {
    private String ime;
    private String brojTelefona;
    private Double ocena;
    private Double cenaPoKilometru;
    private Integer brojAutomobila;

    public TaxiSluzba(String ime, String brojTelefona, Double ocena, Double cenaPoKilometru, int brojAutomobila) {
        this.ime = ime;
        this.brojTelefona = brojTelefona;
        this.ocena = ocena;
        this.brojAutomobila = brojAutomobila;
        this.cenaPoKilometru = cenaPoKilometru;
    }
    public TaxiSluzba(String ime, String brojTelefona, Double ocena) {
        this.ime = ime;
        this.brojTelefona = brojTelefona;
        this.ocena = ocena;
        this.brojAutomobila = 10;
        this.cenaPoKilometru = 390.3;
    }

    public void setBrojAutomobila(int brojAutomobila) { this.brojAutomobila = brojAutomobila; }

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
}
