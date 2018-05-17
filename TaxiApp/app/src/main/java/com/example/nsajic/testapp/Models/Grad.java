package com.example.nsajic.testapp.Models;

/**
 * Created by nsajic on 4/16/2018.
 */

public class Grad {
    String ime;
    String postanskiBroj;

    public Grad(String ime, String postanskiBroj){
        this.ime = ime;
        this.postanskiBroj = postanskiBroj;
    }

    public String getIme() {
        return ime;
    }

    public String getPostanskiBroj() {
        return postanskiBroj;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPostanskiBroj(String postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }
}
