import java.util.Random;

public class SpecialAttack {

    public static void radarScan(Board opponentBoard) {
        System.out.println("Radar scan activated");

        Random random = new Random();
        int centerRow = random.nextInt(opponentBoard.getSize());
        int centerCol = random.nextInt(opponentBoard.getSize());

        System.out.println("Radar scan results" + centerRow + ", " + centerCol + ")):");

        for (int i = centerRow - 1; i <= centerRow + 1; i++) {
            for (int j = centerCol - 1; j <= centerCol + 1; j++) {
                if (i >= 0 && i < opponentBoard.getSize() && j >= 0 && j < opponentBoard.getSize()) {
                    char cell = opponentBoard.getGrid()[i][j];
                    if (cell == 'S') {
                        System.out.print("S ");
                    } else {
                        System.out.print("~ ");
                    }
                } else {
                    System.out.print("~ ");
                }
            }
            System.out.println();
        }
    }

    public static void multiStrike(Board opponentBoard, Coordinate center, Player opponentPlayer) {
        System.out.println("Multi-Strike attack activated");

        int row = center.getRow();
        int col = center.getCol();

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < opponentBoard.getSize() && j >= 0 && j < opponentBoard.getSize()) {
                    if (opponentBoard.getGrid()[i][j] == 'S') {
                        opponentBoard.getGrid()[i][j] = 'X';
                        opponentBoard.getTrackingGrid()[i][j] = 'X';

                        for (Ship ship : opponentPlayer.getShips()) {
                            ship.markHits(i, j);
                        }

                        System.out.println("Hit at (" + i + ", " + j + ")");
                    } else if (opponentBoard.getGrid()[i][j] == '~') {
                        opponentBoard.getTrackingGrid()[i][j] = 'O';
                        System.out.println("Miss at (" + i + ", " + j + ")");
                    } else {
                        System.out.println("Already attacked at (" + i + ", " + j + ")");
                    }
                }
            }
        }
    }
}