package com.example.agentievanzareiss.model;

import java.util.Objects;

public class Produs implements Identifiable<Integer> {
    private int idProdus;

    private String denumire;

    private float pret;

    private int stoc;

    public Produs(String denumire, float pret, int stoc){
        this.denumire = denumire;
        this.pret = pret;
        this.stoc = stoc;
    }
    @Override
    public Integer getID() {
        return idProdus;
    }

    @Override
    public void setID(Integer id) {
        this.idProdus = id;

    }

    public String getDenumire() {
        return denumire;
    }

    public float getPret() {
        return pret;
    }

    public int getStoc() {
        return stoc;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public void setStoc(int stoc) {
        this.stoc = stoc;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "idProdus=" + idProdus +
                ", denumire='" + denumire + '\'' +
                ", pret=" + pret +
                ", stoc=" + stoc +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produs produs = (Produs) o;
        return idProdus == produs.idProdus && Float.compare(pret, produs.pret) == 0 && stoc == produs.stoc && Objects.equals(denumire, produs.denumire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProdus, denumire, pret, stoc);
    }
}
