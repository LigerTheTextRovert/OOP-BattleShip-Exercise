import java.util.Random;

public class Ship {

    private final int size;
    private boolean isHorizontal;
    private int row, col;
    private boolean isSunk;
    private boolean[] hits;

    public Ship(int size) {
        this.size = size;
        this.isSunk = false;
        this.hits = new boolean[size];
    }

    public void setPosition(int row, int col, boolean isHorizontal) {
        this.col = col;
        this.row = row;
        this.isHorizontal = isHorizontal;
    }

    public boolean isHit(int row, int col) {
        if (isHorizontal) {
            return this.row == row && this.col <= col && this.col < col + size;
        }
        if (!isHorizontal) {
            return this.col == col && this.row <= row && this.row < row + size;
        }
    }

    public void markHit(int row, int col) {
        if (isHit(row, col)) {
            int index = (isHorizontal ? col - this.col : row - this.row);
            hits[index] = true;
            checkIfSunk();
        }
    }
    public void checkIfSunk(){
        for (boolean hit : hits){
            if (!hit) return;
        }
        this.isSunk = true;
    }

    public boolean isSunk(){
        return this.isSunk;
    }
}
