import java.util.Scanner;

public class Game {
    private final Player player1;
    private final Player player2;
    private final Scanner scanner = new Scanner(System.in);

    public Game() {
        int boardSize = 0;
        boolean validSize = false;

        while (!validSize) {
            System.out.println("Enter your board size (should be between 4 and 26): ");
            boardSize = scanner.nextInt();
            scanner.nextLine();

            if (boardSize >= 4 && boardSize <= 26) {
                validSize = true;
            } else {
                System.out.println("Incorrect board size.Enter again");
            }
        }

        Board board1 = new Board(boardSize);
        Board board2 = new Board(boardSize);

        System.out.println("Do you want to play against an AI or another player?");
        System.out.println("Enter 'AI' or 'human'");
        String opponentChoice = scanner.nextLine();

        switch (opponentChoice) {
            case "AI":
                player1 = new Player("Player 1", board1);
                player2 = new AIPlayer("AI Player", board2);
                break;
            case "human":
                player1 = new Player("Player 1", board1);
                player2 = new Player("Player 2", board2);
                break;
            default:
                System.out.println("Invalid choice.default is against another player.");
                player1 = new Player("Player 1", board1);
                player2 = new Player("Player 2", board2);
                break;
        }
    }

    public void start() {
        boolean playAgain;
        do {
            resetGame();

            placingShips(player1);
            placingShips(player2);

            playingGame();
            playAgain = askReplay();
        } while (playAgain);
    }

    private void resetGame() {
        player1.getBoard().initializeGrid(player1.getBoard().getGrid());
        player1.getBoard().initializeGrid(player1.getBoard().getTrackingGrid());
        player2.getBoard().initializeGrid(player2.getBoard().getGrid());
        player2.getBoard().initializeGrid(player2.getBoard().getTrackingGrid());

        player1.setShips(new Ship[4]);
        player2.setShips(new Ship[4]);
    }

    private boolean askReplay() {
        System.out.println("Play again? (yes/no)");
        return scanner.next().equalsIgnoreCase("yes");
    }

    private void placingShips(Player player) {
        if (player instanceof AIPlayer) {
            placeShipsRandom(player);
        } else {
            System.out.println(player.getName() + ", do you want to place your ships randomly or yourself?");
            System.out.println("Enter 'random' or 'manual'");
            String choice = scanner.next();

            if (choice.equals("random")) {
                placeShipsRandom(player);
            } else if (choice.equals("manual")) {
                placeShipsManual(player);
            } else {
                System.out.println("Invalid choice. Default is random.");
                placeShipsRandom(player);
            }
        }
    }

    private void playingGame() {
        boolean gameOver = false;
        Player currentPlayer = player1;
        Player opponentPlayer = player2;

        while (!gameOver) {
            System.out.println(currentPlayer.getName() + "'s turn!");

            if (!(currentPlayer instanceof AIPlayer)) {
                System.out.println("Tracking grid:");
                opponentPlayer.getBoard().display(opponentPlayer.getBoard().getTrackingGrid());
            }

            if (currentPlayer instanceof AIPlayer) {
                String move = ((AIPlayer) currentPlayer).makeMove();
                System.out.println(currentPlayer.getName() + " chose " + move);

                Coordinate cord = Coordinate.parse(move, opponentPlayer.getBoard().getSize());

                if (cord != null) {
                    opponentPlayer.getBoard().attack(cord, opponentPlayer);

                    if (opponentPlayer.lost()) {
                        System.out.println(currentPlayer.getName() + " wins! Game over.");
                        gameOver = true;
                    }
                } else {
                    System.out.println("Invalid input! Try again.");
                }
            } else {
                if (askingForSpecialAttack(currentPlayer)) {
                    performSpecialAttack(currentPlayer, opponentPlayer);
                } else {
                    System.out.println(currentPlayer.getName() + ", enter your move (for example A1): ");
                    String move = scanner.next();

                    Coordinate cord = null;
                    boolean validMove = false;
                    while (!validMove) {
                        if (Coordinate.isValidInput(move, opponentPlayer.getBoard().getSize())) {
                            cord = Coordinate.parse(move, opponentPlayer.getBoard().getSize());
                            if (cord != null) {
                                validMove = true;
                            } else {
                                System.out.println("Invalid input. enter again");
                                move = scanner.next();
                            }
                        } else {
                            System.out.println("Invalid input.enter again");
                            move = scanner.next();
                        }
                    }

                    opponentPlayer.getBoard().attack(cord, opponentPlayer);

                    if (opponentPlayer.lost()) {
                        System.out.println(currentPlayer.getName() + " wins! Game over.");
                        gameOver = true;
                    }
                }
            }

            if (!gameOver) {
                Player temp = currentPlayer;
                currentPlayer = opponentPlayer;
                opponentPlayer = temp;
            }
        }
    }

    private boolean askingForSpecialAttack(Player player) {
        System.out.println(player.getName() + ", do you want a special attack?");
        System.out.println("Enter 'yes' or 'no'");
        String choice = scanner.next();
        return "yes".equals(choice);
    }

    private void performSpecialAttack(Player player , Player opponent) {
        System.out.println("Which special attack do you want?");
        System.out.println("Enter 'radar' or 'multi'");
        String choice = scanner.next();

        switch (choice) {
            case "radar":
                SpecialAttack.radarScan(opponent.getBoard());
                break;
            case "multi":
                System.out.println("Enter the center coordinate for Multi-Strike (for example A1): ");
                String center = scanner.next();
                Coordinate cord = Coordinate.parse(center, opponent.getBoard().getSize());
                if (cord != null) {
                    SpecialAttack.multiStrike(opponent.getBoard(), cord, opponent);
                } else {
                    System.out.println("Invalid input.");
                }
                break;
            default:
                System.out.println("Invalid special attack choice.");
        }
    }

    private void placeShipsRandom(Player player) {
        if (!(player instanceof AIPlayer)) {
            System.out.println("Randomly placing ships for " + player.getName());
        }

        Ship[] ships = new Ship[4];
        ships[0] = new Ship(2);
        ships[1] = new Ship(3);
        ships[2] = new Ship(4);
        ships[3] = new Ship(5);

        player.setShips(ships);

        for (Ship ship : ships) {
            ship.placeShipRandomly(player.getBoard());
            if (!(player instanceof AIPlayer)) {
                System.out.println("Placed ship of size " + ship.getSize() + " randomly.");
            }
        }
    }

    private void placeShipsManual(Player player) {
        Ship[] ships = new Ship[4];
        ships[0] = new Ship(2);
        ships[1] = new Ship(3);
        ships[2] = new Ship(4);
        ships[3] = new Ship(5);

        player.setShips(ships);

        for (int i = 0; i < ships.length; i++) {
            boolean placed = false;
            while (!placed) {
                System.out.println("Place ship " + (i + 1) + " (size " + ships[i].getSize() + ").");
                System.out.println("Enter your coordinate ");
                String startPos = scanner.next();
                System.out.println("Place horizontally? (yes/no): ");
                boolean horizontal = scanner.next().equalsIgnoreCase("yes");

                Coordinate cord = Coordinate.parse(startPos, player.getBoard().getSize());
                if (cord != null) {
                    placed = ships[i].placeShip(cord.getRow(), cord.getCol(), horizontal, player.getBoard());
                    if (!placed) {
                        System.out.println("Invalid placement. Enter again.");
                    }
                } else {
                    System.out.println("Invalid coordinate. Enter again.");
                }
            }
            System.out.println("Ship placed correctly");
        }
    }
}