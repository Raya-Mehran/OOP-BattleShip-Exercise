import java.util.Scanner;

public class Player {
    private final String name;
    private final Board board;
    private Ship[] ships;

    public Player(String name, Board board) {
        this.name = name;
        this.board = board;
        this.ships = new Ship[4];
    }

    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }

    public Ship[] getShips() {
        return ships;
    }

    public void setShips(Ship[] ships) {
        if (ships.length == 4) {
            this.ships = ships;
        } else {
            System.out.println(" you should have 4 ships.");
        }
    }

    public boolean lost() {
        for (Ship ship : ships) {
            if (ship == null || !ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    public String makeMove() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(name + ", enter your input ");
        return scanner.next().toUpperCase();
    }
}