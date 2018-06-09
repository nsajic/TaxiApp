package com.example.nsajic.testapp.Models;

public class TaxiStajalista {

    private double geoDuzina;
    private double geoSirina;
    private String adresa;

    public TaxiStajalista(){
        this.geoDuzina = 0;
        this.geoSirina = 0;
        adresa = "";
    }

    public TaxiStajalista(double geoDuzina, double geoSirina){
        this.geoDuzina = geoDuzina;
        this.geoSirina = geoSirina;
        adresa = "";
    }

    public TaxiStajalista(String adresa){
        this.geoDuzina = 0;
        this.geoSirina = 0;
        this.adresa = adresa;
    }

    public double getGeoDuzina() {
        return geoDuzina;
    }

    public double getGeoSirina() {
        return geoSirina;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public void setGeoDuzina(double geoDuzina) {
        this.geoDuzina = geoDuzina;
    }

    public void setGeoSirina(double geoSirina) {
        this.geoSirina = geoSirina;
    }
}
