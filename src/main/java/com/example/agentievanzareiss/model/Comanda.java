package com.example.agentievanzareiss.model;

public class Comanda implements Identifiable<Integer>{

    private int idComanda;

    private Utilizator agent;

    private String detaliiLivrare;

    public Comanda(Utilizator agent, String detaliiLivrare){
        this.agent = agent;
        this.detaliiLivrare = detaliiLivrare;
    }
    @Override
    public Integer getID() {
        return idComanda;
    }

    @Override
    public void setID(Integer id) {
        this.idComanda = id;

    }

    public Utilizator getAgent() {
        return agent;
    }

    public String getDetaliiLivrare() {
        return detaliiLivrare;
    }

    public void setDetaliiLivrare(String detaliiLivrare) {
        this.detaliiLivrare = detaliiLivrare;
    }

    public void setAgent(Utilizator agent) {
        this.agent = agent;
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "idComanda=" + idComanda +
                ", agent=" + agent +
                ", detaliiLivrare='" + detaliiLivrare + '\'' +
                '}';
    }
}
