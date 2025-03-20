public class Board {
    private final char[][] grid;
    private final char[][] trackingGrid;
    private final int size;

    public Board(int size) {
        this.size = size;
        this.grid = new char[size][size];
        this.trackingGrid = new char[size][size];
        initializeGrid(grid);
        initializeGrid(trackingGrid);
    }

    public int getSize() {
        return size;
    }

    public char[][] getGrid() {
        return grid;
    }

    public char[][] getTrackingGrid() {
        return trackingGrid;
    }

    public void initializeGrid(char[][] grid) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = '~';
            }
        }
    }

    public void display(char[][] grid) {
        System.out.print("   ");
        for (char c = 'A'; c < 'A' + size; c++) {
            System.out.print(c + " ");
        }
        System.out.println();

        for (int i = 0; i < size; i++) {
            System.out.printf("%2d ", i + 1);
            for (int j = 0; j < size; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void attack(Coordinate coordinate, Player opponentPlayer) {
        int row = coordinate.getRow();
        int col = coordinate.getCol();

        if (row < 0 || row >= size || col < 0 || col >= size) {
            System.out.println("Invalid input");
            return;
        }

        if (grid[row][col] == 'S') {
            grid[row][col] = 'X';
            trackingGrid[row][col] = 'X';

            for (Ship ship : opponentPlayer.getShips()) {
                ship.markHits(row, col);
            }

            System.out.println("It's a hit!");
            return; // It's a hit
        } else if (grid[row][col] == '~') {
            trackingGrid[row][col] = 'O';
            System.out.println("It's a miss!");
            return;
        }

        System.out.println("This cell has already been shot at .");
    }
}