import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Game {
    private boolean isAI;
    private int gridSize;
    private Player player1;
    private Player player2;
    private Scanner scanner;
    private Set<String> aiMoves; // Tracks AI's previous moves

    public Game() {
        scanner = new Scanner(System.in);
        aiMoves = new HashSet<>();
        setupGame();
    }

    public void start() {
        boolean playAgain;
        do {
            playGame();
            playAgain = askReplay();
        } while (playAgain);
    }

    private boolean askReplay() {
        System.out.print("Play again? (yes/no): ");
        return scanner.next().trim().equalsIgnoreCase("yes");
    }

    private void setupGame() {
        System.out.print("Enter the grid size (e.g., 10 for a 10x10 board): ");
        gridSize = scanner.nextInt();

        System.out.print("Do you want to play against AI? (Yes/No): ");
        isAI = scanner.next().trim().equalsIgnoreCase("yes");

        System.out.print("Player One, enter your name: ");
        String name1 = scanner.next();
        player1 = new Player(name1, gridSize, false);

        String name2 = isAI ? "AI" : getPlayerName("Player Two");
        player2 = new Player(name2, gridSize, isAI);

        System.out.print(name1 + ", do you want to place your ships manually? (yes/no): ");
        boolean manual1 = scanner.next().trim().equalsIgnoreCase("yes");
        if (manual1) {
            player1.getGrid().placeShipsManually();
        } else {
            player1.getGrid().placeShipsAutomatically();
        }

        if (!isAI) {
            System.out.print(name2 + ", do you want to place your ships manually? (yes/no): ");
            boolean manual2 = scanner.next().trim().equalsIgnoreCase("yes");
            if (manual2) {
                player2.getGrid().placeShipsManually();
            } else {
                player2.getGrid().placeShipsAutomatically();
            }
        } else {
            player2.getGrid().placeShipsAutomatically(); // AI places ships automatically
        }

    }

    public void playGame() {
        player1.resetGrid();
        player2.resetGrid();

        boolean player1Turn = true;
        while (!isGameOver()) {
            Player currentPlayer = player1Turn ? player1 : player2;
            Player opponent = player1Turn ? player2 : player1;

            System.out.println("\n" + currentPlayer.getName() + "'s turn:");
            currentPlayer.getTrackingGrid().printGrid();

            String input;
            if (currentPlayer.isAI()) {
                input = makeMoveForAI(); // AI move
                System.out.println("AI attacks at: " + input);
                int[] coords = parseInput(input, opponent.getGrid().getGridSize());
                currentPlayer.attack(opponent, coords[0], coords[1]); // Keep row as coords[0] and column as coords[1]
            } else {
                System.out.print("Enter coordinates to attack (e.g., B4): ");
                while(true){
                    input = scanner.next().toUpperCase();
                    int[] coords = parseInput(input ,opponent.getGrid().getGridSize());

                    // First, check if coords is null
                    if (coords == null) {
                        System.out.print("Invalid format! Please enter coordinates like B4 : ");
                        continue;
                    }
                    // Then, check if the attack is valid
                    if (opponent.getGrid().isValidAttack(coords[0], coords[1])) {
                        currentPlayer.attack(opponent, coords[0], coords[1]); // Keep row as coords[0] and column as coords[1]
                        break;
                    }
                    System.out.print("Invalid attack! Please enter a valid coordinate :");
                }
            }


            player1Turn = !player1Turn;
        }

        System.out.println("\nGame Over! " + (player1.allShipsSunk(player1.getGrid()) ? player2.getName() : player1.getName()) + " wins!");
    }

    public boolean isGameOver() {
        if (player1.allShipsSunk(player1.getGrid())) {
            System.out.println("Player 2 wins!");
            return true;
        } else if (player2.allShipsSunk(player2.getGrid())) {
            System.out.println("Player 1 wins!");
            return true;
        }
        return false;
    }

    private String getPlayerName(String prompt) {
        System.out.print(prompt + ", enter your name: ");
        return scanner.next();
    }

    public static int[] parseInput(String input, int gridSize) {
        if (input.length() < 2) return null; // Too short to be valid

        char rowChar = Character.toUpperCase(input.charAt(0)); // Extract first character as row
        int row = rowChar - 'A'; // Convert 'A' -> 0, 'B' -> 1, etc.

        String colPart = input.substring(1); // Extract number part
        int col;

        try {
            col = Integer.parseInt(colPart); // Convert column to integer
        } catch (NumberFormatException e) {
            return null; // Invalid number
        }

        // Ensure within grid bounds
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize ) {
            return null;
        }

        return new int[]{col, row}; // Return correct row and column
    }

    public String makeMoveForAI() {
        Random rand = new Random();
        String move;

        do {
            char column = (char) ('A' + rand.nextInt(Math.min(gridSize, 26)));
            int row = rand.nextInt(gridSize);
            move = String.format("%c%d", column, row);
        } while (aiMoves.contains(move)); // Avoid duplicate AI moves

        aiMoves.add(move);
        return move;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
