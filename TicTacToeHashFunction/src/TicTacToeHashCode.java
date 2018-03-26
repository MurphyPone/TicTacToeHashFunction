import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//TODO Make sure you remove all of the TODO comments from this file before turning itin

public class TicTacToeHashCode extends Board {

	boolean[] winners; // True if the hash string that maps to this index is a winner, false otherwise
 	static final int[] powsOf3 = {1, 3, 9, 27, 81, 243, 729, 2187, 6561, 19683}; //for quick referencing

 	TicTacToeHashCode(String s) {
		super(s);	
 		winners = new boolean[(int) Math.pow(3, 9)]; 
 		//Read in file TicTacToeWinners.txt
 		Scanner input = openFile("TicTacToeWinners.txt");
 		while(input.hasNextLine()) { //while theres winners in the file
 			super.setBoardString(input.nextLine()); //get current file 
 			winners[myHashCode()] = true;  //set each winner[index] from the file to true
 		}
	}

	@Override
	public int myHashCode() {
		int sum = 0;	//invalid by default
 		int move = -1;	//hash value of the current character

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
	}
	
	//hashcode that accepts a string for isWin()
	public int myHashCode(String s) {
		int sum = 0;	//invalid by default
 		int move = -1;	//hash value of the current character

 		for(int r = 0; r < TicTacToe.ROWS; r++) {	//iterate through the rows 
 			for(int c = 0; c < TicTacToe.COLS; c++) {	//iterate through the cols 
 				char current = (char) charAt(s, r, c);		//Get hashCode of charAt(s, r, c)  --ONLY DIFFERENCE FROM no args hashcode()
				move = charCode(current);	//get value of current char
			
				if(move >= 0) {	//ensure it's valid
					int i = r * TicTacToe.COLS + c;	//convert from 2D matrix to 1D index
					sum += move * powsOf3[i];	//The algorithm itself //gets called in super.... from reverse inherited 
				} else return -2;	//invalid char
			}
		}
		return sum; 
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

	@Override //Accepts a String
	public boolean isWin(String s) {
		System.out.println(s);
		return winners[myHashCode(s)];
	}
      
	@Override
	public boolean isWin() {
		return winners[myHashCode()];
    }
	
	public boolean isValid(String b) {
		char[] board = b.toCharArray();
		int numXs = 0;
		int numOs = 0;
		int numSpaces = 0;
		
		if(board.length  < 9 || board.length > 9 )return false;  //Boards should only be 9 chars long
		
		for(int i = 0; i < board.length; i++ ) { //Iterate through the board to count num plays
			if( charCode(board[i]) == 0 ) numSpaces++;
			if( charCode(board[i]) == 1 ) numXs++;
			if( charCode(board[i]) == 2 ) numOs++;
		}
		
		if(numXs < 3 && numOs < 3) return false; //not enough plays --> could check numSpaces
		if(Math.abs(numXs - numOs) > 1) return false;  //two more Xs or Os than the other 
		
		return true; //If it passes everything 
	}
	
	//Part I.3  //TODO ISSUE HERE WITH STATIC METHOD
	public String printWinners(String input) {
		String results = "";
		System.out.println("Results from " + input + "\n");
		Scanner in = openFile(input);
 		while(in.hasNextLine()) { //while theres winners in the file
 			String board = in.nextLine();
 			if(isValid(board))
 				results += board + " : " + myHashCode(board);
 			else 
 				results += board + " : invalid";
 		}
 		return results;
	}
     
	public static void main(String[] args) throws InterruptedException {
		TicTacToeHashCode board = new TicTacToeHashCode("Tic Tac Toe");
		System.out.print( printWinners("TTT_Tests.txt") ); //Default file TTT_Tests.txt
		
		 while (true) {
		   board.displayRandomString();
		   Thread.sleep(15000); //4 seconds usually 
		 } 
	}

}
