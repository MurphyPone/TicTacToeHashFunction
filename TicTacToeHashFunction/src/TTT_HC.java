import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class TTT_HC{
	TreeNode[] winners; //The lookup table which needs to be smaller //holds TreeNodes<String>
 	private static final int[] powsOf3 = {1, 3, 9, 27, 81, 243, 729, 2187, 6561, 19683}; //for quick referencing
 	private static final int SIZE = powsOf3[7]; //6 or 7 are probably optimal
 	
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
 	
 	// ANALYSIS //
 	/** 
 	 * b. Report the size of your array and the load factor.
 	 * c. Report the number of collisions.
 	 * 		1. the number of chains (more than 1 entry)
 	 * 		2. the maximum chain length
 	 * 		3. the average chain length
 	 * d. Report on the distribution
 	 * 		i. Number of entries in each quarter of the array
 	 *		ii. Number of collisions in each tenth of the array
 	*/
 	public void analyze() {
 		int empty = 0; //number of empty slots in winners]
 		int filled;
 		//Distribution
 		int[] quarters = new int[4];
 		int[] tenths = new int[10];
 		
 		int lowest = winners.length;	//Lowest index entry
 		int highest = 0;	//Highest index entry
 		//Chain info 
 		int biggestChain = 0;
 		int sumChains = 0;
 		double averageChain;
 		int numBigChains = 0; //Big chains are greater contain > 10 collisions 
 		int conditionForBig= 0; //The condition for how many big chains there are = the 
 		int chains[] = new int[numCollisions]; //added to keep track of the actual chain sizes  
 		int chainIndex = 0;
 		
 		for(int i = 0; i < winners.length; i++) {
			int ind10 = (int) ((double) i/SIZE * 10); //index as percent --> index
			int ind4 = (int) ((double) ind10*10/25); //x/100 = y/4 --> y = x/24

 			if(winners[i] == null) //If it's an empty slot
 				empty++;
 			else if(winners[i].hasChildren()) {//Is a Collision
 				//Collision Info
 				int b = getSize(winners[i], 0); //store size of current chain in var 
 				sumChains += b; //Sum all the chains for average
 				if(b > biggestChain ) biggestChain = b; //Keep track of the biggest chain
 				chains[chainIndex] = b; //Depth of chains	
 				tenths[ind10]++; //Distribution of Chains
	 		} else { //Is not a collision but is filled 
 				//Collision info 
	 			sumChains++; //If there is a node, it is technically a chain of length 1 
	 			chains[chainIndex]++;
 			} //Do this stuff no matter what
 			//Distribution info //
			if(i < lowest) lowest = i; //First entry in the table
			if(i > highest) highest = i; //Highest entry in the table
			//Entries/quarters
			quarters[ind4]++;	
 			System.out.println(chains.length + ", " + chainIndex + ", " + numCollisions);

			chainIndex++;
 		}
 		
 		averageChain = (double) sumChains/numCollisions; 
 		filled = SIZE - empty;
 		conditionForBig = (int) (biggestChain - averageChain); //Big chains are chains that are > biggest-avg in length 
 		
 		//Secondary sweep on data gathered from pass 1 
 		for(int i = 0; i < chains.length; i++) {
 			if(chains[i] > conditionForBig) numBigChains++;

 		}
 		
 		//These could be cleaned up/moved to helper methods, but it's not too bad  
 		System.out.printf("Table["+SIZE+"] created for "+numBoards+" boards with %d collisions\n", numCollisions);
 		System.out.println( filled + " / " + SIZE + " Slots filled " + " --> " + empty + " Empty slots" ); 
 		System.out.println("Entries per Quarter: " + Arrays.toString(quarters) );
 		System.out.println("Collisions per Tenth: " + Arrays.toString(tenths) );
 		System.out.println("Lowest Index Entry: " + lowest + ", Highest index entry: " + highest);
 		System.out.println("Chain Info: ");
 		System.out.println("\t#collisions = slots filled - #winners = " + ( numBoards - filled ) + "\n\tfrom constructor = " + numCollisions);
 		System.out.println("\tBiggest : "  +  biggestChain + "\n\tAverage: " +  averageChain + "\n\t#Chains with > "+conditionForBig+" collisions: " + numBigChains);
 		System.out.println("All Chains: "  + Arrays.toString(chains));
 	}
 	
 	// ANALYSIS HELPERS //
 	private static int getSize(TreeNode root, int soFar) {
 		if(root == null) 
 			return soFar;
 		else 
 			return getSize(root.getLeft(), soFar + 1) + getSize(root.getRight(), soFar + 1);
 	}
 	
 	public static void main(String[] args) throws InterruptedException {
		TTT_HC board = new TTT_HC();
		board.analyze();
 	}
}
