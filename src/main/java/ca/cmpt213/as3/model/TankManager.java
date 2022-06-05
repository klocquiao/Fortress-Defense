package ca.cmpt213.as3.model;

import java.util.ArrayList;
import java.util.List;

public class TankManager {
    private List<Tank> tanks = new ArrayList<>();
    private List<Observer> observers = new ArrayList<Observer>();

    public List<Tank> getTanks() {
        return tanks;
    }

    public Tank getTank(int index) {
        return tanks.get(index);
    }

    public int getNumberOfTanks() {
        return tanks.size();
    }

    public void addTank(Tank tank) {
        tanks.add(tank);
    }

    public void removeTank(Tank tank) {
        tanks.remove(tank);

        if (tanks.size() == 0) {
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
