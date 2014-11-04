import java.util.HashMap;

/**
 * User: fengye
 * Date: 11/1/14
 * Time: 8:53 PM
 */
public class CellList extends HashMap<Coordinate, Cell> {
    public Coordinate getLeftTopCoordinate() {
        Coordinate leftTop = new Coordinate(0, 0);
        for (Coordinate coordinate : this.keySet()) {
            leftTop = coordinate.compareTo(leftTop) == -1 ? coordinate : leftTop;
        };
        return leftTop;
    }

    public Coordinate getRightBottomCoordinate() {
        Coordinate rightBottom = new Coordinate(0, 0);
        for (Coordinate coordinate : this.keySet()) {
            rightBottom = coordinate.compareTo(rightBottom) == 1 ? coordinate : rightBottom;
        };
        return rightBottom;
    }
}
