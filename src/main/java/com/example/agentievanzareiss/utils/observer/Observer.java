package com.example.agentievanzareiss.utils.observer;
import com.example.agentievanzareiss.utils.events.Event;

public interface Observer<E extends Event>{

    void update(E eveniment);
}
