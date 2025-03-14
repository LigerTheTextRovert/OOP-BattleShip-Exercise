public class Ship {
    private int size;
    private boolean[] hits; // Tracks hits on each part of the ship

    public Ship(int size) {
        this.size = size;
        this.hits = new boolean[size];
    }

    public int getSize() {
        return size;
    }

    public boolean isSunk() {
        for (boolean hit : hits) {
            if (!hit) {
                return false; // If any part is not hit, the ship is still afloat
            }
        }
        return true;
    }

    public void hit(int position) {
        if (position >= 0 && position < size) {
            if (!hits[position]) { // Prevent redundant hits
                hits[position] = true;
            }
        } else {
            throw new IllegalArgumentException("Invalid hit position.");
        }
    }
}
