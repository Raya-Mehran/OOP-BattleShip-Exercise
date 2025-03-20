public class Coordinate {
    private final int row;
    private final int col;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public static boolean isValidInput(String input, int boardSize) {
        if (input.length() < 2 || input.length() > 3) {
            return false;
        }

        char colChar = input.charAt(0);
        if (colChar < 'A' || colChar > 'A' + boardSize - 1) {
            return false;
        }

        String rowStr = input.substring(1);
        for (int i = 0; i < rowStr.length(); i++) {
            if (!Character.isDigit(rowStr.charAt(i))) {
                return false;
            }
        }

        int row = Integer.parseInt(rowStr);
        return row >= 1 && row <= boardSize;
    }

    public static Coordinate parse(String input, int boardSize) {
        if (!isValidInput(input, boardSize)) {
            return null;
        }

        int col = input.charAt(0) - 'A';
        int row = Integer.parseInt(input.substring(1)) - 1;

        return new Coordinate(row, col);
    }
}