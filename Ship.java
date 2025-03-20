import java.util.Random;

public class Ship {
    private final int size;
    private final boolean[] hits;
    private int startRow, startCol;
    private boolean horizontal;

    public Ship(int size) {
        this.size = size;
        this.hits = new boolean[size];
    }

    public int getSize() {
        return size;
    }

    public boolean placeShip(int row, int col, boolean horizontal, Board board) {
        this.startRow = row;
        this.startCol = col;
        this.horizontal = horizontal;

        int shipSize = this.getSize();

        if (horizontal) {
            if (col + shipSize > board.getSize()) {
                return false;
            }

            for (int i = 0; i < shipSize; i++) {
                if (board.getGrid()[row][col + i] != '~') {
                    return false;
                }
                board.getGrid()[row][col + i] = 'S';
            }
        } else {
            if (row + shipSize > board.getSize()) {
                return false;
            }

            for (int i = 0; i < shipSize; i++) {
                if (board.getGrid()[row + i][col] != '~') {
                    return false;
                }
                board.getGrid()[row + i][col] = 'S';
            }
        }
        return true;
    }

    public void placeShipRandomly(Board board) {
        Random random = new Random();
        boolean placed = false;

        while (!placed) {
            int row = random.nextInt(board.getSize());
            int col = random.nextInt(board.getSize());
            boolean horizontal = random.nextBoolean();

            if (horizontal) {
                if (col + size <= board.getSize()) {
                    placed = true;
                    for (int i = 0; i < size; i++) {
                        if (board.getGrid()[row][col + i] != '~') {
                            placed = false;
                            break;
                        }
                    }
                }
            } else {
                if (row + size <= board.getSize()) {
                    placed = true;
                    for (int i = 0; i < size; i++) {
                        if (board.getGrid()[row + i][col] != '~') {
                            placed = false;
                            break;
                        }
                    }
                }
            }

            if (placed) {
                startRow = row;
                startCol = col;
                this.horizontal = horizontal;

                for (int i = 0; i < size; i++) {
                    if (horizontal) {
                        board.getGrid()[row][col + i] = 'S';
                    } else {
                        board.getGrid()[row + i][col] = 'S';
                    }
                }
            }
        }
    }

    public void markHits(int row, int col) {
        if (horizontal) {
            if (row == startRow && col >= startCol && col < startCol + size) {
                hits[col - startCol] = true;
            }
        } else {
            if (col == startCol && row >= startRow && row < startRow + size) {
                hits[row - startRow] = true;
            }
        }
    }

    public boolean isSunk() {
        for (boolean hit : hits) {
            if (!hit) {
                return false;
            }
        }
        return true;
    }
}