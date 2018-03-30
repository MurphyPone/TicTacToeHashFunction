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
		Scanner input = openFile("TicTacToeWinners.txt"); //Reads in file TicTacToeWinners.txt and returns a Scanner of all winning/valid game states
		while(input.hasNextLine()) { //Iterates through all the boards in the input file 
			cap++;
			winners.put(input.nextLine(), true);
		}
		input.close();
   }

   // TODO This method uses reflect to investigae the objects inside the HashMap
   // You should be able to update this with your information and determine 
   // Information about capacity (different than size()) and what is stored in the cells

   private int capacity() throws NoSuchFieldException, IllegalAccessException {
	   Field tableField = HashMap.class.getDeclaredField("table");
	   tableField.setAccessible(true);
	   Object[] table = (Object[]) tableField.get(winners);  //TODO put my hash map here I guess
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
	public void eval() throws NoSuchFieldException, IllegalAccessException {
		//Setup
		Field tableField = HashMap.class.getDeclaredField("table");
		tableField.setAccessible(true);
		Object[] table = (Object[]) tableField.get(winners);  //TODO put my hash map here I guess

		//Analysis
		for(Object board : table) {
			//DO STUFF HERE
			System.out.println(board);
		}
		
	}

	public static void main(String[] args) throws java.io.FileNotFoundException, NoSuchFieldException, IllegalAccessException {
		TicTacToeHashMap m = new TicTacToeHashMap();

		// TODO read in and store the strings in your hashmap, then close the file
  
		// TODO print out the capacity using the capcity() method
		System.out.println("Known Capacity: " + m.cap +"\nm's capacity: " + m.capacity() +"\n\n");
		// TODO print out the other analytical statistics as required in the assignment
		m.eval();
  
   }

}