import java.util.*;

public class BombSquare extends GameSquare {
    private boolean thisSquareHasBomb = false;
    private boolean PrevClicked = false; 
    private GameBoard gameBoard;
    public static final int MINE_PROBABILITY = 10;

    public BombSquare(int x, int y, GameBoard board) {
        super(x, y, "images/blank.png", board);
        this.gameBoard = board;
        Random r = new Random();
        thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);
    }

// This method is called when the square has bomb.
    /**
    * Called when the player clicks on a square. This method checks to see if there are bomb squares adjacent to the square
    */
    public void clicked() {
        if (!this.PrevClicked){
            this.PrevClicked =true;
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
                // Clears the Sqaures and the Sqaures.
                if (NoSurroundingBombs==0){
                    clearSqaures(X,Y);
                }
            }
        }
    }
    /**
    * Clears the squaures at the given coordinates. This is called when the user clicks on a bomb or is over the square
    * 
    * @param x - The x coordinate of the square to clear the squaures at. It is assumed that this is the top left corner of the square
    * @param y - The y coordinate of the square to clear the
    */
    public void clearSqaures(int x,int y){
        // Define the relative positions of adjacent squares 
        int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};

        // Loop through all adjacent squares
        // This method is used to check if the adjacent square is a bomb square.
        for (int i = 0; i < dx.length; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];
            // If the adjacent square is adjacent to the current position
            if (newX >= 0 && newX < gameBoard.getWidth() && newY >= 0 && newY < gameBoard.getHeight()) {
                GameSquare adjacentSquare = gameBoard.getSquareAt(newX, newY);
            // If the adjacent square is a bomb square and has a bomb square click the adjacent square.
                if (adjacentSquare instanceof BombSquare && !((BombSquare) adjacentSquare).hasBomb()) {
                    adjacentSquare.clicked();
                }
            }
        }
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

    // Find the adjacent square in the game board.
    for (int i = 0; i < dx.length; i++) {
        int newX = x + dx[i];
        int newY = y + dy[i];
        // Check if the adjacent square is a bomb square
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
