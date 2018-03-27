import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TTT_HC{
	TreeNode[] winners; //The lookup table which needs to be smaller //holds TreeNodes<String>
 	private static final int[] powsOf3 = {1, 3, 9, 27, 81, 243, 729, 2187, 6561, 19683}; //for quick referencing
 	private static final int SIZE = powsOf3[6];
 	
 	int numCollisions; //used for analysis
	int numBoards;
 	
 	//Constructor
 	TTT_HC() {
 		winners = new TreeNode[ SIZE ]; //729
 		numCollisions = 0;
 		numBoards = 0;
 		
 		Scanner input = openFile("TicTacToeWinners.txt"); //Reads in file TicTacToeWinners.txt and returns a Scanner of all winning/valid game states
 		while(input.hasNextLine() ) { //Iterates through all the boards in the input file 
 			numBoards++; //increase the numBoards --> probably check isValid before adding it 
 			String board = input.nextLine(); //Save the Board String 
 			int index = tttHashCode(board); //Get the index from the String
 			
 			if(winners[index] == null) //If the entry does not exist
 				winners[index] = new TreeNode(board); //Create one
 			else {	//Else the entry exists
 				winners[index] = winners[index].add(winners[index], board); //Insert the colliding value 
 				numCollisions++;
 			}
 		}
 		System.out.printf("Table created with %d collisions\n", numCollisions);
	}

	public int tttHashCode(String s) {
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
		return sum % SIZE; //distribute values across the new array 
	}
	
	// CHAR HELPER METHODS //
 	static int charCode(char x) {
 		//Use single quotes to indicate it's a char not a String
 		if( x == ' ') return 0;
 		if( x == 'x') return 1;
 		if( x == 'o') return 2;
 		return -1; //invalid char
 	}
 	
 	public char charAt(String s, int row, int col) {
 	     int pos = row * TicTacToe.COLS + col;
 	     if (s.length() >= pos)
 	       return s.charAt(pos);
 	     else
 	       return '*';   
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
 	
 	//Analysis 
 	public void analyze() { //TODO Add analysis for tenths
 		int empty = 0;
 		int Q1 = 0;
 		int Q2 = 0;
 		int Q3 = 0;
 		int Q4 = 0;
 		int lowest = winners.length;
 		int highest = 0;
 		
 		for(int i = 0; i < winners.length; i++) {
 			if(winners[i] == null) //If it's an empty slot
 				empty++;
 			else { 
 				if(i < lowest) lowest = i; //First entry in the table
 				if(i > highest) highest = i; //Highest entry in the table
 				if(i < SIZE/4) Q1++; //in First quartile
 				if(i > SIZE/4 && i < SIZE/2) Q2++; //in second quartile
 				if(i > SIZE/2 && i < (3*SIZE)/4) Q3++; //in third quartile
 				if(i > (3*SIZE)/4) Q4++; //in fourth quartile
 			}
 		}
 		System.out.println("Empty slots : " + empty);
 		System.out.println("#slots filled = " + (Q1 + Q2 + Q3 + Q4) + " / " + SIZE ); 
 		System.out.println("\tQ1 filled : " + Q1);
 		System.out.println("\tQ2 filled : " + Q2);
 		System.out.println("\tQ3 filled : " + Q3);
 		System.out.println("\tQ4 filled : " + Q4);
 		System.out.println("#Boards = " + numBoards);
 		System.out.println("#collisions = slots filled - #winners = " + ( numBoards -(Q1 + Q2 + Q3 + Q4) ) + "\n\tfrom constructor = " + numCollisions);
 		System.out.println("Lowest: " + lowest + ", highest: " + highest);
 	}
 	
 	public static void main(String[] args) throws InterruptedException {
		TTT_HC board = new TTT_HC();
		board.analyze();
 	}

}
