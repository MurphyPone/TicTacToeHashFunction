import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//TODO Make sure you remove all of the TODO comments from this file before turning itin
public class TicTacToeHashCode extends Board {

	boolean [] winners;  // True if the hash string that maps to this index is a winner, false otherwise
 	static final int[] powsOf3 = {1, 3, 9, 27, 81, 243, 729, 2187, 6561, 19683}; //for quick referencing

	TicTacToeHashCode(String s) {
 		super(s);	//TODO ERROR --> SUPER CALLS myHashCode...
 		winners = new boolean[19638]; //BUT THIS ARRAY ISn't INITIALIZED YET...
 		//Read in file TicTacToeWinners.txt
 		Scanner input = openFile("TicTacToeWinners.txt");
 		while(input.hasNextLine()) { //while theres winners in the file
 			super.setBoardString(input.nextLine()); //get current file 
 			winners[myHashCode()] = true;  //set each winner[index] from the file to true
 			System.out.println( myHashCode() );
 		}
  }
  
 	// TODO - write the myHashCode function.  It must create a unique hashcode for all of the 
 	//   possible values the game board (3 ^ 9) and it MUST use the super.charAt(row, col) function
 	@Override
 	public int myHashCode() {
 		int sum = 0;	//invalid by default
 		int move = -1;	//hash value of the current character

		
 		//if( (board.length()) == LENGTH ) {	//make sure the board is valid
 		for(int r = 0; r < TicTacToe.ROWS; r++) {	//iterate through the rows 
 			for(int c = 0; c < TicTacToe.COLS; c++) {	//iterate through the cols 
 				char current = (char) charAt(r, c);		//Get hashCode of charAt(r, c)
				move = charCode(current);	//get value of current char
			
				if(move >= 0) {	//ensure it's valid
					int i = r * TicTacToe.COLS + c;	//convert from 2D matrix to 1D index
					sum += move * powsOf3[i];	//The algorithm itself //gets called in super.... from reverse inherited 
				} else return -2;	//invalid char
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
  
   public static void main(String[] args) throws InterruptedException {
      TicTacToeHashCode board = new TicTacToeHashCode ("Tic Tac Toe");
      while (true) {
    	  board.setBoardString("xxox xoo "); //TODO IS THIS ALLOWED?
         
    	  board.displayRandomString();
    	  board.setHashCodeLabel( board.myHashCode() );  //change the label to the current 
    	  board.setWinnerLabel( board.isWin() );
         
    	  Thread.sleep(10000); //4,000      
      }
   }

	@Override
	boolean isWin() {
		return winners[myHashCode()];
	}
}  
  