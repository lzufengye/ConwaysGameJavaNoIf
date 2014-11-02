import java.util.HashMap;

/**
 * User: fengye
 * Date: 10/31/14
 * Time: 3:25 PM
 */
public class LiveEnvironment {
    private CellList cells;

    public LiveEnvironment(CellState[][] initialState) {
        cells = new CellList();

        for (int row = 0; row < initialState.length; row++) {
            for (int col = 0; col < initialState[row].length; col++) {
                cells.put(new Coordinate(row, col), new Cell(initialState[row][col]));
            }
        }
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
        Coordinate leftTop = cells.getLeftTopCoordinate();
        Coordinate rightBottom = cells.getRightBottomCoordinate();

        HashMap<String, Boolean> boundNeedExpand = boundExpand();


        expandTopAndBottomBound(leftTop, rightBottom, boundNeedExpand);
        expandLeftAndRightBound(leftTop, rightBottom, boundNeedExpand);
        expandLeftTopAndLeftRightPoint(leftTop, rightBottom, boundNeedExpand);
        expandBottomLeftAndBottomRightPoint(leftTop, rightBottom, boundNeedExpand);
    }

    private void expandBottomLeftAndBottomRightPoint(Coordinate leftTop, Coordinate rightBottom, HashMap<String, Boolean> boundNeedExpand) {
        if (boundNeedExpand.get("BOTTOM")) {
            if (boundNeedExpand.get("LEFT")) {
                cells.put(new Coordinate(rightBottom.getX() + 1, leftTop.getY() -1), new Cell(CellState.DEAD));
            }
            if (boundNeedExpand.get("RIGHT")) {
                cells.put(new Coordinate(rightBottom.getX() + 1, rightBottom.getY() +1), new Cell(CellState.DEAD));
            }
        }
    }

    private void expandLeftTopAndLeftRightPoint(Coordinate leftTop, Coordinate rightBottom, HashMap<String, Boolean> boundNeedExpand) {
        if (boundNeedExpand.get("TOP")) {
            if (boundNeedExpand.get("LEFT")) {
                cells.put(new Coordinate(leftTop.getX() - 1, leftTop.getY() -1), new Cell(CellState.DEAD));
            }
            if (boundNeedExpand.get("RIGHT")) {
                cells.put(new Coordinate(rightBottom.getX() + 1, leftTop.getY() -1), new Cell(CellState.DEAD));
            }
        }
    }

    private void expandLeftAndRightBound(Coordinate leftTop, Coordinate rightBottom, HashMap<String, Boolean> boundNeedExpand) {
        for(int row = leftTop.getY(); row < rightBottom.getY(); row++) {
            if(boundNeedExpand.get("LEFT")) cells.put(new Coordinate(row + 1, leftTop.getX() -1), new Cell(CellState.DEAD));
            if(boundNeedExpand.get("RIGHT")) cells.put(new Coordinate(row + 1, rightBottom.getX() +1), new Cell(CellState.DEAD));
        }
    }

    private void expandTopAndBottomBound(Coordinate leftTop, Coordinate rightBottom, HashMap<String, Boolean> boundNeedExpand) {
        for(int col = leftTop.getX(); col < rightBottom.getX(); col++) {
            if(boundNeedExpand.get("TOP")) cells.put(new Coordinate(leftTop.getY()-1, col), new Cell(CellState.DEAD));
            if(boundNeedExpand.get("BOTTOM")) cells.put(new Coordinate(rightBottom.getY() + 1, col), new Cell(CellState.DEAD));
        }
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

                if (oldCells.get(coordinateIndex) == null) continue;
                if (coordinate.equals(coordinateIndex)) continue;
                if (oldCells.get(coordinateIndex).isAlive()) {
                    neighborsNum++;
                }
            }
        }
        return neighborsNum;
    }

    public HashMap<String, Boolean> boundExpand() {
        Coordinate leftTop = cells.getLeftTopCoordinate();
        Coordinate rightBottom = cells.getRightBottomCoordinate();
        HashMap<String, Boolean> boundExpanded = new HashMap<String, Boolean>();
        boundExpanded.put("TOP", boundNeedExpand(leftTop.getX(), leftTop.getX() + 1, leftTop.getY(), rightBottom.getY() + 1));
        boundExpanded.put("BOTTOM", boundNeedExpand(rightBottom.getX(), rightBottom.getX() + 1, leftTop.getY(), rightBottom.getY() + 1));
        boundExpanded.put("LEFT", boundNeedExpand(leftTop.getX(), rightBottom.getX() + 1, leftTop.getY(), leftTop.getY() + 1));
        boundExpanded.put("RIGHT", boundNeedExpand(leftTop.getX(), rightBottom.getX() + 1, rightBottom.getY(), rightBottom.getY() + 1));

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
