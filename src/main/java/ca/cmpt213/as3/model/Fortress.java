package ca.cmpt213.as3.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Fortress class contains fortress health value. Returns updated fortress health
 * for each turn. Calculates total damage to fortressHealth each turn.
 */
public class Fortress {
    public static final int STARTING_HEALTH = 2500;
    private int fortressHealth;

    public Fortress() {
        this.fortressHealth = STARTING_HEALTH;
    }
    private List<Observer> observers = new ArrayList<Observer>();

    public int getFortressHealth() {
        if (fortressHealth > 0) {
            return fortressHealth;
        }
        return 0;
    }

    public void takeDamageFromTanks(List<Tank> tanks) {
        for (Tank tank: tanks) {
            fortressHealth -= tank.getDamage();
        }

        if (fortressHealth <= 0) {
            notifyAllObservers();
        }
    }

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.getResults();
        }
    }
}
