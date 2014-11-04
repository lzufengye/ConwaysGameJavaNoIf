import static java.lang.Thread.sleep;

/**
 * User: fengye
 * Date: 11/4/14
 * Time: 5:57 PM
 */
public class Game {
    private static final CellState O = CellState.DEAD;
    private static final CellState X = CellState.ALIVE;

    public static void main(String args[]) {
        CellState[][] initialState = {
                {X, X, X, O, X},
                {X, O, X, O, X},
                {X, X, X, O, X},
                {O, O, O, O, X},
                {X, X, X, O, X},
                {O, O, O, X, O}
        };
        LiveEnvironment liveEnvironment = new LiveEnvironment(initialState);
        printEnvironment(liveEnvironment);
        while(true) {
            try {
                liveEnvironment.tick();
                sleep(1000);
                printEnvironment(liveEnvironment);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static void printEnvironment(LiveEnvironment liveEnvironment) {
        CellState[][] environmentState = liveEnvironment.getCurrentCellsState();

        for(int row = 0; row < environmentState.length; row ++) {
            for(int col = 0; col < environmentState[row].length; col++) {
                System.out.print((environmentState[row][col] == CellState.ALIVE) ? 'X' : ' ');
                System.out.print(' ');
            }
            System.out.println();
        }
        System.out.println();
    }
}
