import java.util.Arrays;

/**
 * User: fengye
 * Date: 11/1/14
 * Time: 8:39 PM
 */
public class Coordinate{
    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (x != that.x) return false;
        if (y != that.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Integer[]{x, y});
    }

    public int compareTo(Coordinate that) {
        return x < that.x ? -1 : x > that.x ? 1 : (y < that.y ? -1 : (y == that.y ? 0 : 1));
    }
}
