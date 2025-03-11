public class Utils {

    static void printGrid(Board board) {
        char[][] grid  = board.getGrid();
        int size = board.getGridSize();

        System.out.print("  ");
        for (char c = 'A'; c < 'K'; c++) {
            System.out.print(" " + c);
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            System.out.print((i) + "  ");
            for (int j = 0; j < size; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}
