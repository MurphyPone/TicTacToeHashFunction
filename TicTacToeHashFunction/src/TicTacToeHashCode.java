/**
 * Class Description: This class extends the Board class and acts as a utility to quickly evaluate the game state 
 * 						of TicTacToe Boards (winner or loser).
 * @author MurphyP1
 * @date 3/26/18
 **/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@SuppressWarnings("serial")
public class TicTacToeHashCode extends Board {

	private boolean[] winners; // True if the hash string that maps to this index is a winner, false otherwise
 	private static final int[] powsOf3 = {1, 3, 9, 27, 81, 243, 729, 2187, 6561, 19683}; //for quick referencing
 	
 	/**
	 * The default constructor for a TicTacToeHashCode which accepts a String which becomes the title of the Board GUI
	 * 
	 * @author MurphyP1
	 * @date 3/26/18
	 * @method TicTactoeHashCode
	 * @param s the String which becomes the title of the Board
	 */
 	TicTacToeHashCode(String s) {
		super(s);	
 		winners = new boolean[(int) Math.pow(3, 9)]; //Initialize the look up table for the Board states
 		Scanner input = openFile("TicTacToeWinners.txt"); //Reads in file TicTacToeWinners.txt and returns a Scanner of all winning/valid game states
 		while(input.hasNextLine()) { //Iterates through all the boards in the input file 
 			super.setBoardString(input.nextLine()); //Configures the Board to match the String from the file
 			winners[ myHashCode() ] = true;  //sets each value at the index generated by the myHashCode() method to true
 		}
 		input.close();
	}

	/**
	 * A method which pulls a String representing the Board's configuration and returns an int representing it's unique value 
	 * 								to be matched to the winners[] lookup table. 
	 * @author MurphyP1
	 * @date 3/26/18
	 * @method myHashCode
	 * @return the integer representing the index of the current game state in the winners[] lookup table
	 */
	@Override
	public int myHashCode() {
		int sum = 0;	//The value which is returned 
 		int move = -1;	//hash value of the current character

 		for(int r = 0; r < TicTacToe.ROWS; r++) {	//Iterates through the rows of the Board
 			for(int c = 0; c < TicTacToe.COLS; c++) {	//Iterate through the cols of the Board 
 				char current = (char) charAt(r, c);	//gets the current character of the Board at [r][c]	
				move = charCode(current);	//Gets the hashCode of the current character
			
				if(move >= 0) {	//Ensures it's valid character
					int i = r * TicTacToe.COLS + c;	//Convert from matrix index to 1D array index
					sum += move * powsOf3[i];	//The algorithm itself : the sum for all characters of (charCode * (3 ^ position) )
				} else return -2;	//invalid char
			}
		}
		return sum; 
	}
	
	/**
	 * A method which accepts a String representing a Board configuration and returns an int representing it's unique value 
	 * 								to be matched to the winners[] lookup table. 
	 * @author MurphyP1
	 * @date 3/26/18
	 * @method myHashCode
	 * @param s a String representing a 9 character Board configuration
	 * @return the integer representing the index of the current game state in the winners[] lookup table
	 */
	public int myHashCode(String s) {
		int sum = 0;	//The value which is returned 
 		int move = -1;	//hash value of the current character

 		for(int r = 0; r < TicTacToe.ROWS; r++) {	//Iterates through the rows of the Board
 			for(int c = 0; c < TicTacToe.COLS; c++) {	//Iterate through the cols of the Board 
 				char current = (char) charAt(s, r, c);	//gets the current character of the String 	
				move = charCode(current);	//Gets the hashCode of the current character
			
				if(move >= 0) {	//Ensures it's valid character
					int i = r * TicTacToe.COLS + c;	//Convert from matrix index to 1D array index
					sum += move * powsOf3[i];	//The algorithm itself : the sum for all characters of (charCode * (3 ^ position) )
				} else return -2;	//invalid char
			}
		}
		return sum; 
	} 
	
	// HELPER METHOD FOR myHashCode() //
	
	/**
	 * A helper method which accepts a character, checks if it is a valid Board token, and returns an integer representing its individual hashCode value 
	 * @author MurphyP1
	 * @date 3/26/18
	 * @method charCode
	 * @param x the character to be evaluated
	 * @return the integer representing the character's unique hashCode
	 */
 	static int charCode(char x) {
 		//Use single quotes to indicate it's a char not a String
 		if( x == ' ') return 0;
 		if( x == 'x') return 1;
 		if( x == 'o') return 2;
 		return -1; //invalid char
 	}

 	/**
	 * A method which accepts a String representing a Board configuration and returns whether or not it is a winning and valid configuration
	 * @author MurphyP1
	 * @date 3/26/18
	 * @method isWin
	 * @param s the String configuration of the Board
	 * @return true if the String corresponds to a winning Board, false if it corresponds to a losing/invalid Board
	 */	
 	public boolean isWin(String s) {
		 if( isValid(s) ) return winners[myHashCode(s)];
		 else return false;
		 //boolean result;
		 //try { result = winners[myHashCode(s)]; }	//if valid result = corresponding value
		 //catch( ArrayIndexOutOfBoundsException e ) { result = false; } //if Invalid, return false 
		 //return result;
	}
      
