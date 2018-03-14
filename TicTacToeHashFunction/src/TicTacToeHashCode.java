
public class TicTacToeHashCode extends Board {

 boolean [] winners;  // True if the hash string that maps to this index is a winner, false otherwise
    
  TicTacToeHashCode(String s) {
   super(s);
  // TODO Instantiate/fill winners array.  
  }
  
  // TODO - write the myHashCode function.  It must create a unique hashcode for all of the 
  //   possible values the game board (3 ^ 9) and it MUST use the super.charAt(row, col) function
  @Override
    public int myHashCode() {

      return 0;
   }
   
    public boolean isWin(String s) {
    // return the value in the winner array for the hash code of the board string sent in.
    return true;
    }
  
   public static void main(String[] args) throws InterruptedException {
      TicTacToeHashCode board = new TicTacToeHashCode ("Tic Tac Toe");
      while (true) {
      
         String currentBoard = board.boardValues[(int)(Math.random()* board.boardValues.length)];
         board.show(currentBoard);
         board.setHashCode(board.myHashCode());
         // TODO Update this line to call your isWin method.
         board.setWinner(TicTacToe.isWin(currentBoard));
         
         Thread.sleep(4000);      
      }
   }
}