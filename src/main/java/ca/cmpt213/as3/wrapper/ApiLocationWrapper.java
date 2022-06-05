package ca.cmpt213.as3.wrapper;

import ca.cmpt213.as3.model.Field;
import ca.cmpt213.as3.model.Game;

/**
 * Wrapper class for the REST API to define object structures required by the front-end.
 * HINT: Create static factory methods (or constructors) which help create this object
 *       from the data stored in the model, or required by the model.
 */
public class ApiLocationWrapper {
    public int row;
    public int col;

    public ApiLocationWrapper(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
