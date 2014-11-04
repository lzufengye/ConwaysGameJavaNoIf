import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * User: fengye
 * Date: 10/31/14
 * Time: 3:25 PM
 */
public class LiveEnvironment {
    private CellList cells;
    Coordinate leftTop;
    Coordinate rightBottom;

    public LiveEnvironment(CellState[][] initialState) {
        cells = new CellList();

        for (int row = 0; row < initialState.length; row++) {
            for (int col = 0; col < initialState[row].length; col++) {
                cells.put(new Coordinate(row, col), new Cell(initialState[row][col]));
            }
        }

        leftTop = cells.getLeftTopCoordinate();
        rightBottom = cells.getRightBottomCoordinate();
    }

    public CellState[][] getCurrentCellsState() {
        CellState[][] currentCellsState = new CellState[height()][];
        Coordinate leftTop = cells.getLeftTopCoordinate();

        for (int row = 0; row < height(); row++) {
            currentCellsState[row] = new CellState[width()];
            for (int col = 0; col < width(); col++) {
                currentCellsState[row][col] = cells.get(new Coordinate(row + leftTop.getX() , col + leftTop.getY())) != null ?
                        cells.get(new Coordinate(row + leftTop.getX() , col + leftTop.getY())).getCurrentState() : CellState.DEAD;
            }
        }

        return currentCellsState;
    }

    public void tick() {
        expandEnvironment();
        CellList oldCells = recordOldCells();

        for (Coordinate coordinate : cells.keySet()) {
            cells.get(coordinate).getNextState(getNeighborsNum(oldCells, coordinate));
        }
    }

    private int width() {
        return cells.getRightBottomCoordinate().getY() - cells.getLeftTopCoordinate().getY() + 1;
    }

    private int height() {
        return cells.getRightBottomCoordinate().getX() - cells.getLeftTopCoordinate().getX() + 1;
    }

    private void expandEnvironment() {
        leftTop = cells.getLeftTopCoordinate();
        rightBottom = cells.getRightBottomCoordinate();

        HashMap<String, String> boundNeedExpand = boundExpand();
        Class selfClass = this.getClass();

        for(String bound : boundNeedExpand.keySet()) {
            try {
                selfClass.getMethod(boundNeedExpand.get(bound)).invoke(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void expandBottomLeftPoint() {
        cells.put(new Coordinate(rightBottom.getX() + 1, leftTop.getY() -1), new Cell(CellState.DEAD));
    }

    public void expandBottomRightPoint() {
        cells.put(new Coordinate(rightBottom.getX() + 1, rightBottom.getY() +1), new Cell(CellState.DEAD));
    }

    public void expandTopRightPoint() {
        cells.put(new Coordinate(rightBottom.getX() + 1, leftTop.getY() -1), new Cell(CellState.DEAD));
    }

    public void expandTopLeftPoint() {
        cells.put(new Coordinate(leftTop.getX() - 1, leftTop.getY() -1), new Cell(CellState.DEAD));
    }

    public void expandLeft() {
        for(int row = leftTop.getY(); row < rightBottom.getY(); row++) {
            cells.put(new Coordinate(row + 1, leftTop.getX() -1), new Cell(CellState.DEAD));
        }
    }

    public void expandRight() {
        for(int row = leftTop.getY(); row < rightBottom.getY(); row++) {
            cells.put(new Coordinate(row + 1, rightBottom.getX() +1), new Cell(CellState.DEAD));
        }
    }

    public void expandTop() {
        for(int col = leftTop.getX(); col < rightBottom.getX(); col++) {
            cells.put(new Coordinate(leftTop.getY()-1, col), new Cell(CellState.DEAD));
        }
    }

    public void expandBottom() {
        for(int col = leftTop.getX(); col < rightBottom.getX(); col++) {
            cells.put(new Coordinate(rightBottom.getY() + 1, col), new Cell(CellState.DEAD));
        }
    }

    public void noExpand() {

    }

    private CellList recordOldCells() {
        CellList oldCells = new CellList();

        for (Coordinate coordinate : cells.keySet()) {
            oldCells.put(coordinate, new Cell(cells.get(coordinate).getCurrentState()));
        }

        return oldCells;
    }

    private int getNeighborsNum(CellList oldCells, Coordinate coordinate) {
        int neighborsNum = 0;

        for (int rowIndex = coordinate.getX() - 1; rowIndex <= coordinate.getX() + 1; rowIndex++) {
            for (int colIndex = coordinate.getY() -1 ; colIndex <= coordinate.getY() + 1; colIndex++) {
                Coordinate coordinateIndex = new Coordinate(rowIndex, colIndex);
                neighborsNum += !coordinate.equals(coordinateIndex) &&
                                    oldCells.get(coordinateIndex) != null &&
                                    oldCells.get(coordinateIndex).isAlive() ? 1 : 0;
            }
        }
        return neighborsNum;
    }

    public HashMap<String, String> boundExpand() {
        Coordinate leftTop = cells.getLeftTopCoordinate();
        Coordinate rightBottom = cells.getRightBottomCoordinate();
        HashMap<String, String> boundExpanded = new HashMap<String, String>();

        Boolean topNeedExpand = boundNeedExpand(leftTop.getX(), leftTop.getX() + 1, leftTop.getY(), rightBottom.getY() + 1);
        Boolean bottomNeedExpand = boundNeedExpand(rightBottom.getX(), rightBottom.getX() + 1, leftTop.getY(), rightBottom.getY() + 1);
        Boolean leftNeedExpand = boundNeedExpand(leftTop.getX(), rightBottom.getX() + 1, leftTop.getY(), leftTop.getY() + 1);
        Boolean rightNeedExpand = boundNeedExpand(leftTop.getX(), rightBottom.getX() + 1, rightBottom.getY(), rightBottom.getY() + 1);

        boundExpanded.put("TOP", topNeedExpand ? "expandTop" : "noExpand");
        boundExpanded.put("BOTTOM", bottomNeedExpand ? "expandBottom" : "noExpand");
        boundExpanded.put("LEFT", leftNeedExpand ? "expandLeft" : "noExpand");
        boundExpanded.put("RIGHT", rightNeedExpand ? "expandRight" : "noExpand");

        boundExpanded.put("TOP_LEFT_POINT", topNeedExpand && leftNeedExpand ? "expandTopLeftPoint" : "noExpand");
        boundExpanded.put("BOTTOM_LEFT_POINT", bottomNeedExpand && leftNeedExpand ? "expandBottomLeftPoint" : "noExpand");
        boundExpanded.put("TOP_RIGHT_POINT", topNeedExpand && rightNeedExpand ? "expandTopRightPoint" : "noExpand");
        boundExpanded.put("BOTTOM_RIGHT_POINT", bottomNeedExpand && rightNeedExpand ? "expandBottomRightPoint" : "noExpand");

        return boundExpanded;
    }

    private Boolean boundNeedExpand(int rowStart, int rowEnd, int colStart, int colEnd) {
        String pattern = "XXX";
        String boundString = "";
        for(int row = rowStart; row < rowEnd; row++) {
            for(int col = colStart; col < colEnd; col++) {
                boundString += cells.get(new Coordinate(row, col)).isAlive() ? 'X' : 'O';
            }
        }
        return boundString.indexOf(pattern) > -1;
    }
}
