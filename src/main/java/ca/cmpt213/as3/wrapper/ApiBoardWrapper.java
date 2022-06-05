package ca.cmpt213.as3.wrapper;

import ca.cmpt213.as3.model.Cell;
import ca.cmpt213.as3.model.Cell.State;
import ca.cmpt213.as3.model.Field;
import ca.cmpt213.as3.model.Game;

import static ca.cmpt213.as3.model.Cell.State.*;

/**
 * Wrapper class for the REST API to define object structures required by the front-end.
 * HINT: Create static factory methods (or constructors) which help create this object
 *       from the data stored in the model, or required by the model.
 */
public class ApiBoardWrapper {
    public int boardWidth;
    public int boardHeight;
    public String[][] cellStates;

    public ApiBoardWrapper(int boardWidth, int boardHeight, String[][] cellStates) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.cellStates = cellStates;
    }

    public static ApiBoardWrapper createApiBoardWrapper(Game game) {
        boolean isCheat = game.isCheat();
        int boardWidth = Field.ROW_LENGTH;
        int boardHeight = Field.COLUMN_LENGTH;
        Cell[][] cells = game.getField().getCells();

        String[][] cellStates = new String[boardWidth][boardHeight];

        for (int x = 0; x < boardWidth; x++) {
            for (int y = 0; y < boardHeight; y++) {
                Cell currentCell = cells[x][y];
                cellStates[x][y] = "fog";

                if (currentCell.getState() == HIDDEN && isCheat) {
                    if (currentCell.isTank()) {
                        cellStates[x][y] = "tank";
                    }
                    else {
                        cellStates[x][y] = "field";
                    }
                }

                switch (currentCell.getState()) {
                    case HIT -> cellStates[x][y] = "hit";
                    case MISS -> cellStates[x][y] = "miss";
                }
            }
        }

        return new ApiBoardWrapper(boardWidth, boardHeight, cellStates);
    }
}
