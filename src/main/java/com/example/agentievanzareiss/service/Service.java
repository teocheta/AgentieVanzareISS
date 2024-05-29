package com.example.agentievanzareiss.service;

import com.example.agentievanzareiss.model.Comanda;
import com.example.agentievanzareiss.model.Produs;
import com.example.agentievanzareiss.model.ProdusComanda;
import com.example.agentievanzareiss.model.Utilizator;
import com.example.agentievanzareiss.utils.events.ChangeEvent;
import com.example.agentievanzareiss.utils.observer.Observable;

import java.util.List;

public interface Service extends Observable<ChangeEvent> {

    void adaugaProdus(String denumire, float pret, int stoc);

    void stergeProdus(Produs produs);

    void updateProdus(String denumire, float pret, int stoc);

    Iterable<Produs> findAllProduse();

    boolean exista(Utilizator crtUser);

    Utilizator getUserByUsername(String username);

    int adaugaComanda(Comanda comanda);

    void adaugaProdusComanda(int idComanda, List<ProdusComanda> produse);

    Produs filtreazaProduse(String filtru);
}
