package com.example.agentievanzareiss.repository;

import com.example.agentievanzareiss.model.Identifiable;

import java.util.Collection;

public interface Repository<Tid, T extends Identifiable<Tid>> {

    int add(T elem);
    void delete(T elem);
    void update(T elem);
    T findById(Tid id);
    Iterable<T> findAll();
}

