/**
 * Class Description: This class represents a game of Tic Tac Toe and contains many (slOw) methods which determine the current win/lose status of the game
 * 					--Side note, I also commented & completed the JavaDocs for this Class in class during one of the lab days we had for this assignment, 
 * 						but then a new version of this Class was uploaded...  So, yes, these JDocs are late, but only sort of.
 * @author KellyT
 * @date 4/5/18
 **/
import java.util.Arrays;

public class TicTacToe {
	public final static int ROWS = 3;	//The dimensions of the game
	public final static int COLS = 3;
	public final static int POSSIBILITIES = (int) Math.pow(3,9);
	public final static int CHAR_POSSIBILITIES = 3; // x, o or space
  
	/**
	 * A helper method which accepts a 2D array of characters and a single character and returns the number of occurrences of that 
	 * 		character in the current game.
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method numChars
	 * @param b the 2D array of characters to be evaluated
	 * @param ch a character to search for in the 2D array
	 * @return an integer representing the number of occurrences in that game
	 */
  	private static int numChars(char[][] b, char ch) {
  		int total = 0;
		for (int r = 0; r < ROWS; r++)
			for (int c = 0; c < COLS; c++)
				if (ch == b[r][c]) 
					total++;
		return total;
  	}
  
  	/**
	 * A helper method which determines whether or not the current game is valid according to the number of plays
	 * 
	 * @author KellyT and MurphyP1
	 * @date 4/5/18
	 * @method valid
	 * @param board the 2D array of characters representing the current game configuration
	 * @return an integer representing the number of occurrences in that game
	 */
  public static boolean valid(char[][] board) { //Would need more cases if there are more than 2 players 
  // Ensure there are at least 3 xs and 2 os, or 3 os and 2 xs
  // Make sure there are at least one more x or one more o
	  int numX = numChars(board, 'x');
	  int numO = numChars(board, 'o');
	  
	  if (! (numX > 2 || numO > 2)) { return false; } // >3
	  if ((numX == numO + 1) || (numO == numX + 1)) { return true; }  
	  return false;
  }
  
  	/**
	 * A helper method which converts a 2D array of characters into a single String
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method boardToString
	 * @param b the 2D array of characters representing the current game configuration which are converted to a single String
	 * @return the concatenation of all the values of b
	 */
   public static String boardToString(char[][] b) {
     String result = "";
     for (int r = 0; r < ROWS; r++) {
       for (int c = 0; c < COLS; c++) 
         result += b[r][c];
       	//result += "\n";	//Uncomment for readable board output --note this would break the whole darn program
       }
     return result;
   }
   
   /**
	 * A helper method which converts a String to a 2D array of characters 
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method StringToBoard
	 * @param board the String representing the current game configuration which is converted to a 2D array of characters
	 * @return the 2D array of characters 
	 */
   public static char[][] stringToBoard(String board) {
	   char[][] b = new char[ROWS][COLS];
	   int index = 0;
	   for (int r = 0; r < ROWS; r++) {
		   for (int c = 0; c < COLS; c++) 
			   b[r][c] = whichLetter(board.charAt(index++));
       	}
	   return b;
   }

   /**
	 * A helper method which converts a integer represented as a character to its corresponding TicTacToe token: 'x', 'o', or ' '.
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method whichLetter
	 * @param ch the character to be evaluated
	 * @return the character representing the TicTacToe token
	 */
   public static char whichLetter(char ch) {
	   switch (ch) {
   		case '1' : return 'x';
        case '2' : return 'o';
        case '0'  : return ' ';
        default: return ch;
       }
   }
    
   /**
	 * A helper method which converts several integers represented as a single String to its corresponding game configuration
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method makeBoard
	 * @param s the String to be converted
	 * @return a 2D array of characters representing the board configuration
	 */
   public static char[][] makeBoard(String s) { //Accepts a string : '101200102' --> [ ][ ][x][x][ ][x][o][x][o][o]
	   char[][] b = new char[ROWS][COLS];
	   int ch = 0;
	   for (int r = 0; r < ROWS; r++)
		   for (int c = 0; c < COLS; c++){         
			   b[r][c] = whichLetter(s.charAt(ch));
			   ch++;
		   }
	   return b;
   }
   
