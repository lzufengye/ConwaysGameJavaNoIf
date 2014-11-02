import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: fengye
 * Date: 10/31/14
 * Time: 2:29 PM
 */
public class CellTest {

    @Test
    public void shouldGetCurrentState() throws Exception {
        Cell cell = new Cell(CellState.ALIVE);

        assertEquals(CellState.ALIVE, cell.getCurrentState());
    }

    @Test
    public void shouldGetTureIfAliveOrFalseIfDead() throws Exception {
        Cell aliveCell = new Cell(CellState.ALIVE);
        Cell deadCell = new Cell(CellState.DEAD);

        assertEquals(true, aliveCell.isAlive());
        assertEquals(false, deadCell.isAlive());
    }

    @Test
    public void shouldDieWithLessThanTwoNeighborsWhenAlive() throws Exception {
        Cell cell = new Cell(CellState.ALIVE);

        assertEquals(CellState.DEAD, cell.getNextState(0));
        assertEquals(CellState.DEAD, cell.getNextState(1));
    }

    @Test
    public void shouldAliveWithTwoOrThreeNeighborsWhenAlive() throws Exception {
        Cell cell = new Cell((CellState.ALIVE));

        assertEquals(CellState.ALIVE, cell.getNextState(2));
        assertEquals(CellState.ALIVE, cell.getNextState(3));
    }

    @Test
    public void shouldDieWithMoreThanThreeNeighborsWhenAlive() throws Exception {
        Cell cell = new Cell(CellState.ALIVE);

        assertEquals(CellState.DEAD, cell.getNextState(4));
        assertEquals(CellState.DEAD, cell.getNextState(5));
        assertEquals(CellState.DEAD, cell.getNextState(6));
        assertEquals(CellState.DEAD, cell.getNextState(7));
        assertEquals(CellState.DEAD, cell.getNextState(8));
    }

    @Test
    public void shouldAliveWithThreeNeighborsWhenDead() throws Exception {
        Cell cell = new Cell(CellState.DEAD);

        assertEquals(CellState.ALIVE, cell.getNextState(3));
    }

    @Test
    public void shouldDieWithoutNeighborsWhenDead() throws Exception {
        Cell cell = new Cell(CellState.DEAD);

        assertEquals(CellState.DEAD, cell.getNextState(0));
        assertEquals(CellState.DEAD, cell.getNextState(1));
        assertEquals(CellState.DEAD, cell.getNextState(2));
        assertEquals(CellState.DEAD, cell.getNextState(4));
        assertEquals(CellState.DEAD, cell.getNextState(5));
        assertEquals(CellState.DEAD, cell.getNextState(6));
        assertEquals(CellState.DEAD, cell.getNextState(7));
        assertEquals(CellState.DEAD, cell.getNextState(8));
    }
}