public class Player {
    private String name;
    private Board grid;
    private Board trackingGrid;
    private boolean isAI;

    public Player(String name, int boardSize, boolean isAI) {
        this.name = name;
        this.grid = new Board(boardSize);
        this.trackingGrid = new Board(boardSize);
        this.isAI = isAI;
    }

    // Reinitialize the grid to clear all previous ships & attacks
    public void resetGrid() {
        grid = new Board(grid.getGridSize());
        trackingGrid = new Board(grid.getGridSize());
    }


    public Board getTrackingGrid(){
        return this.trackingGrid;
    }

    public boolean isAI() {
        return this.isAI;
    }

    public void attack(Player opponent, int row, int col) {
        if (!trackingGrid.isValidAttack(row, col)) {
            System.out.println("You already attacked this location! Try again.");
            return;
        }

        if (opponent.getGrid().isHit(row, col)) {
            System.out.println("Hit!");
            opponent.getGrid().markHit(row, col);
            trackingGrid.markHit(row, col);
        } else {
            System.out.println("Miss!");
            opponent.getGrid().markMiss(row, col);
            trackingGrid.markMiss(row, col);
        }
    }

    public Board getGrid() {
        return grid;
    }

    public String getName() {
        return this.name;
    }

    public boolean allShipsSunk(Board board){
        int sunkShips = 0;

        for (int i = 0; i < board.getGridSize(); i++) {
            for (int j = 0; j < board.getGridSize(); j++) {
                if (grid.getGrid()[i][j] == 'X') sunkShips++;
            }
        }
        return sunkShips == 14;
    }
}
