import java.util.HashMap;

/**
 * User: fengye
 * Date: 10/31/14
 * Time: 2:37 PM
 */
public class Cell {

    private CellState currentState;

    private static final HashMap<Boolean, CellState> stateQueryTable = new HashMap<Boolean, CellState>();

    static {
        stateQueryTable.put(true, CellState.ALIVE);
        stateQueryTable.put(false, CellState.DEAD);
    }

    public Cell(CellState state) {
        currentState = state;
    }

    public CellState getCurrentState() {
        return currentState;
    }

    public CellState getNextState(int neighborNum) {
        return currentState = stateQueryTable.get((neighborNum == 2 && this.isAlive()) || neighborNum == 3);
    }

    public boolean isAlive() {
        return currentState == CellState.ALIVE;
    }

}

enum CellState {
    ALIVE, DEAD
}
