package ca.cmpt213.as3.wrapper;

import ca.cmpt213.as3.model.Fortress;
import ca.cmpt213.as3.model.Game;
import ca.cmpt213.as3.model.TankManager;

/**
 * Wrapper class for the REST API to define object structures required by the front-end.
 * HINT: Create static factory methods (or constructors) which help create this object
 *       from the data stored in the model, or required by the model.
 */
public class ApiGameWrapper {
    public int gameNumber;
    public boolean isGameWon;
    public boolean isGameLost;
    public int fortressHealth;
    public int numTanksAlive;

    // Amount of damage that the tanks did on the last time they fired.
    // If tanks have not yet fired, then it should be an empty array (0 size).
    public int[] lastTankDamages;

    public ApiGameWrapper(int gameNumber, boolean isGameWon, boolean isGameLost, int fortressHealth, int numTanksAlive, int[] lastTankDamages) {
        this.gameNumber = gameNumber;
        this.isGameWon = isGameWon;
        this.isGameLost = isGameLost;
        this.fortressHealth = fortressHealth;
        this.numTanksAlive = numTanksAlive;
        this.lastTankDamages = lastTankDamages;
    }

    public static ApiGameWrapper createApiGameWrapper(int gameNumber, Game game) {
        boolean isGameWon = game.isGameWon();
        boolean isGameLost = game.isGameLost();

        int fortressHealth = game.getFortress().getFortressHealth();

        TankManager tanksOnField = game.getField().getTanksOnField();
        int numTanksAlive = tanksOnField.getNumberOfTanks();
        int[] lastTankDamages = new int[numTanksAlive];
        if (fortressHealth != Fortress.STARTING_HEALTH) {
            for (int i = 0; i < numTanksAlive; i++) {
                lastTankDamages[i] = tanksOnField.getTank(i).getDamage();
                System.out.println(lastTankDamages[i]);

            }
        }

        return new ApiGameWrapper(gameNumber, isGameWon, isGameLost, fortressHealth, numTanksAlive, lastTankDamages);
    }
}