   /**
	 * A method which accepts a 9 character String and adds one to the last character, adjusting all the rest of the characters as necessary.
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method addOne
	 * @param s a 9 character string, composed of 0s, 1s, and 2
	 * @return the new String
	 */
   private static String addOne(String s) {
	   boolean carry = false;
	   char ch[] = s.toCharArray();
	   ch[ch.length-1] =  (char)((int)(ch[ch.length-1])+1);
	   for (int n = ch.length-1; n >=0; n--) {
		   if (carry) 
			   ch[n] = (char)((int)(ch[n])+1);
		   if (ch[n] == '3') {
			   carry = true;
			   ch[n] = '0';
		   } else
			   carry = false;      
      }
      return new String(ch);
   }
   
   /**
	 * A method which creates an array of Strings holding all possible game configurations
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method fillValues
	 * @return the array of all possible game configurations
	 */
   public static String[] fillValues() {
	   String strBoard = "000000000";
	   String[] values = new String[POSSIBILITIES];
	   int index = 0;
	   values[index++] = strBoard;
	   while (!strBoard.equals("222222222") ) {
		   strBoard = addOne(strBoard);
		   values[index++] = strBoard;
      }
	   return values;
   }
   
   /**
	 * A method which determines if a given board configuration represented by a 2D array of characters contains a diagonal win.
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method diagonalWin
	 * @param board the 2D array of characters to be evaluated
	 * @return true if yes, false if no
	 */
   private static boolean diagonalWin(char[][] board) {
	   if ((board[0][0] == 'x' && board[1][1] == 'x' && board[2][2] == 'x') || 
		   (board[0][0] == 'o' && board[1][1] == 'o' && board[2][2] == 'o')) {
		   return true;
     	} else 
 		if ((board[0][2] == 'x' && board[1][1] == 'x' && board[2][0] == 'x') ||
			(board[0][2] == 'o' && board[1][1] == 'o' && board[2][0] == 'o')) {
 			return true;
        }
     return false;
   }
   
   /**
	 * A method which determines if a given board configuration represented by a 2D array of characters contains a horizontal win.
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method rowWin
	 * @param board the 2D array of characters to be evaluated
	 * @return true if yes, false if no
	 */
   private static boolean rowWin(char[][] board) {
	   char ch;
	   for (int r = 0; r < ROWS; r++) {
		   ch = board[r][0];
		   for (int c = 0; c < COLS; c++) 
			   if (ch != board[r][c]) return false;
    		} 
        return true;
   } 
   
   /**
	 * A method which determines if a given board configuration represented by a 2D array of characters contains a vertical win.
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method colWin
	 * @param board the 2D array of characters to be evaluated
	 * @return true if yes, false if no
	 */
   private static boolean colWin(char[][] board) {
	   char ch;
	   for (int c = 0; c < COLS; c++) {
		   ch = board[0][c];
		   for (int r = 0; r < ROWS; r++) 
			   if (ch != board[r][c]) return false;
    		} 
        return true;
   } 

   /**
	 * A method which determines if a given board configuration represented by a 2D array of characters contains a win.
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method isWin
	 * @param b the 2D array of characters to be evaluated
	 * @return true if yes, false if no
	 */
   public static boolean isWin(char[][]b) {
	   return valid(b) && (rowWin(b) || colWin(b) || diagonalWin(b));
   }
   
   /**
	 * A method which determines if a given board configuration represented by a String contains a win by converting it to a 2D array of 
	 * 		characters and evaluating that.
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method isWin
	 * @param s the String to be converted and evaluated
	 * @return true if yes, false if no
	 */
   public static boolean isWin(String s) {
	   char[][] b = stringToBoard(s);
	   return isWin(b);
   }
}
