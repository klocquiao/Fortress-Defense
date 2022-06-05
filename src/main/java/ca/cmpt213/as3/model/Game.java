package ca.cmpt213.as3.model;


public class Game {
    private final Fortress fortress = new Fortress();
    private final Field field = new Field(5);
    private boolean isGameWon = false;
    private boolean isGameLost = false;
    private boolean isCheat = false;

    public Game() {
        fortress.addObserver(new Observer() {
            @Override
            public void getResults() {
                isGameLost = true;
                isCheat = true;
            }
        });

        field.getTanksOnField().addObserver(new Observer() {
            @Override
            public void getResults() {
                isGameWon = true;
                isCheat = true;
            }
        });
    }

    public void userAttackAtCoordinate(Coordinate coordinate) {
        Cell target = field.getCell(coordinate);
        target.takeFireFromPlayer();

        if (target.getTank() != null && target.getTank().isDestroyed()) {
            field.getTanksOnField().removeTank(target.getTank());
        }

        fortress.takeDamageFromTanks(field.getTanksOnField().getTanks());
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
