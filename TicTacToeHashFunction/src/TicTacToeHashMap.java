import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class TicTacToeHashMap  {

	private HashMap<String, Boolean> winners;
	private int actualCap; //capacity 
	private float lf;
	private int initialCap;

	TicTacToeHashMap() {
		initialCap = 2000;
		actualCap = 0;
		lf = 0.9f; //Learn more about floats and what this 'f' nonsense is 
		
		winners = new HashMap<String, Boolean>(initialCap, lf ); //create our HashMap with an initial capacity that exactly fits the amount of winning boards 
		//Potentially move this to a different method for the main???
		Scanner input = openFile("TicTacToeWinners.txt"); //Reads in file TicTacToeWinners.txt and returns a Scanner of all winning/valid game states
		while(input.hasNextLine()) { //Iterates through all the boards in the input file 
			actualCap++;  //Keep track of the actual size of the hashMap
			winners.put(input.nextLine(), true); //Add the entry from the winners file to the map with the value true
		}
		input.close();	
   }

   private int capacity() throws NoSuchFieldException, IllegalAccessException {
	   Field tableField = HashMap.class.getDeclaredField("table");
	   tableField.setAccessible(true);
	   Object[] table = (Object[]) tableField.get(winners);
	   return table == null ? 0 : table.length;   
   }
   
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
   
   // TODO using the same code to get the table of entries as in the capacity method,
   // create a method that will evaluate the table as directed in the assignment.
   // note - if an entry is not null, then it has a value, it may have more than one value
   // see if you can determine how many values it has.  Using the debugger will assist.
	public void analysis() throws NoSuchFieldException, IllegalAccessException {
		//Setup
		Field tableField = HashMap.class.getDeclaredField("table");
		tableField.setAccessible(true);
		Object[] table = (Object[]) tableField.get(winners); 
		
		// init values
		int cap = capacity();
		int empty = 0;
		int filled = 0;
		int numCollisions;
		int highest = 0;
		int lowest = table.length;
		int[] quarters = new int[4];
		int[] tenths = new int[10];
		
		for(int i = 0; i < table.length; i++) {
			int ind10 = (int) ((double) i/cap * 10); //index as percent --> index
			int ind4 = (int) ((double) ind10*10/25); //x/100 = y/4 --> y = x/24

 			if(table[i] == null) //If it's an empty slot
 				empty++;
 			else if(table[i] instanceof TreeNode ) { //Is a Collision
 				System.out.println("XXXSDKGNSDKGNSDKG");
 				filled++;
 				//Collision Info
 				tenths[ind10]++; //Distribution of Chains
 				if(i < lowest) lowest = i; //First entry in the table
 				if(i > highest) highest = i; //Highest entry in the table
	 		} else { //Is not a collision but is filled 
	 			filled++;
	 			if(i < lowest) lowest = i; //First entry in the table
				if(i > highest) highest = i; //Highest entry in the table
 			} //is not filled, but Do this stuff no matter what
 			//Distribution info //
			quarters[ind4]++; //distribution of quarters
 		}
	
		numCollisions = actualCap - filled;
		
		System.out.println("HashMap["+capacity()+"] created for " + actualCap + " boards with " + numCollisions + " collisions");
		System.out.println("loadFactor: " + lf );
		System.out.println(filled + "/" + cap + " slots filled --> " + empty + " Empty slots" );
		System.out.println("#collisions: " + numCollisions);
		System.out.println("Entries per Quarter: " + Arrays.toString(quarters) );
 		System.out.println("Collisions per Tenth: " + Arrays.toString(tenths) );
 		System.out.println("Lowest Index Entry: " + lowest + ", Highest index entry: " + highest);
 		System.out.println("Chain Info: ");
 		System.out.println("\tCollisions: " + numCollisions);
 		
		/*  Table[729] created for 1400 boards with 956 collisions
			loadFactor (numCollisions/SIZE) = 1.3113854595336076
			444 / 729 Slots filled  --> 285 Empty slots
			Entries per Quarter: [219, 146, 219, 145]
			Collisions per Tenth: [15, 36, 53, 22, 34, 54, 42, 36, 51, 43]
			Lowest Index Entry: 0, Highest index entry: 728
			Chain Info: 
				Collisions: 956
				Biggest Chain: 47
				Average Chain: 10.0
				Chains with > 37 collisions: 14
		 */
		
		/*Analysis
		for(Object board : table) {
			amt++;
			//DO STUFF HERE
			if(board == null) {
				empty++;
			} else {
				filled++;
			}
		} */
		
	}
	
	//Added method which accepts a string and returns whether or not it is a winner based off the given input winners file
	public boolean isWin(String board) {
		if(winners.get(board) != null)	//If the board exists in the hashmap (and is true) 
			return winners.get(board);
		else //Board DNE --> false
			return false;
	}

	public static void main(String[] args) throws java.io.FileNotFoundException, NoSuchFieldException, IllegalAccessException {
		TicTacToeHashMap m = new TicTacToeHashMap();
		// TODO print out the capacity using the capcity() method
		System.out.println("Capacity: " + m.capacity() );
		System.out.println(m.isWin("    xxooo")); 
		System.out.println(m.isWin(" assdfsdf"));

		// TODO print out the other analytical statistics as required in the assignment
		m.analysis();  
   }

}