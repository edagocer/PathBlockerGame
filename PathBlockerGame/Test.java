
public class Test {

    public static void main(String[] args) {
        Solver solver = new Solver();

        for (int i = 1; i <= 10; i++) {
            String fileName = String.format("level%02d.txt", i);
            solver.solveLevel(fileName);
            System.out.println();
        }
    }
}
/* 
1) Why you prefer the search algorithm you choose?
BFS will find the shortest path to the goal.In our implementation, each move involves sliding until hitting a wall or goal, 
which modifies the board state. BFS ensures you explore all possible board states level by level.

2) Can you achieve the optimal result? Why? Why not?
Yes, our implementation can achieve optimal results in terms of the minimum number of moves because BFS explores
all possible moves at each level before moving to the next level.

3) How you achieved efficiency for keeping the states?
To achieve efficiency in managing the states, we implemented a set data structure to track visited board configurations. 
This helps prevent redundant processing of the same state, which can significantly reduce the number of nodes explored
 during the search. Although copying the grids repeatedly to avoid path conflicts increases memory usage, 
 our visited hash set minimizes memory waste by preventing us from revisiting previously explored paths.

4) If you prefer to use DFS (tree version) then do you need to avoid cycles?
DFS starts at root node and explores as far as possible along each branch before backtracking. 
Stack should be used to track nodes to visit next.
Push deeper nodes first to defer wider traversal. Visited nodes should be marked to avoid cycles.

5) What will be the path-cost for this problem?
The total cost is based on how many times the player changes direction throughout the path. 
Thus, the path cost can be calculated as the total number of moves, which corresponds to the length of the path list minus one. 
This is because the initial position is included in the list, but it doesn’t count as a move.  
*/
