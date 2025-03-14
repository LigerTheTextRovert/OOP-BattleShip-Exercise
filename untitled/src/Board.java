public class Board {
    public int GRID_SIZE;
    private char[][] grid;
    private int gridSize;

    public Board() {
        this.grid = new char[10][10];
        initBoard();
    }

    public Board(int size) {
        this.GRID_SIZE = size;
        this.grid = new char[size][size];
    }

    public int getGridSize() {
        return this.gridSize;
    }

    public char[][] getGrid() {
        return this.grid;
    }

    private void initBoard() {
        this.gridSize = grid.length;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = '~';
            }
        }
    }

    // Check if a ship can be placed
    public boolean canPlaceShip(int row, int col, int size, boolean isHorizontal) {
        if (isHorizontal) {
            if (col + size > gridSize) return false;
            for (int i = 0; i < size; i++) {
                if (grid[row][col + i] != '~') return false;
            }
        } else {
            if (row + size > gridSize) return false;
            for (int i = 0; i < size; i++) {
                if (grid[row + i][col] != '~') return false;
            }
        }
        return true;
    }

    // Actually place the ship on the board
    public boolean placeShip(int row, int col, int size, boolean isHorizontal) {
        if (!canPlaceShip(row, col, size, isHorizontal)) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (isHorizontal) {
                grid[row][col + i] = 'S'; // Mark ship on board
            } else {
                grid[row + i][col] = 'S';
            }
        }
        return true;
    }

    public void printGrid() {

        // Print column headers
        System.out.print("  "); // Initial space for row numbers
        for (int i = 0; i < gridSize; i++) {
            System.out.printf("%2c ", 'A' + i); // Adjust spacing for alignment
        }
        System.out.println();

        // Print rows with proper alignment
        for (int i = 0; i < gridSize; i++) {
            System.out.printf("%2d ", i); // Ensures proper alignment for row numbers
            for (int j = 0; j < gridSize; j++) {
                System.out.print(grid[i][j] + "  "); // Consistent spacing for grid elements
            }
            System.out.println();
        }
    }

}
