package com.example.agentievanzareiss.repository;

import com.example.agentievanzareiss.model.Utilizator;

public interface UtilizatorRepository extends Repository<Integer, Utilizator>{

   Utilizator findByUsername(String username);

}
