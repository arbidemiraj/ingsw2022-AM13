package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Observable class that can be inherited by the view class that needs to be observed
 */
public abstract class ViewObservable {

    protected final List<ViewObserver> observers = new ArrayList<>();

    /**
     * adds an observer
     * @param obs the observer to add
     */
    public void addObserver(ViewObserver obs) {
        observers.add(obs);
    }

    /**
     * Adds a list of observer
     * @param observerList the list of observers to add
     */
    public void addAllObservers(List<ViewObserver> observerList) {
        observers.addAll(observerList);
    }


    public void removeObserver(ViewObserver obs) {
        observers.remove(obs);
    }


    public void removeAllObservers(List<ViewObserver> observerList) {
        observers.removeAll(observerList);
    }

    /**
     * notifies all the observers using a lambda argument
     * @param lambda the lambda called by the observers
     */
    protected void notifyObserver(Consumer<ViewObserver> lambda) {
        for (ViewObserver observer : observers) {
            lambda.accept(observer);
        }
    }
}
