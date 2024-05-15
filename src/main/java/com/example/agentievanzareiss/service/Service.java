package com.example.agentievanzareiss.service;

import com.example.agentievanzareiss.model.Produs;
import com.example.agentievanzareiss.utils.events.ChangeEvent;
import com.example.agentievanzareiss.utils.observer.Observable;

public interface Service extends Observable<ChangeEvent> {

    void adaugaProdus(String denumire, float pret, int stoc);

    void stergeProdus(Produs produs);

    void updateProdus(String denumire, float pret, int stoc);

    Iterable<Produs> findAllProduse();

    void login(String username, String password);

}
