import java.util.Scanner;

public class Coordinate {
    private int row;
    private int col;  // Stored as an integer (0-based)

    // Constructor
    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Parses a coordinate string like "A5" into row and column
    public static Coordinate fromString(String input, int gridSize) {
        if (input == null || input.length() < 2) {
            throw new IllegalArgumentException("Invalid coordinate format.");
        }

        char columnChar = Character.toUpperCase(input.charAt(0));  // Get column letter (A-J)
        int row;

        try {
            row = Integer.parseInt(input.substring(1));  // Extract row number
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid row number.");
        }

        int col = columnChar - 'A';  // Convert 'A' to 0, 'B' to 1, etc.

        if (!isValid(row, col, gridSize)) {
            throw new IllegalArgumentException("Coordinate out of bounds.");
        }

        return new Coordinate(row, col);
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

    // Converts back to a string (e.g., "A5")
    public String toString() {
        return (char) ('A' + col) + Integer.toString(row);
    }
}
