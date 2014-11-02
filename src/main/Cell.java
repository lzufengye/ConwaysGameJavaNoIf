/**
 * User: fengye
 * Date: 10/31/14
 * Time: 2:37 PM
 */
public class Cell {

    private CellState currentState;

    public Cell(CellState state) {
        currentState = state;
    }

    public CellState getCurrentState() {
        return currentState;
    }

    public CellState getNextState(int neighborNum) {
        return currentState = (neighborNum == 2 && this.isAlive()) || neighborNum == 3 ?
                CellState.ALIVE : CellState.DEAD;
    }

    public boolean isAlive() {
        return currentState == CellState.ALIVE;
    }
}

enum CellState {
    ALIVE, DEAD
}
