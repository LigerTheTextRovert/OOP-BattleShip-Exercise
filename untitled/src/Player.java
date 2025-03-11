public class Player {
    private Board grid = new Board();
    private Board trackGrid = new Board();

    public boolean allShipSunk(Board board) {
        int size = board.getGridSize();
        char[][] grid = board.getGrid();
        int sunkShips = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == 'X') sunkShips++;
            }
        }
        return sunkShips == 14;
    }
}
