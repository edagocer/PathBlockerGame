
public class Step {

    private final int BOARD_SIZE = 15;
    int fromX, fromY, toX, toY;
    char[][] boardState;
    int playerX, playerY;

    Step(int fromX, int fromY, int toX, int toY, char[][] boardState) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.boardState = new char[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            this.boardState[i] = boardState[i].clone();
        }
        //Set the player's new position
        this.playerX = toX;
        this.playerY = toY;
    }
}
