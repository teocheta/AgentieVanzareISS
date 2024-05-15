package com.example.agentievanzareiss.repository;

import com.example.agentievanzareiss.model.Produs;

public interface ProdusRepository extends Repository<Integer, Produs> {

    Produs findByDenumire(String denumire);
}
