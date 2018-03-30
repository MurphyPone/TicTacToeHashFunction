import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Scanner;

public class TicTacToeHashMap  {

	private HashMap<String, Boolean> winners;
	private int cap; //capacity 

	TicTacToeHashMap() {
		// TODO Instantiate/fill your HashMap ... pay attention to initial capacity and load values
		cap = 0;
		winners = new HashMap<String, Boolean>(1400); //create our HashMap with an initial capacity that exactly fits the amount of winning boards 
		//Potentially move this to a different method for the main???
		Scanner input = openFile("TicTacToeWinners.txt"); //Reads in file TicTacToeWinners.txt and returns a Scanner of all winning/valid game states
		while(input.hasNextLine()) { //Iterates through all the boards in the input file 
			cap++;
			winners.put(input.nextLine(), true);
		}
		input.close();
   }

   // TODO This method uses reflect to investigate the objects inside the HashMap
   // You should be able to update this with your information and determine 
   // Information about capacity (different than size()) and what is stored in the cells

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
		
		// analysis values
		int empty = 0;
		int filled = 0;
		int amt = 0;
		int numCollisions;

		//Analysis
		for(Object board : table) {
			amt++;
			//DO STUFF HERE
			if(board == null) {
				empty++;
			} else {
				filled++;
			}
		}
	
		numCollisions = cap - filled;
		
		System.out.println("HashMap["+capacity()+"] created for cap boards with " + numCollisions + " collisions");
		System.out.println("loadFactor: " + ((double) numCollisions/amt) );
		System.out.println(filled + "/" + amt + "slots filled --> " + empty + "Empty slots" );

		/* 
		 * Table[729] created for 1400 boards with 956 collisions
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
		
		System.out.println("#collisions: " + numCollisions);
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
		String good = " xo  o xo";
		String bad = " 1wglasjasnfxo";

		// TODO read in and store the strings in your hashmap, then close the file
  
		// TODO print out the capacity using the capcity() method
		//System.out.println("Known Capacity: " + m.cap +"\nm's capacity: " + m.capacity() );
		//System.out.println("Win/Lose for '"+good+"' + : " + m.isWin(good) );
		//System.out.println("Win/Lose for '"+bad+"' + : " + m.isWin(bad) );
		
		m.analysis();

		// TODO print out the other analytical statistics as required in the assignment
		//m.eval();
  
   }

}