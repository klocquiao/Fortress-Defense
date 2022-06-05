package ca.cmpt213.as3.model;

import java.util.Random;

import static ca.cmpt213.as3.model.Field.COLUMN_LENGTH;
import static ca.cmpt213.as3.model.Field.ROW_LENGTH;

/**
 * Helper method for handling user inputs for accessing
 * cells on the field, as well as Field operations.
 */
public class Coordinate {
    public enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    private int rowIndex;
    private int columnIndex;

    public Coordinate(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public Coordinate(char[] input) {
        StringBuilder numStr = new StringBuilder();
        for (char ch: input) {
            if (Character.isLetter(ch)) {
                this.rowIndex = convertLetterToNumber(ch);
            } else {
                numStr.append(ch);
            }
        }
        this.columnIndex = Integer.parseInt(numStr.toString()) - 1;
    }
    public static Coordinate createRandomCoordinate() {
        Random random = new Random();
        int rowIndex = random.nextInt(ROW_LENGTH);
        int columnIndex = random.nextInt(COLUMN_LENGTH);

        return new Coordinate(rowIndex, columnIndex);
    }

    public Coordinate getAdjacentCoordinates(Direction direction) {
        switch (direction) {
            case UP:
                return new Coordinate(rowIndex, columnIndex - 1);
            case RIGHT:
                return new Coordinate(rowIndex + 1, columnIndex);
            case DOWN:
                return new Coordinate(rowIndex, columnIndex + 1);
            case LEFT:
                return new Coordinate(rowIndex - 1, columnIndex);
            default:
                throw new UnsupportedOperationException("Unsupported Operation");
        }
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public boolean isInBound() {
        if (rowIndex < ROW_LENGTH && rowIndex >= 0 &&
                columnIndex < COLUMN_LENGTH && columnIndex >= 0) {
            return true;
        }

        return false;
    }

    private int convertLetterToNumber(char letter) {
        letter = Character.toUpperCase(letter);
        return letter - 'A';
    }
}
