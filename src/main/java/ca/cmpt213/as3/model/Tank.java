package ca.cmpt213.as3.model;

import java.util.List;
import java.util.Map;

/**
 * Tank class handles tank damage and identifiers.
 */
public class Tank {
    private int uniqueID;
    private List<Cell> tankCells;
    private static Map<Integer, Integer> tankHealthToDamage =
            Map.of(5, 20, 4, 20,
                    3, 5, 2, 2,
                    1, 1, 0, 0);

    Tank(List<Cell> tankCells, int uniqueID) {
        this.tankCells = tankCells;
        this.uniqueID = uniqueID;

        for (Cell cell: tankCells) {
            cell.setTank(this);
        }
    }

    public void takeDamage(Cell cell) {
        tankCells.remove(cell);
    }

    public int getDamage() {
        return tankHealthToDamage.get(tankCells.size());
    }

    public boolean isDestroyed() {
        return tankCells.size() == 0;
    }

    public int getUniqueID() {
        return uniqueID;
    }
}
