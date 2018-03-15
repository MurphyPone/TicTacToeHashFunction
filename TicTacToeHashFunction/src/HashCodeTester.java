
public class HashCodeTester {

	private static final int LENGTH = 9; // length of the expected string 
	private static String max = "ooooooooo";
	private static String min = "         ";
	private static String med = " ox x o x";	
	private static String len = " ox x ox";	//invalid length test
	private static String car = " ox x oxz";	 //invaid char test
	
	public static void main(String[] args) {
		System.out.println("hashFunction max val: " + hashFunction(max));
		System.out.println("hashFunction min val: " + hashFunction(min));
		System.out.println("hashFunction med val: " + hashFunction(med));
		System.out.println("hashFunction len val: " + hashFunction(len));
		System.out.println("hashFunction car val: " + hashFunction(car));
	}
	
	
	//TicTacToeHashCode class, use the inherited boardValues[n] object
	static int hashFunction(String board) {
		int sum = 0; //invalid by default
		int move = -1; //hash value of the current character
		
		if( (board.length()) == LENGTH ) { //make sure the board is valid
			for(int i = 0; i < LENGTH; i++) { //iterate through the moves 
				char current = (char) board.charAt(i); 	//Get char val TODO use board.charAt
				move = charCode(current); //get value of current char
				
				if(move >= 0) //ensure it's valid
					sum += move * Math.pow(3, i); //The algorithm itself (without modulo)
				else return -2; //invalid char
			}
		} else 
			return -1; //invalid board
		return sum;
	}
	
	static int charCode(char x) {		
		//Use single quotes to indicate it's a char not a String
		if( x == ' ') return 0;
		if( x == 'x') return 1;
		if( x == 'o') return 2;
		return -1; //invalid char
	}
}
