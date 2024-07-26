import java.util.Random; 

//this class contains all the different sudoku boards and backtracker
public class SudokuFrames {

    //this is the board we will pass to the Frame class
    public int[][] emptyboard;

    //create an instance of the random class
    Random random = new Random();
    int randomint;

    //this constructor calls the corresponding method to for which sudoku board to use 
    //it takes in an int which is the users choice of mode
    public SudokuFrames(int choice){
       
        //create the random number
        randomint =random.nextInt(3)+1;

        //call the corresponding method
        switch (choice) {
            case 0:easyboards();
                break;
            case 1:mediumboards();
                break;
            case 2:hardboards();
                break;

            default:easyboards();
                break;
        }
    }
    
    //Let's build the algorithm of this program. The board size will be n x n.
    private int[][] newBoard;
    private int size = 9;
    
// Let's return true or false in case there is a solution or isn't a solution.
    public boolean backtrackSudoku(int[][] board){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    for (int k = 1; k <= 9; k++) {
                        if (validRow(board,i,k) && validCol(board,j,k) && valid3x3(board,i-i%3,j-j%3,k)) {
                            board[i][j] = k;
                            if (backtrackSudoku(board)) {
                                newBoard = board;
                                return true;
                            }else{
                                board[i][j] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    //Let's check if we have enough information to solve the board.

    public boolean validBoard(int[][] board){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(board[i][j] != 0){
                    int[][] tempBoard = new int[size][size]; // Let's make a temporary board in order to run some tests.
                    for (int k = 0; k < size; k++) {
                        tempBoard[k] = board[k].clone(); 
                        
                    }
                    tempBoard[i][j] = 0; // Let's empty it out so it's ready for the next test.
                    if (!validRow(tempBoard,i,board[i][j]) || !validCol(tempBoard,j,board[i][j]) || !valid3x3(tempBoard,i-i%3,j-j%3,board[i][j]))
                        return false; //all 3 conditions must be satisfied or else it's not true.
                }
            }
        }
        return true;
    }
    //Let's check if the row inputs are valid. There should be no duplicates in the same row.
    public boolean validRow(int[][] board, int i,int candidate){
        for (int j = 0; j < size; j++) {
            if (board[i][j]==candidate)
                return false;
        }
        return true;
    }
    //Let's do the same for columns now. There must not be duplicates in the same column.
    public boolean validCol(int[][] board, int j, int candidate){
        for (int i = 0; i < size; i++) {
            if (board[i][j] == candidate) {
                return false;
            }
        }
        return true;
    }

    //Now let's check the 3x3 squares. There must not be duplicates in the same 3 by 3 square of the current value.
    public boolean valid3x3(int[][] board, int top, int left, int candidate){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[top+i][left+j] == candidate) 
                    return false;
            }
        }
        return true;
    } 

    //this checks if the board can be solved and returns the solved board
    public int[][] solved(){
        if(backtrackSudoku(emptyboard) && validBoard(emptyboard)){
            return newBoard;
        }
        return null;
    }

    //this method assigns one of 3 easy sudoku boards to the emptyboard variable
    public void easyboards(){
        switch (randomint) {
            case 1: emptyboard = new int[][]{
                {0, 2, 7, 5, 0, 1, 9, 8, 4},
                {0, 1, 3, 0, 0, 9, 2, 0, 0},
                {0, 0, 4, 0, 0, 7, 6, 0, 0},
                {0, 7, 5, 4, 0, 0, 8, 3, 2},
                {3, 0, 0, 0, 1, 8, 7, 0, 0},
                {0, 0, 8, 0, 5, 0, 1, 0, 0},
                {0, 3, 6, 1, 8, 5, 0, 0, 9},
                {0, 0, 0, 0, 0, 0, 3, 7, 0},
                {9, 8, 0, 3, 0, 0, 0, 0, 0}
            }; 
                break;
        
            case 2: emptyboard = new int[][]{
                {0, 2, 6, 3, 5, 0, 0, 0, 1},
                {8, 0, 1, 0, 6, 0, 0, 0, 0},
                {0, 0, 0, 8, 1, 9, 5, 0, 6},
                {3, 0, 2, 0, 0, 0, 1, 0, 5},
                {5, 0, 0, 9, 0, 0, 0, 0, 0},
                {0, 9, 0, 2, 4, 0, 8, 6, 3},
                {0, 0, 5, 4, 9, 7, 0, 0, 0},
                {0, 3, 4, 1, 2, 0, 9, 5, 0},
                {0, 0, 0, 5, 8, 0, 0, 7, 0}
            }; 
                break;
            case 3: emptyboard = new int[][]{
                {5, 6, 8, 0, 4, 0, 0, 0, 3},
                {0, 0, 2, 0, 9, 0, 0, 0, 7},
                {0, 9, 7, 8, 6, 0, 0, 0, 0},
                {6, 0, 0, 3, 1, 0, 4, 0, 9},
                {0, 3, 0, 0, 5, 0, 0, 6, 2},
                {0, 1, 9, 6, 0, 0, 5, 0, 8},
                {0, 0, 3, 0, 0, 6, 8, 0, 1},
                {0, 5, 1, 0, 0, 0, 0, 2, 0},
                {9, 0, 0, 7, 0, 0, 3, 4, 5}
            }; 
                break;
        }
    }

    //this method does the same but with medium boards
    public void mediumboards(){
        switch (randomint) {
            case 1: emptyboard = new int[][]{
                {2, 6, 0, 1, 0, 4, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 5, 0, 0},
                {0, 8, 0, 0, 0, 7, 0, 2, 9},
                {6, 0, 0, 5, 0, 0, 0, 3, 2},
                {0, 0, 0, 9, 6, 3, 0, 4, 0},
                {3, 0, 7, 8, 4, 2, 1, 0, 0},
                {0, 0, 8, 0, 9, 0, 6, 0, 0},
                {0, 3, 5, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 2, 0, 7}
            };
            break;
        
            case 2: emptyboard = new int[][]{
                {0, 9, 0, 0, 0, 3, 0, 2, 4},
                {0, 0, 0, 4, 0, 0, 1, 0, 0},
                {0, 0, 2, 0, 0, 0, 0, 0, 7},
                {0, 2, 6, 0, 7, 0, 0, 0, 5},
                {8, 0, 0, 3, 4, 0, 0, 7, 0},
                {4, 0, 3, 0, 0, 6, 9, 0, 0},
                {2, 0, 9, 0, 6, 0, 3, 0, 0},
                {0, 8, 0, 1, 0, 2, 0, 4, 0},
                {7, 0, 0, 0, 0, 0, 6, 0, 0}
            }; 
                break;
            case 3: emptyboard = new int[][]{
                {6, 7, 0, 0, 0, 0, 4, 5, 0},
                {0, 5, 0, 0, 7, 2, 0, 0, 0},
                {0, 0, 0, 4, 0, 5, 7, 0, 3},
                {0, 8, 0, 0, 0, 0, 3, 0, 0},
                {0, 9, 0, 7, 5, 0, 0, 1, 8},
                {2, 0, 0, 0, 0, 8, 5, 0, 6},
                {0, 0, 0, 5, 4, 6, 8, 2, 9},
                {8, 0, 9, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 8, 0, 0, 0, 0, 0}
            }; 
                break;
        }
    }

    //this method does the same but with hard boards
    public void hardboards(){
        switch (randomint) {
            case 1: emptyboard = new int[][]{
                {0, 3, 1, 0, 6, 0, 4, 0, 0},
                {0, 0, 0, 0, 0, 2, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 3},
                {0, 0, 0, 4, 0, 0, 0, 0, 0},
                {0, 6, 2, 0, 0, 0, 0, 0, 9},
                {0, 0, 4, 9, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 4, 5, 0, 0},
                {0, 9, 7, 0, 8, 0, 0, 0, 0},
                {0, 8, 0, 0, 1, 0, 7, 6, 0}
            };
            break;
        
            case 2: emptyboard = new int[][]{
                {0, 7, 0, 0, 0, 1, 0, 0, 5},
                {0, 4, 0, 5, 2, 0, 0, 6, 0},
                {0, 0, 2, 0, 0, 3, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 8, 4, 5, 0, 0, 0, 7},
                {7, 0, 0, 0, 0, 0, 0, 3, 0},
                {0, 0, 0, 0, 0, 2, 0, 0, 0},
                {0, 0, 4, 7, 8, 0, 0, 0, 3},
                {0, 6, 0, 0, 0, 0, 9, 0, 0}
            }; 
                break;
            case 3: emptyboard = new int[][]{
                {0, 0, 1, 3, 0, 0, 0, 0, 0},
                {0, 9, 0, 0, 2, 0, 0, 0, 0},
                {0, 0, 8, 0, 0, 0, 0, 2, 6},
                {0, 0, 0, 0, 0, 9, 0, 0, 5},
                {0, 0, 0, 0, 0, 1, 8, 0, 9},
                {0, 0, 0, 7, 3, 4, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 7, 0, 0},
                {4, 7, 0, 0, 0, 0, 0, 9, 0},
                {3, 0, 0, 0, 1, 0, 4, 0, 0}
            }; 
                break;
        }
    }

}
