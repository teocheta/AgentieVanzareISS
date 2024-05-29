package com.example.agentievanzareiss.model;

public class ProdusComanda implements Identifiable<Integer>{

    private int idProdusComanda;

    private Produs produs;

    private Comanda comanda;

    private int cantitate;

    public ProdusComanda(Produs produs, Comanda comanda, int cantitate){
        this.produs = produs;
        this.comanda = comanda;
        this.cantitate = cantitate;
    }
    @Override
    public Integer getID() {
        return idProdusComanda;
    }

    @Override
    public void setID(Integer id) {
        this.idProdusComanda = id;
    }

    public Produs getProdus() {
        return produs;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public void setProdus(Produs produs) {
        this.produs = produs;
    }

    public void setCantitate(int cantitate){
        this.cantitate = cantitate;
    }

    @Override
    public String toString() {
        return "ProdusComanda{" +
                "idProdusComanda=" + idProdusComanda +
                ", produs=" + produs +
                ", comanda=" + comanda +
                ", cantitate=" + cantitate +
                '}';
    }
}