	/**
	 * A method which pulls a String from the Board and returns whether or not it is a winning and valid configuration
	 * @author MurphyP1
	 * @date 3/26/18
	 * @method isWin
	 * @return true if the Board configuration corresponds to a winning Board, false if it corresponds to a losing/invalid Board
	 */	
	@Override
	public boolean isWin() {
		return winners[myHashCode()];
    }
	
	// HELPER METHOD FOR VALIDATING BOARD CONFIGURATION //
	/**
	 * A method which accepts a string and ensure that it represents a valid Board configuration
	 * @author MurphyP1
	 * @date 3/26/18
	 * @method isValid
	 * @param s a String representing a Board configuration
	 * @return true if the String corresponds to a winning Board, false if it corresponds to a losing/invalid board
	 */	
	public static boolean isValid(String b) {
		char[] board = b.toCharArray();  //Convert String an char[] for easy iterations without substringing
		int numXs = 0;
		int numOs = 0;
		int numSpaces = 0; //Probably don't need this
		
		if(board.length  < 9 || board.length > 9) return false;  //Boards should only be 9 chars long
		
		for(int i = 0; i < board.length; i++ ) { //Iterate through the board to count number of different moves
			if( charCode(board[i]) == 0 ) numSpaces++;
			if( charCode(board[i]) == 1 ) numXs++;
			if( charCode(board[i]) == 2 ) numOs++;
		}
		//Check ways a Board can be invalid
		if(numSpaces > 4) return false; //A winning board has at least 5 moves on it xxx oo
		if( (numXs < 3 && numOs < 2) || (numXs < 2 && numOs < 3) ) return false; //Not enough plays --redundant check 
		if(Math.abs(numXs - numOs) > 1) return false;  //Two more Xs or Os than the other 
		
		return true; //If it passes everything 
	}
	
	// FILE READING //
 	
	/**
	 *	A helper method which returns a Scanner from a supplied fileName
	 *  @author MurphyP1
	 *  @date 3/26/18
	 *  @method openFile
	 *  @param fname a String which can be used to try to open initialize a Scanner
	 *  @return a Scanner from the supplied fileName or null if the file name was invalid 
	 */
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
	
	// OUTPUT AND TESTING // 
 	
 	/**
	 *	A helper method which accepts an input String as a filename and an int representing how long the program should 
	 *					wait before cycling to the next board configuration in the supplied file
	 *  @author MurphyP1
	 *  @date 3/26/18
	 *  @method printWinners
	 *  @param input a String which can be used to try to open initialize a Scanner
	 *  @param interval an int representing how many ms the program should wait before moving to the next Board config in the file
	 *  @return a String with information about the Boards pulled from the input file
	 *  @throws InterruptedException
	 */
	public String printWinners(String input, int interval) throws InterruptedException {
		String results = "";
		System.out.println("---Results from " + input + "---\n");
		Scanner in = openFile(input);
		
		if(in != null) {		//If input file was created successfully 
	 		while(in.hasNextLine()) { //Iterate through the input
	 			String board = in.nextLine(); //Get current Board config
	 			//Output the result
	 			if( isValid(board) ) { 
	 				updateGUI(board);  //Modify the Board to display the new info
	 				Thread.sleep(interval); //wait for (interval) milliseconds
	
	 				if(winners[myHashCode(board)]) //Valid winner
	 					results += "\t(" + board + ", " + myHashCode(board) + ") = winner\n";
	 				else //Valid loser
	 					results += "\t(" + board + ", " + myHashCode(board) + ") = loser\n";
	 			} else //Invalid --> loser 
	 				results += "\t(" + board + ", invalid" + ") = loser\n";
	 		}
		} else { results = "Invalid input file"; } //File does not exist
 		return results += "\n";
	}
	
	/**
	 *	A helper method for the isValid method which updates the Board's relevant visual components 
	 *  @author MurphyP1
	 *  @date 3/26/18
	 *  @method updateGUI
	 *  @param s a String representing a Board configuration
	 */
	private void updateGUI(String s) {
		setHashCodeLabel( myHashCode(s) );
		setWinnerLabel( isWin(s) );
		show(s);
	}
     
	/**
	 *	The main method which runs a series of tests involving hashCodes of the instance Board and Strings representing Board configuration
	 *  @author MurphyP1
	 *  @date 3/26/18
	 *  @method main
	 *  @param args and array of Strings holding the arguments passed in through the command line
	 *  @throws InterruptedException  
	 */
	public static void main(String[] args) throws InterruptedException {
		TicTacToeHashCode board = new TicTacToeHashCode("Peter's Tic Tac Toe");
		//System.out.println(board.isWin("skjfngkjsnfs")); //Test invalid isWin calls
		System.out.print( board.printWinners("TicTacToeWinners.txt", 2) ); //Cycle through the control file: TicTacToeWinners.txt
		System.out.print( board.printWinners("TTT_Tests.txt", 4000) ); //Cycle through my test file: TTT_Tests.txt //TODO ADD MORE TESTS
		System.out.print( board.printWinners("InvalidFilename.txt", 20) ); //Test an invalid file 
	}
}
