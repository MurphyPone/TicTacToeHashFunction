import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class OLD_TicTacToeHashCode extends OLD_Board {

	boolean [] winners;  // True if the hash string that maps to this index is a winner, false otherwise (by default)
    
 	OLD_TicTacToeHashCode(String s) {
 		super(s);
 		winners = new boolean[19683]; //Array of size 3^9
 		//read in file TicTacToeWinners.txt
	    Scanner input = openFile("TicTacToeWinners.txt");	
 		//winners[hashCode(currentString)] = tru
	    while (input.hasNext() != null) 
	    	winners[ myHashCode(input.next() )] = true;
 	}
  
 	@Override
    public int myHashCode() {
 		int sum = 0; //invalid by default
		int move = -1; //hash value of the current character
		
		//if( (board.length()) == LENGTH ) { //make sure the board is valid
			for(int r = 0; r < TicTacToe.ROWS; r++) { //iterate through the rows 
				for(int c = 0; c < TicTacToe.COLS; c++) { //iterate through the cols 
					char current = (char) charAt(r, c); 	//Get hashCode of charAt(r, c)
					move = charCode(current); //get value of current char
				
					if(move >= 0) { //ensure it's valid
						int i = r * TicTacToe.COLS + c;
						sum += move * Math.pow(3, i); //The algorithm itself --use the visual rep w/2D array?
					} else return -2; //invalid char
				}
			}
			return sum; 
		// else invalid board return sum;
 	}
 	
 	// MY ADDED HELPER METHOD //
 	static int charCode(char x) {		
		//Use single quotes to indicate it's a char not a String
		if( x == ' ') return 0;
		if( x == 'x') return 1;
		if( x == 'o') return 2;
		return -1; //invalid char
	}
   
    public boolean isWin(String s) {
    // return the value in the winner array for the hash code of the board string sent in.
    return true;
    }
  
   public static void main(String[] args) throws InterruptedException {
      OLD_TicTacToeHashCode board = new OLD_TicTacToeHashCode ("Tic Tac Toe");
      while (true) {
      
         String currentBoard = board.boardValues[(int)(Math.random()* board.boardValues.length)];
         board.show(currentBoard);
         board.setHashCode(board.myHashCode());
         // TODO Update this line to call your isWin method.
         board.setWinner(TicTacToe.isWin(currentBoard));
         
         Thread.sleep(4000);      
      }
   }
   
   // FILE READING //
 
   public static Scanner openFile(String fname) {	
		File file = new File(fname);
		Scanner input = null;

		try {							//Check to see if the requested input file exists in the given directory
			input = new Scanner(file);	//If so, create a new Scanner to grab the data
		} catch (FileNotFoundException ex) {	//Else, print to console the reason why and output the reason why & part of program getting stuck
			//System.out.println("Unable to Open File: fname\n"); 
			return null;
		}
		return input;
	}
}