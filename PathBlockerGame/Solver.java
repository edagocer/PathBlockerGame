
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import javax.imageio.ImageIO;


public class Solver {
    
    private final int[] dRow = {-1, 0, 1, 0}; 
    private final int[] dCol = {0, 1, 0, -1};
    private final int BOARD_SIZE = 15;
    private final int CELL_SIZE = 10;

    private char[][] board;
    private String levelName;
    private List<Step> path;
    
    public Solver(){
    }
    
    public void solveLevel(String file) {
        board = readFromFile(file);
        path = new ArrayList<>();
        int[] start = findChar('S');

        levelName = file.substring(0, file.indexOf('.'));
        createFolder(levelName);
        
        char[][] initialBoard = copyBoard(); //Save initial board state	
        Step initialMove = new Step(start[0], start[1], start[0], start[1], initialBoard);
        path.add(initialMove); 
        
        if (start != null && findChar('G') != null && bfs(start[0], start[1])) {
            saveSuccessfulPath();
        } else {
            System.out.println("Path not found.");
        }
    }

    //Implements Breadth-First Search to find a path from start to goal.
    private boolean bfs(int startX, int startY) {
        Queue<Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        List<Step> initialPath = new ArrayList<>();
        initialPath.add(path.get(0));
        
        char[][] startBoard = copyBoard();
        queue.add(new Node(startX, startY, startBoard, initialPath));
        
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int x = current.x;
            int y = current.y;
            
            String boardKey = getBoardKey(current.boardState);
            if (visited.contains(boardKey)) {
                continue;
            }
            visited.add(boardKey);
            
            
           // Try all four directions	
            for (int i = 0; i < 4; i++) {
                int newRow = x, newCol = y;
                char[][] newBoard = copyBoard(current.boardState);
                
               // Move in current direction until blocked or reach goal.
                while (canMove(newRow + dRow[i], newCol + dCol[i], newBoard)) {
                	newRow += dRow[i];
                	newCol += dCol[i];
                	//Stop moving if the goal is reached
                    if (newBoard[newRow][newCol] == 'G') {
                        break;
                    }
                    newBoard[newRow][newCol] = '#';//Mark path as blocked.
                }

                if (newRow != x || newCol != y) {
                    List<Step> newPath = new ArrayList<>(current.path);
                    Step newStep = new Step(x, y, newRow, newCol, newBoard);
                    newPath.add(newStep);
                    
                    if (newBoard[newRow][newCol] == 'G') {
                        path = newPath;
                        return true;
                    }
                    
                    queue.add(new Node(newRow, newCol, newBoard, newPath));
                }
            }
        }
        return false;
    }
    
    //Saves the current board state as a PNG image
	private void saveBoardAsImage(char[][] boardState, Step step, int index) {
		BufferedImage image = new BufferedImage(BOARD_SIZE * CELL_SIZE, BOARD_SIZE * CELL_SIZE,BufferedImage.TYPE_INT_RGB);
		Graphics img = image.getGraphics();

		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				int x = j * CELL_SIZE;
				int y = i * CELL_SIZE;

				if (i == step.playerX && j == step.playerY) {
					img.setColor(Color.YELLOW);
					img.fillRect(x, y, CELL_SIZE, CELL_SIZE);
					continue;
				}

				switch (boardState[i][j]) {
				case 'G':
					img.setColor(Color.GREEN);
					img.fillRect(x, y, CELL_SIZE, CELL_SIZE);
					break;
				case 'S':
				case '#':
					img.setColor(new Color(128, 0, 128));
					img.fillRect(x, y, CELL_SIZE, CELL_SIZE);
					break;
				default:
					img.setColor(new Color(173, 216, 230));
					img.fillRect(x, y, CELL_SIZE, CELL_SIZE);
				}
			}
		}

		try {
			File outputfile = new File(levelName + "/level" + String.format("%04d", index) + ".png");
			System.out.println(levelName + "/level" + String.format("%04d", index) + ".png");
			ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Saves images for each step in the successful path.	
    private void saveSuccessfulPath() {
        for (int i = 0; i < path.size(); i++) {
            saveBoardAsImage(path.get(i).boardState, path.get(i), i);
        }
    }
   
     //Creates a unique string representation of the board state for visited checking.
    private String getBoardKey(char[][] boardState) {
        StringBuilder key = new StringBuilder();
        for (char[] row : boardState) {
            key.append(new String(row));
        }
        return key.toString();
    }

    //Creates a deep copy of the provided board state.
    private char[][] copyBoard(char[][] source) {
        char[][] copy = new char[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            copy[i] = source[i].clone();
        }
        return copy;
    }
    
    //Creates a deep copy of the current board state.
    private char[][] copyBoard() {
        return copyBoard(board);
    }
    //Checks if a move to the given position is valid
    private boolean canMove(int x, int y, char[][] currentBoard) {
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE && 
               currentBoard[x][y] != '#' && currentBoard[x][y] != 'S'||currentBoard[x][y] == 'G';
    }
    //Finds the coordinates of a specific character on the board
    private int[] findChar(char ch) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == ch) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private char[][] readFromFile(String fileName) { 
        char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int row = 0;
            while ((row < BOARD_SIZE) && (line = br.readLine()) != null) {
                for (int col = 0; col < Math.min(line.length(), BOARD_SIZE); col++) {
                    board[row][col] = line.charAt(col);
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return board;
    }

    private static void createFolder(String name) {
        File dir = new File(name);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
