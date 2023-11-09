import java.util.*;

public class BombSquare extends GameSquare {
    private boolean thisSquareHasBomb = false;
    private GameBoard gameBoard;
    public static final int MINE_PROBABILITY = 10;

    public BombSquare(int x, int y, GameBoard board) {
        super(x, y, "images/blank.png", board);
        this.gameBoard = board;
        Random r = new Random();
        thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);
    }

    public void clicked() {
        int NoSurroundingBombs;
        if (thisSquareHasBomb) {
            super.setImage("images/bomb.png");
            System.out.println("You clicked on a bomb! Game over!");
        } else {
            int X = super.xLocation;
            int Y = super.yLocation;
            NoSurroundingBombs=checkAdjacentSquares(X, Y);

            super.setImage("images/"+NoSurroundingBombs+".png");
        }
    }

    public int checkAdjacentSquares(int x, int y) {
    int NoSurroundingBombs=0;
    // Define the relative positions of adjacent squares 
    int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
    int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};

    // Loop through all adjacent squares
    for (int i = 0; i < dx.length; i++) {
        int newX = x + dx[i];
        int newY = y + dy[i];

        // Check if the adjacent square is within the game board boundaries
        if (newX >= 0 && newX < gameBoard.getWidth() && newY >= 0 && newY < gameBoard.getHeight()) {
            GameSquare adjacentSquare = gameBoard.getSquareAt(newX, newY);
            if (adjacentSquare instanceof BombSquare && ((BombSquare) adjacentSquare).hasBomb()) {
                NoSurroundingBombs++;
            }
        }
    }
    return (NoSurroundingBombs);
    }

    public boolean hasBomb() {
        return thisSquareHasBomb;
    }
}
