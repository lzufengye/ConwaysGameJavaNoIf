import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: fengye
 * Date: 11/1/14
 * Time: 8:34 PM
 */
public class CoordinateTest {
    @Test
    public void shouldEqualToSelf() throws Exception {
        Coordinate coordinate = new Coordinate(1, 1);

        assertEquals(true, coordinate.equals(coordinate));
    }

    @Test
    public void shouldReturnFalseIfXOrYNotTheSame() throws Exception {
        Coordinate coordinate1 = new Coordinate(1,1);
        Coordinate coordinate2 = new Coordinate(1,2);

        assertEquals(false, coordinate1.equals(coordinate2));
    }

    @Test
    public void shouldReturnTrueIfXYAreTheSame() throws Exception {
        Coordinate coordinate1 = new Coordinate(1,1);
        Coordinate coordinate2 = new Coordinate(1,1);

        assertEquals(true, coordinate1.equals(coordinate2));
    }

    @Test
    public void shouldGetMinusOneIfLittleThan() throws Exception {
        assertEquals(-1, new Coordinate(1,1).compareTo(new Coordinate(1,2)));
        assertEquals(-1, new Coordinate(1,1).compareTo(new Coordinate(2,1)));
    }

    @Test
    public void shouldGetZeroIfEquals() throws Exception {
        assertEquals(0, new Coordinate(1,1).compareTo(new Coordinate(1,1)));
    }

    @Test
    public void shouldGetOneIfBiggerThan() throws Exception {
        assertEquals(1, new Coordinate(1,1).compareTo(new Coordinate(1,0)));
        assertEquals(1, new Coordinate(1,1).compareTo(new Coordinate(0,1)));
    }
}
