import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class TicTacToeHashMap  {

	private HashMap<String, Boolean> winners;  
	private int s; //size
	private float lf; //The load factor is a measure of how full the hash table is allowed to get before its capacity is 
						//automatically increased. When the number of entries in the hash table exceeds the product of the 
						//load factor and the current capacity, the hash table is rehashed (that is, internal data structures are rebuilt) 
						//so that the hash table has approximately twice the number of buckets.
	private int initialCap; //#buckets

	TicTacToeHashMap() {
		initialCap = (int) Math.pow(2,  10); // = 1024
		s = 0;
		lf = 1.5f; //I 'want' collisions, so force them to happen by using an inverted loadfactor and prevent HashMap resizing 
		
		winners = new HashMap<String, Boolean>(initialCap, lf ); //create our HashMap with an initial capacity that exactly fits the amount of winning boards 
		//Potentially move this to a different method for the main???
		Scanner input = openFile("TicTacToeWinners.txt"); //Reads in file TicTacToeWinners.txt and returns a Scanner of all winning/valid game states
		while(input.hasNextLine()) { //Iterates through all the boards in the input file 
			s++;  //Keep track of the actual size of the hashMap
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
		int highest = 0;
		int lowest = table.length;
		int[] quarters = new int[4];
		int[] tenths = new int[10];
		//Chain info
		int numCollisions = 0;
		int biggestChain = 0;
		int sumChains = 0;
		double averageChain;
		int numBigChains = 0;
		int conditionForBig = 0;
 		ArrayList<Integer> chains = new ArrayList<Integer>(3 * table.length / 4); //ArrList to keep track of the actual chain sizes -- list because the initial size != numCollisions
		
		for(int i = 0; i < table.length; i++) {  //iterate through table instead of winners to show null entries
			int ind10 = (int) ((double) i/cap * 10); //index as percent --> index
			int ind4 = (int) ((double) ind10*10/25); //x/100 = y/4 --> y = x/24
						
 			if(table[i] == null) //If it's an empty slot
 				empty++;
 			else {
 				if(i < lowest) lowest = i; //First entry in the table
				if(i > highest) highest = i; //Highest entry in the table
 				
 				/* Thanks Patrick for this code */
 				Field next = table[i].getClass().getDeclaredField("next"); //store the private "next" field 
 				next.setAccessible(true);	//Allow access
 				Object o = (Object) next.get(table[i]);	//Get the value of the Field?
 				/* end Patrick's code */
 				
 				if(o != null ) { //Is a Collision
 					filled++;
 					//Collision Info
 					int b = 0; //# buckets (entries) in collision
 					while (o != null) {
 						b++; 
 						next = o.getClass().getDeclaredField("next");
 						next.setAccessible(true);
 						o = next.get(o);
 					}
 					if(b > biggestChain ) biggestChain = b; //Keep track of the biggest chain
 	 				if(b > 1 ) { 
 	 					chains.add(b); //Depth of chains	
 	 					sumChains += b; //sum all the chains for avg
 	 				}
 	 				tenths[ind10]++; //Distribution of Chains
 				} else { //Is not a collision but is filled 
 					filled++;	
 				}
 			} //is not filled, but Do this stuff no matter what
 			//Distribution info //
			quarters[ind4]++; //distribution of quarters
 		}
	
		numCollisions = s - filled;
 		averageChain = sumChains/chains.size(); 
 		conditionForBig = (int) (biggestChain - averageChain); //Big chains are chains that are > biggest-avg in length 
		
 		//Secondary sweep on data gathered from pass 1 
 		for(int i = 0; i < chains.size(); i++) {
 			if(chains.get(i) > conditionForBig) numBigChains++;
 		}
 		
		System.out.println("HashMap["+capacity()+"] created for " + s + " boards with " + numCollisions + " collisions");
		System.out.println("loadFactor: " + lf );
		System.out.println(filled + "/" + cap + " slots filled --> " + empty + " Empty slots" );
		System.out.println("#collisions: " + numCollisions);
		System.out.println("Entries per Quarter: " + Arrays.toString(quarters) );
 		System.out.println("Collisions per Tenth: " + Arrays.toString(tenths) );
 		System.out.println("Lowest Index Entry: " + lowest + ", Highest index entry: " + highest);
 		System.out.println("Chain Info: ");
 		System.out.println("\tCollisions: " + numCollisions);
 		System.out.println("\tBiggest Chain: "  +  biggestChain + "\n\tAverage Chain: " +  averageChain + "\n\tChains with > "+conditionForBig+" collisions: " + numBigChains);
 		System.out.println("All Chains: "  + chains.toString()); 		
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
		System.out.println("Capacity of TicTacToeHashMap: " + m.capacity());
		System.out.println("\n-----ANALYSIS-----\n");
		m.analysis();  
   }

}