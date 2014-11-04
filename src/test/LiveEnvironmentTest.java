import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * User: fengye
 * Date: 10/31/14
 * Time: 3:17 PM
 */
public class LiveEnvironmentTest {
    private static final CellState O = CellState.DEAD;
    private static final CellState X = CellState.ALIVE;

    @Test
    public void shouldGetCurrentCellsState() throws Exception {
        CellState[][] initialState = {
                {O, X, O},
                {O, X, O},
                {O, X, O}
        };
        LiveEnvironment liveEnvironment = new LiveEnvironment(initialState);

        assertArrayEquals(initialState, liveEnvironment.getCurrentCellsState());
    }

    @Test
    public void shouldGetNextCellsStateAfterTick() throws Exception {
        CellState[][] initialState = {
                {O, X, O},
                {O, X, O},
                {O, X, O}
        };
        LiveEnvironment liveEnvironment = new LiveEnvironment(initialState);
        liveEnvironment.tick();

        CellState[][] expectedState = {
                {O, O ,O},
                {X, X, X},
                {O, O, O}
        };

        assertArrayEquals(expectedState, liveEnvironment.getCurrentCellsState());
    }

    @Test
    public void shouldGetNextCellsStateAfterTickConsideringInfiniteBounds() throws Exception {
        CellState[][] initialState = {
                {X, X, X},
                {X, O, X},
                {X, X, X}
        };
        LiveEnvironment liveEnvironment = new LiveEnvironment(initialState);
        liveEnvironment.tick();

        CellState[][] expectedState = {
                {O, O, X, O, O},
                {O, X, O, X, O},
                {X, O, O, O, X},
                {O, X, O, X, O},
                {O, O, X, O, O}
        };

        assertArrayEquals(expectedState, liveEnvironment.getCurrentCellsState());
    }

    @Test
    public void shouldGetNextCellsStateAfterTickWithMoreCells() throws Exception {
        CellState[][] initialState = {
                {O, O, X, O, O},
                {O, X, O, X, O},
                {X, O, O, O, X},
                {O, X, O, X, O},
                {O, O, X, O, O}
        };

        LiveEnvironment liveEnvironment = new LiveEnvironment(initialState);
        liveEnvironment.tick();

        CellState[][] expectedState = {
                {O, O, X, O, O},
                {O, X, X, X, O},
                {X, X, O, X, X},
                {O, X, X, X, O},
                {O, O, X, O, O}
        };
        assertArrayEquals(expectedState, liveEnvironment.getCurrentCellsState());
    }
}
