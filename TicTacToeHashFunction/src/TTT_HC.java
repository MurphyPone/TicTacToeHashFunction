import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TTT_HC{
	TreeNode[] winners; //The lookup table which needs to be smaller //holds TreeNodes<String>
 	private static final int[] powsOf3 = {1, 3, 9, 27, 81, 243, 729, 2187, 6561, 19683}; //for quick referencing
 	private static final int SIZE = powsOf3[8];
 	
 	//Constructor
 	TTT_HC() {
 		winners = new TreeNode[ powsOf3[8] ]; //6561 = 1/3rd of the size, nice!
 		//for(int i = 0; i < winners.length; i++) //Try initializing each of the TreeNodes first
 		//	winners[i] = new TreeNode("");  //place holder
 		int numCollisions = 0;
 		
 		Scanner input = openFile("TicTacToeWinners.txt"); //Reads in file TicTacToeWinners.txt and returns a Scanner of all winning/valid game states
 		while(input.hasNextLine() ) { //Iterates through all the boards in the input file 
 			String board = input.nextLine(); //Save the Board String 
 			int index = tttHashCode(board); //Get the index from the String
 			
 			if(winners[index] == null) //DNE 
 				winners[index] = new TreeNode(board); //Create
 			else {
 				winners[index] = winners[index].add(winners[index], board); //Insert
 				numCollisions++;
 			}
 
 		}
 		System.out.printf("Table created with %d collisions", numCollisions);
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
 	public void analyze() {
 		numCollisions = 0;
 		for(int i = 0; i < winners.length; i ++) {
 			
 		}
 	}
 	
 	public static void main(String[] args) throws InterruptedException {
		TTT_HC board = new TTT_HC();
		//System.out.println(board);
 	}

}
