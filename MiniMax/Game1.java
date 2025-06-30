import java.util.*;

public class Game1{
    static final int ROWS = 4;
    static final int COLS = 3;

    public static void main(String[] args){
       Scanner sc = new Scanner(System.in);
    char board[][] = new char[ROWS][COLS];

    initBoard(board);
    //arxikes times gia X kai O
    board[2][2] = 'X';
    board[0][0] = 'O';

    while(true){
        printBoard(board);
        //elegxos gia termatismo
        if(isGameOver(board)) break;

        //kinisi PC
        System.out.println("PC thinking...");
        int[] move = findBestMove(board);
        board[move[0]][move[1]]='X';
        System.out.println("PC chose: (" + move[0] + "," + move[1] + ")");
        printBoard(board);
        //elegxos
        if(isGameOver(board)) break;

        //kinisi xristi(MIN - O)
        System.out.println("Your turn!Write a row: ");
        int row = sc.nextInt();
        System.out.println("Write a collumn: ");
        int col = sc.nextInt();

        if(isValidMove(board,row,col)){
            board[row][col] = 'O';
        }
        else{
            System.out.println("Wrong move!Try again..");
            continue;
        }
    }
}

    public static void initBoard(char[][] board){
        for(int i=0;i<ROWS;i++){
            Arrays.fill(board[i],'.');
        }
    }

    public static void printBoard(char[][] board){
        System.out.println("-----------");
        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLS;j++)
                System.out.print(board[i][j] + " ");
            System.out.println();    
        }
    }

    public static boolean isValidMove(char[][] board,int row ,int col){
        return row>=0 && row<ROWS && col>=0 && col<COLS && board[row][col] == '.';
    }

    public static int checkWin(char[][] board){
        String[] patterns = {"XOX","OXO"};

        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLS;j++){
                for(String pattern : patterns){
                    char a = pattern.charAt(0);
                    char b = pattern.charAt(1);
                    char c = pattern.charAt(2);

                    //orizontia    
                    if(j+2<COLS && board[i][j]== a && board[i][j+1]==b && board[i][j+2]==c ){
                        return(pattern.equals("XOX")? 1 : -1);
                    }
                    //katheta
                    if(i+2<ROWS && board[i][j]== a && board[i+1][j]==b && board[i+2][j]==c){
                        return(pattern.equals("XOX")? 1 : -1);
                    }
                    //diagwnia(katw dexia)
                    if(i+2<ROWS && j+2<COLS && board[i][j] == a && board[i+1][j+1]==b && board[i+2][j+2]==c ){
                        return(pattern.equals("XOX")? 1 : -1);
                    }
                    //diagwnia(katw aristera)
                    if(i+2<ROWS && j-2>=0 && board[i][j] == a && board[i+1][j-1]==b && board[i+2][j-2]==c  ){
                        return(pattern.equals("XOX")? 1 : -1);
                    }
                }
            }
        }
        return 0 ;//kanenas den nikise
    }

    public static boolean isGameOver(char[][] board){
        int result = checkWin(board);

        if(result == 1){
            printBoard(board);
            System.out.println("PC is the winner!!!(MAX-X)");
            return true;
        }
        if(result == -1){
            printBoard(board);
            System.out.println("You are the winner!!!(MIN-O)");
            return true;
        }

        //elegxos gia to an exei gemisei to board
        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLS;j++){
                if(board[i][j] == '.'){
                    return false;
                }
            }
        }

        printBoard(board);
        System.out.println("DRAW");//An den emeinan kena kelia game over
        return true;        
    }

    public static int minimax(char[][] board, int depth, boolean isMaximizing){
    int result = checkWin(board);
    if(result == 1) return 10 - depth;  // win MAX
    if(result == -1) return depth - 10; // lose MAX
    if(isFull(board)) return 0;         // draw

    if(isMaximizing){
        int best = Integer.MIN_VALUE;
        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLS;j++){
                if(board[i][j] == '.'){
                    board[i][j] = 'X';
                    int val = minimax(board, depth + 1, false);
                    board[i][j] = '.';
                    best = Math.max(best, val);
                }
            }
        }
        return best;
    } else {
        int best = Integer.MAX_VALUE;
        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLS;j++){
                if(board[i][j] == '.'){
                    board[i][j] = 'O';
                    int val = minimax(board, depth + 1, true);
                    board[i][j] = '.';
                    best = Math.min(best, val);
                }
            }
        }
        return best;
    }
}


    public static boolean isFull(char[][] board) {
    for (int i = 0; i < ROWS; i++) {
        for (int j = 0; j < COLS; j++) {
            if (board[i][j] == '.') return false;
        }
    }
    return true;
}
    public static int[] findBestMove(char[][] board){
        int bestValue = Integer.MIN_VALUE;
        int[] bestMove = {-1,-1};

        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLS;j++){
                if(board[i][j] == '.'){
                    board[i][j] = 'X';
                    int moveValue = minimax(board,0,false);//twra paizei o anthrwpos
                    board[i][j] = '.';

                    if(moveValue > bestValue){
                        bestValue = moveValue;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        return bestMove;
    }

}
