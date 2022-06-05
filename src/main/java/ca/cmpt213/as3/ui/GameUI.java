package ca.cmpt213.as3.ui;

import ca.cmpt213.as3.model.Cell;
import ca.cmpt213.as3.model.Field;
import ca.cmpt213.as3.model.Fortress;
import ca.cmpt213.as3.model.Tank;

import java.util.List;

/**
 * GameUI class handles all text-related outputs. Prints game board and --cheat
 * game-board, fortress taking damage, and fortress health left. Prints end-game
 * answers and cheat calls.
 */
public class GameUI {
    public static void printWelcomeMessage(int numberOfTanks) {
        System.out.println("----------------------------");
        System.out.println("WELCOME TO FORTRESS DEFENSE!");
        System.out.println("----------------------------");
        System.out.println("Starting the game with " + numberOfTanks + " tanks...");
    }

    public static void printGameBoard(Field field, boolean isReveal) {
        System.out.println("\nGame Board: ");
        for (int i = 1; i <= Field.COLUMN_LENGTH; i++) {
            System.out.printf("   %d", i);
        }
        System.out.println();

        int counter = 0;
        int row = 1;
        for (Cell cell : field) {
            if (counter % 10 == 0) {
                System.out.printf("%s  ", numberToLetter(row));
                row++;
            }
            char symbol = translateStateToSymbol(cell, isReveal);
            System.out.printf("%s   ", symbol);
            counter++;
            if (counter % 10 == 0 && counter != 0) {
                System.out.println();
            }
        }
    }

    public static void printFortressHealth(Fortress fortress) {
        System.out.println("Fortress Structure Left: " + fortress.getFortressHealth());
    }

    public static void printUserCoordinatePrompt() {
        System.out.println("Enter your move: ");
    }

    public static void printCellFiringResults(Cell target) {
        if (target.getState() == Cell.State.HIT) {
            System.out.println("Hit!");
        } else {
            System.out.println("Miss!");
        }
    }

    public static void printFortressFiringResults(List<Tank> tanks) {
        for (Tank tank: tanks) {
            System.out.println("Alive tank #" + tank.getUniqueID() + " of " + tanks.size() +
                " shot you for " + tank.getDamage());
        }
    }

    public static void printWinningMessage(Field field, Fortress fortress) {
        System.out.println("Congratulations! You won!");
        printGameBoard(field, true);
        printFortressHealth(fortress);
        System.out.println("(Lowercase letters are where you shot.)");
    }

    public static void printLosingMessage(Field field, Fortress fortress) {
        System.out.println("Uh oh! You lost!");
        printGameBoard(field, true);
        printFortressHealth(fortress);
        System.out.println("(Lower case letters are where you shot.");
    }

    private static char translateStateToSymbol(Cell cell, boolean isCheat) {
        Cell.State state = cell.getState();
        if (isCheat) {
            return getRevealSymbol(cell);
        } else {
            return switch (state) {
                case HIDDEN -> '~';
                case MISS -> ' ';
                case HIT -> 'X';
            };
        }
    }

    private static char getRevealSymbol(Cell cell) {
        if (!cell.isTank()) {
            return '.';
        } else {
            char tankIDSymbol = numberToLetter(cell.getTank().getUniqueID());
            if (cell.getState() == Cell.State.HIT) {
                return Character.toLowerCase(tankIDSymbol);
            } else {
                return tankIDSymbol;
            }
        }
    }

    private static char numberToLetter(int num) {
        return (char)(num + 'A' - 1);
    }
}
