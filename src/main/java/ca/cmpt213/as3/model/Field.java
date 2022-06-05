package ca.cmpt213.as3.model;


import ca.cmpt213.as3.ui.ErrorUI;

import java.util.*;

/**
 * Field class handles cells and tanks. Initializes the cells
 * on the game-board and randomly places tanks.
 */
public class Field implements Iterable<Cell> {
    private static final int MAX_NUMBER_OF_CELLS = 5;
    public static final int ROW_LENGTH = 10;
    public static final int COLUMN_LENGTH = 10;

    private Cell[][] cells = new Cell[ROW_LENGTH][COLUMN_LENGTH];
    TankManager tanksOnField = new TankManager();

    public Field(int numberOfTanks) {
        initializeCellsInField();
        randomizeTanksOnField(numberOfTanks);
    }

    private void initializeCellsInField() {
        for (int row = 0; row < ROW_LENGTH; row++) {
            for (int col = 0; col < COLUMN_LENGTH; col++) {
                cells[row][col] = new Cell();
            }
        }
    }

    private void randomizeTanksOnField(int numberOfTanks) {
        if (numberOfTanks * MAX_NUMBER_OF_CELLS > (ROW_LENGTH * COLUMN_LENGTH)) {
            ErrorUI.printIncompatibleNumberOfTanks(numberOfTanks);
            System.exit(0);
        }

        for (int i = 1; i <= numberOfTanks; i++) {
            while(true) {
                Coordinate startingCoordinate = Coordinate.createRandomCoordinate();
                Cell cell = getCell(startingCoordinate);
                if (cell == null || !cell.isTank()) {
                    generateTank(i, startingCoordinate);
                    break;
                }
            }
        }
    }

    private void generateTank(int uniqueTankID, Coordinate startingCoordinate) {
        List<Cell> tankCells = new ArrayList<>();
        tankCells.add(getCell(startingCoordinate));

        Coordinate currentCoordinate = startingCoordinate;
        for (int i = 1; i < MAX_NUMBER_OF_CELLS; i++) {
            boolean isEmptyCellsFound = false;
            List<Coordinate.Direction> directions = Arrays.asList(Coordinate.Direction.values());
            Collections.shuffle(directions);

            for (Coordinate.Direction direction: directions) {
                Coordinate adjacentCoordinate = currentCoordinate.getAdjacentCoordinates(direction);
                Cell cell = getCell(adjacentCoordinate);
                if (cell != null && !tankCells.contains(cell) && !cell.isTank()) {
                    isEmptyCellsFound = true;
                    currentCoordinate = adjacentCoordinate;
                    tankCells.add(cell);
                    break;
                }
            }

            if (!isEmptyCellsFound) {
                ErrorUI.printTankPlacementError();
                System.exit(0);
            }
        }

        tanksOnField.addTank(new Tank(tankCells, uniqueTankID));
    }

    public Cell getCell(Coordinate coordinate) {
        int row = coordinate.getRowIndex();
        int column = coordinate.getColumnIndex();

        if (row >= ROW_LENGTH || row < 0 ||
            column >= COLUMN_LENGTH || column < 0) {
            return null;
        }

        return cells[row][column];
    }

    public Cell[][] getCells() {
        return cells;
    }

    public TankManager getTanksOnField() {
        return tanksOnField;
    }

    @Override
    public Iterator<Cell> iterator() {
            return new Iterator<Cell>() {
                int row = 0, col = 0;
                @Override
                public boolean hasNext() {
                    return (row < ROW_LENGTH) && (col < COLUMN_LENGTH);
                }
                @Override
                public Cell next() {
                    Cell item = cells[row][col++];
                    if (col >= COLUMN_LENGTH) {
                        col = 0;
                        row++;
                    }
                    return item;
                }
                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }

