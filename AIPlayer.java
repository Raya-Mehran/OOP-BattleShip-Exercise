import java.util.Random;

public class AIPlayer extends Player {

    public AIPlayer(String name, Board playerBoard) {
        super(name, playerBoard);
    }

    @Override
    public String makeMove() {
        Random rand = new Random();
        int row = rand.nextInt(getBoard().getSize());
        int col = rand.nextInt(getBoard().getSize());
        return "" + (char) ('A' + row) + col;
    }
}