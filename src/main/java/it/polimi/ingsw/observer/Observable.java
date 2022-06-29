package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable class inherited from a class that can be observed
 */
public class Observable {

    private final List<Observer> observers = new ArrayList<>();

    /**
     * adds an observer to the list of class that observes this one
     * @param obs the observer class to add
     */
    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    /**
     * removes an observer
     * @param obs the observer to remove
     */
    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    /**
     * notifies the observers of the message to update them
     * @param message
     */
    protected void notifyObserver(Message message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}