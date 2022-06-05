package ca.cmpt213.as3.model;


public class Game {
    private final Fortress fortress = new Fortress();
    private final Field field = new Field(5);
    private boolean isGameWon = false;
    private boolean isGameLost = false;
    private boolean isCheat = false;

    public void userAttackAtCoordinate(Coordinate coordinate) {
        Cell target = field.getCell(coordinate);
        target.takeFireFromPlayer();

        if (target.getTank() != null && target.getTank().isDestroyed()) {
            field.getTanksOnField().removeTank(target.getTank());
        }

        fortress.takeDamageFromTanks(field.getTanksOnField().getTanks());

        if (fortress.isDestroyed()) {
            isGameLost = true;
            isCheat = true;
        }

        if (field.getTanksOnField().isTanksDestroyed()) {
            isGameWon = true;
            isCheat = true;
        }
    }

    public Fortress getFortress() {
        return fortress;
    }

    public Field getField() {
        return field;
    }

    public boolean isGameWon() {
        return isGameWon;
    }

    public boolean isGameLost() {
        return isGameLost;
    }

    public boolean isCheat() {
        return isCheat;
    }

    public void activateCheats() {
        isCheat = true;
    }
}
