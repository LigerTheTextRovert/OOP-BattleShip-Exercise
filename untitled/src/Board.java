import java.util.Random;
import java.util.Scanner;

public class Board {
    private char[][] grid;
    private int gridSize;

    public Board() {
        this(10);
    }

    public Board(int size) {
        this.gridSize = size;
        this.grid = new char[size][size];
        initBoard();
    }

    public int getGridSize() {
        return this.gridSize;
    }

    public char[][] getGrid() {
        return this.grid;
    }

    private void initBoard() {
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

    public boolean isValidAttack(int row, int col) {
        // Check if row and col are within grid bounds
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            return false;
        }

        // Check if the cell is already attacked
        return grid[row][col] != 'X' && grid[row][col] != 'O';
    }

    public boolean isHit(int row, int col) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            return false; // Out-of-bounds means no hit
        }
        return grid[row][col] == 'S';
    }

    public void markHit(int row, int col) {
        grid[row][col] = 'X';
    }

    public void markMiss(int row, int col) {
        grid[row][col] = 'O';
    }

    public void placeShipsAutomatically() {
        int shipCount = gridSize / 2 + 1;
        int[] shipSizes = generateShipSizes(shipCount);
        Random random = new Random();

        for (int size : shipSizes) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(gridSize);
                int col = random.nextInt(gridSize);
                boolean horizontal = random.nextBoolean();

                if (canPlaceShip(row, col, size, horizontal)) {
                    placeShip(row, col, size, horizontal);
                    placed = true;
                }
            }
        }
    }

    public void placeShipsManually() {
        Scanner scanner = new Scanner(System.in);
        int shipCount = gridSize / 2 + 1;
        int[] shipSizes = generateShipSizes(shipCount);

        for (int size : shipSizes) {
            boolean placed = false;

            while (!placed) {
                System.out.println("Place a ship of size " + size);
                System.out.print("Enter starting position (e.g., B4): ");
                String input = scanner.next().toUpperCase();

                int[] coords = Game.parseInput(input, gridSize);
                if (coords == null) {
                    System.out.println("Invalid input! Use format like B4.");
                    continue;
                }

                System.out.print("Place horizontally? (yes/no): ");
                boolean horizontal = scanner.next().trim().equalsIgnoreCase("yes");

                if (canPlaceShip(coords[0], coords[1], size, horizontal)) {
                    placeShip(coords[0], coords[1], size, horizontal);
                    placed = true;
                } else {
                    System.out.println("Invalid placement! Ship overlaps or is out of bounds. Try again.");
                }
            }
        }
    }

    private int[] generateShipSizes(int shipCount) {
        int[] shipSizes = new int[shipCount];
        Random random = new Random();
        for (int i = 0; i < shipCount; i++) {
            //random.nextInt(n) generate an random integer between 0 and n-1
            shipSizes[i] = random.nextInt(4) + 2;
        }
        return shipSizes;
    }

    // Actually place the ship on the board
    public boolean placeShip(int row, int col, int size, boolean isHorizontal) {
        if (!canPlaceShip(row, col, size, isHorizontal)) {
            System.out.println("Cannot place ship at (" + row + ", " + col + "). It overlaps or is out of bounds.");
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (isHorizontal) {
                grid[row][col + i] = 'S';
            } else {
                grid[row + i][col] = 'S';
            }
        }
        return true;
    }

    public void printGrid() {

        // Print column headers
        System.out.print("  ");
        for (int i = 0; i < gridSize; i++) {
            System.out.printf("%2c ", 'A' + i);
        }
        System.out.println();

        // Print rows with proper alignment
        for (int i = 0; i < gridSize; i++) {
            System.out.printf("%2d ", i);
            for (int j = 0; j < gridSize; j++) {
                System.out.print(grid[i][j] + "  ");
            }
            System.out.println();
        }
    }

}
