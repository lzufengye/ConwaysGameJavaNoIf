import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: fengye
 * Date: 11/1/14
 * Time: 8:48 PM
 */
public class CellListTest {
    Cell cell1;
    Cell cell2;
    CellList cellList;

    @Before
    public void setUp() throws Exception {
        cell1 = new Cell(CellState.ALIVE);
        cell2 = new Cell(CellState.DEAD);

        cellList = new CellList();
    }

    @Test
    public void shouldReturnCellAccordingToXY() throws Exception {
        cellList.put(new Coordinate(1, 1), cell1);
        cellList.put(new Coordinate(1, 2), cell2);

        assertEquals(cell1, cellList.get(new Coordinate(1, 1)));
        assertEquals(cell2, cellList.get(new Coordinate(1, 2)));
    }

    @Test
    public void shouldReturnLeftTopCoordinate() throws Exception {
        Coordinate leftTop = new Coordinate(0, 0);
        Coordinate rightBottom = new Coordinate(5, 3);

        cellList.put(leftTop, cell1);
        cellList.put(rightBottom, cell2);

        assertEquals(leftTop, cellList.getLeftTopCoordinate());
    }


    @Test
    public void shouldReturnRightBottomCoordinate() throws Exception {
        Coordinate leftTop = new Coordinate(0, 0);
        Coordinate rightBottom = new Coordinate(5, 3);

        cellList.put(leftTop, cell1);
        cellList.put(rightBottom, cell2);

        assertEquals(rightBottom, cellList.getRightBottomCoordinate());
    }
}
