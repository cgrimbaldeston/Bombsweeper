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

// This method is called when the square has bomb.
    public void clicked() {
        int NoSurroundingBombs =0;
        // This method is called when the square has bomb.
        if (thisSquareHasBomb) {
            super.setImage("images/bomb.png");
            System.out.println("You clicked on a bomb! Game over!");
        } else {
            int X = super.xLocation;
            int Y = super.yLocation;
            NoSurroundingBombs=checkAdjacentSquares(X, Y);
            super.setImage("images/"+NoSurroundingBombs+".png");
            if (NoSurroundingBombs==0){
                clearSqaures();
            }
        }
    }
    public clearSqaures(int x,int y){
        super.setImage("images/0.png");
    }

    /**
    * Checks to see if there are bomb squares adjacent to the given position. This is used by the checkAdjacentSquares method
    * 
    * @param x - The x position to check
    * @param y - The y position to check ( must be in range 0.. gameBoard. height - 1 )
    * 
    * @return The number of bombs adjacent to the given position that are surrounded by bomb squares or 0 if there are
    */
    public int checkAdjacentSquares(int x, int y) {
    int NoSurroundingBombs=0;
    // Define the relative positions of adjacent squares 
    int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
    int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};

    // Loop through all adjacent squares
    for (int i = 0; i < dx.length; i++) {
        int newX = x + dx[i];
        int newY = y + dy[i];
        if (newX >= 0 && newX < gameBoard.getWidth() && newY >= 0 && newY < gameBoard.getHeight()) {
            GameSquare adjacentSquare = gameBoard.getSquareAt(newX, newY);
            // If the adjacent square has a bomb square
            if (adjacentSquare instanceof BombSquare && ((BombSquare) adjacentSquare).hasBomb()) {
                NoSurroundingBombs++;
            }
            }
        }
    return (NoSurroundingBombs);
    }

    /**
    * Returns true if there is a bomb. This is used to determine if we are going to have an enemy in a square that does not belong to the player's hand.
    * 
    * 
    * @return true if there is a bomb false otherwise ( not necessarily true for enemy springs
    */
    public boolean hasBomb() {
        return thisSquareHasBomb;
    }
}
