import java.util.Scanner;

public class Coordinate {
    private int row;
    private int col;  // Stored as an integer (0-based)

    // Constructor
    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Validates whether the coordinate is within grid boundaries
    public static boolean isValid(int row, int col, int gridSize) {
        return row >= 0 && row < gridSize && col >= 0 && col < gridSize;
    }

    // Getters
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
