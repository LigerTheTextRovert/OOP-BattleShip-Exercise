import java.util.Random;
import java.util.Scanner;

public class Board {
    private char[][] grid;
    private int gridSize;

    public Board() {
        this.grid = new char[10][10];
        initBaord();
    }

    public Board(int size) {
        this.grid = new char[size][size];
    }

    public int getGridSize() {
        return this.gridSize;
    }

    public char[][] getGrid() {
        return this.grid;
    }

    private void initBaord() {
        this.gridSize = grid.length;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = '~';
            }
        }
    }

    public boolean canPlaceShip(int row, int col, int size, boolean isHorizontal) {
        if (isHorizontal) {
            if (col + size > gridSize) return false;
            for (int i = 0; i < size; i++) {
                if (grid[row][col + i] != '~') return false;
            }
        }
        if (!isHorizontal) {
            if (row + size > gridSize) return false;
            for (int i = 0; i < size; i++) {
                if (grid[row + i][col] != '~') return false;
            }
        }

        return true;
    }

    public void placeShip(int row, int col, int size, boolean horizontal) {
        if (horizontal) {
            if (col + size > gridSize) {
                throw new IllegalArgumentException("Ship goes out of bounds horizontally.");
            }
            for (int i = 0; i < size; i++) {
                if (grid[row][col + i] != '~') {
                    throw new IllegalArgumentException("Ship placement overlaps with another ship.");
                }
            }
            for (int i = 0; i < size; i++) {
                grid[row][col + i] = 'S';
            }
        } else {
            if (row + size > gridSize) {
                throw new IllegalArgumentException("Ship goes out of bounds vertically.");
            }
            for (int i = 0; i < size; i++) {
                if (grid[row + i][col] != '~') {
                    throw new IllegalArgumentException("Ship placement overlaps with another ship.");
                }
            }
            for (int i = 0; i < size; i++) {
                grid[row + i][col] = 'S';
            }
        }
    }


    public void placeShipsManually() {
        int[] shipSizes = {2, 3, 4, 5};  // Ship sizes
        Scanner scanner = new Scanner(System.in);
        for (int size : shipSizes) {
            boolean isTaken = false;
            while (!isTaken) {
                System.out.println("Enter coordinates for a ship of size " + size + ":");
                System.out.print("Enter starting row (0 to " + (gridSize - 1) + "): ");
                int row = scanner.nextInt();

                System.out.print("Enter starting column (0 to " + (gridSize - 1) + "): ");
                int col = scanner.nextInt();

                System.out.print("Enter orientation (H for horizontal, V for vertical): ");
                char orientation = scanner.next().charAt(0);
                boolean isHorizontal = (orientation == 'H' || orientation == 'h');

                // Validate and place the ship
                if (canPlaceShip(row, col, size, isHorizontal)) {
                    placeShip(row, col, size, isHorizontal);
                    System.out.println("Ship placed successfully!");
                    isTaken = true;  // Ship successfully placed
                } else {
                    System.out.println("Invalid placement. Try again.");
                }
            }
        }
    }

    public void placeShipsRandomly() {
        int[] shipSize = {2, 3, 4, 5};
        Random random = new Random();

        for (int size : shipSize) {
            boolean isTaken = false;

            while (!isTaken) {
                int row = random.nextInt(gridSize);
                int col = random.nextInt(gridSize);
                boolean horizontal = random.nextBoolean();

                if (canPlaceShip(row, col, size, horizontal)) {
                    placeShip(row, col, size, horizontal);
                    isTaken = true;
                }
            }
        }
    }

    public boolean allShipSunk() {
        int sunkShips = 0;

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (grid[i][j] == 'X') sunkShips++;
            }
        }
        return sunkShips == 14;
    }
}
