package ca.cmpt213.as3.model;

/**
 * Cell class handles each cell on the field. Stores
 * the state of the cell and its state accordingly.
 */
public class Cell {
    public enum State {
        HIDDEN,
        MISS,
        HIT
    }

    private Tank tank;
    private State state;

    public Cell() {
        this.tank = null;
        this.state = State.HIDDEN;
    }

    public void takeFireFromPlayer() {
        if (this.isTank()) {
            state = State.HIT;
            tank.takeDamage(this);
        } else {
            state = State.MISS;
        }
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    public boolean isTank() {
        if (tank == null) {
            return false;
        }
        return true;
    }

    public State getState() {
        return state;
    }

    public Tank getTank() {
        return tank;
    }

    @Override
    public String toString() {
        return state.name().toString();
    }
}
