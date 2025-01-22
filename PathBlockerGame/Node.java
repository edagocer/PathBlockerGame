
import java.util.List;

public class Node {

    int x, y;
    char[][] boardState;
    List<Step> path;

    Node(int x, int y, char[][] boardState, List<Step> path) {
        this.x = x;
        this.y = y;
        this.boardState = boardState;
        this.path = path;
    }
}
