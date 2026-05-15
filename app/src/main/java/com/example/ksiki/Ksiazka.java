package com.example.ksiki;

import androidx.annotation.NonNull;

public class Ksiazka {
    private String tytul;
    private String autor;
    private double cena;
    private String typ;
    private int promocja;

    public Ksiazka(String tytul, String autor, double cena, String typ, int promocja) {
        this.tytul = tytul;
        this.autor = autor;
        this.cena = cena;
        this.typ = typ;
        this.promocja = promocja;
    }

    @NonNull
    @Override
    public String toString() {
        String infoPromocja = "";
        if (promocja > 0) {
            infoPromocja = " (Promocja: " + promocja + "%)";
        }
        return String.format("%s - %s\nCena: %.2f, Typ: %s%s", 
                tytul, autor, cena, typ, infoPromocja);
    }
}
