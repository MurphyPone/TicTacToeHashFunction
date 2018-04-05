/**
 * Class Description: This class extends the JFrame class to visually display the contents of a TicTacToe game.
 * 					--Side note, I commented & completed the JavaDocs for this Class in class during one of the lab days we had for this assignment, 
 * 						but then a new version of this Class was uploaded...  So, yes, these JDocs are late, but only sort of.
 * @author KellyT and MurphyP1
 * @date 4/5/18
 **/
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

abstract class Board extends JFrame implements ActionListener {

	private JButton buttons[][];	//2D array of buttons to store the 9 'plays' of the TTT game 
	private JLabel lblHashCode;	//Displays the hashCode of the TTT game
	private JLabel lblWinTitle;	//Displays whether or not the current display is a winner/loser

	private String boardString = "";
	
	/**
	 * The default constructor for a Board which accepts a String which becomes the title of the GUI window
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method Board
	 * @param title the String which becomes the title of the GUI window
	 */
	public Board(String title) {
		super(title);
		setupFrame();
	}

	/**
	 * A setter method which accepts an integer value and changes the corresponding GUI label to that value.
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method setHashCodeLabel
	 * @param hashcode the integer to be displayed
	 * @return void
	 */
	public void setHashCodeLabel(int hashcode) {
		lblHashCode.setText("" + hashcode);
	}

	/**
	 * A setter method which accepts a String and changes the corresponding GUI label to that value.
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method setWinnerLabel
	 * @param result the String to be displayed
	 * @return void
	 */
	public void setWinnerLabel(String result) {
		lblWinTitle.setText(result);
	}

	/**
	 * A setter method which accepts a boolean and changes the corresponding GUI winner label to that "Winner" if true and "Loser" if false.
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method setWinnerLabel
	 * @param result the boolean to be evaluated
	 * @return void
	 */
	public void setWinnerLabel(boolean result) {
		if (result)
			setWinnerLabel("Winner");
		else
			setWinnerLabel("Loser");
	}

	/**
	 * A required abstract method which is unused by the Board
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method actionPerformed
	 * @param e an ActionEvent 
	 * @return void
	 */
	@Override 	// required because of abstract method, but not used
	public void actionPerformed(ActionEvent e) {
	}

	/**
	 * A helper method which configures the structure of the GUI according to the supplied title, hashcode of the Board, and win status.
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method setupPanelOne
	 * @return the resulting JPanel 
	 */
	JPanel setupPanelOne() {
		JPanel panel = new JPanel();
		JLabel lblHCTitle = new JLabel("Hash Code");
		;
		lblHashCode = new JLabel("" + myHashCode());
		lblWinTitle = new JLabel(""); // Will say either Winner or Loser
		setWinnerLabel(TicTacToe.isWin(boardString));
		panel.setLayout(new FlowLayout());
		panel.add(lblHCTitle);
		panel.add(lblHashCode);
		panel.add(lblWinTitle);
		return panel;
	}

	/**
	 * A helper method pulls information from the buttons array to visually display the TicTacToe game.
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method setupPanelTwo
	 * @return the resulting JPanel 
	 */
	private JPanel setupPanelTwo() {
		JButton b;
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(TicTacToe.ROWS, TicTacToe.COLS));
		buttons = new JButton[TicTacToe.ROWS][TicTacToe.COLS];

		int count = 1;

		for (int r = 0; r < TicTacToe.ROWS; r++)
			for (int c = 0; c < TicTacToe.COLS; c++) {
				char ch = randomXO();
				b = new JButton("" + ch);
				boardString += ch;
				b.setActionCommand("" + r + ", " + c);
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JButton btn = (JButton) e.getSource();
						btn.setText("" + cycleValue(btn.getText().charAt(0)));
						resetBoardString();
						setHashCodeLabel(myHashCode());
						setWinnerLabel(isWin());
					}
				});
				panel.add(b);
				buttons[r][c] = b;
			}

		return panel;
	}

	/**
	 * A helper method which acts as a 3-way toggle between the acceptable tokens of a TicTacToe game: 'x', 'o', or ' '. 
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method cycleValue
	 * @param ch a char representing the current character
	 * @return the next character in the cycle
	 */
	private static char cycleValue(char ch) {
		switch (ch) {
		case 'x':
			return 'o';
		case 'o':
			return ' ';
		default:
			return 'x';
		}
	}

	/**
	 * A helper method which initializes the the GUI and configures the content according the the JPanels returned by the 
	 * 		setupPanelOne and setupPanelTwo methods.
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method setupFrame
	 * @return void
	 */
	private void setupFrame() {
		JPanel panel2 = new JPanel();

		// Setup Frame
		super.setSize(250, 200);
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		// Setup Panels
		panel2 = setupPanelTwo(); // panelOne displays a value that requires panelTwo to be ready
		super.add(setupPanelOne());
		super.add(panel2);

		super.setVisible(true);
	}

	/**
	 * A helper method which chooses a random number and returns the corresponding character value for a TicTacToe game.
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method randomXO
	 * @return the corresponding character
	 */
	private char randomXO() {
		int rnd = (int) (Math.random() * TicTacToe.CHAR_POSSIBILITIES);
		switch (rnd) {
		case 1:
			return 'x';
		case 2:
			return 'o';
		default:
			return ' ';
		}
	}

	//ABSTRACT METHODS
	/**
	 * An abstract method to be used by the hashCode Classes.
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method myHashCode
	 * @return an integer representing the value of the current TicTacToe game configuration
	 */
	abstract int myHashCode();
	
	/**
	 * An abstract method which determines whether or not a given String corresponds to a valid winner or a loser Board configuration.
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method isWin
	 * @param s the String to be evaluated
	 * @return a boolean value representing the win/lose status
	 */
	abstract boolean isWin(String s);
	
	/**
	 * An abstract method which determines whether or not the current TicTacToe game configuration is a valid winner or a loser.
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method isWin
	 * @return a boolean value representing the win/lose status
	 */
	abstract boolean isWin();

	/**
	 * A helper method which accepts 2 indices for the buttons array and returns the character at that position
	 * 
	 * @author KellyT
	 * @date 4/5/18
	 * @method charAt
	 * @param row an integer representing the index for the 'row' of the buttons array 
	 * @param col an integer representing the index for the 'col' of the buttons array 
	 * @return the character at that position in the 2D buttons array
	 */
	public char charAt(int row, int col) {
		String value = buttons[row][col].getText();
		if (value.length() > 0)
			return value.charAt(0);
		else
			return '*';
	}
   
	/**
	 * A helper method which accepts 2 integers and a String which returns the character at the position which would correspond to that index
	 * 		combination in a 2D array
	 * 
	 * @author MurphyP1
	 * @date 4/5/18
	 * @method charAt
	 * @param s a String to be evaluated instead of the current Board configuration
	 * @param row an integer representing the index for the 'row' of the buttons array 
	 * @param col an integer representing the index for the 'col' of the buttons array 
	 * @return the character at that position in the 2D buttons array
	 */
   public char charAt(String s, int row, int col) {
     int pos = row * TicTacToe.COLS + col;	//2D --> 1D array index conversion formula
     if (s.length() >= pos)
       return s.charAt(pos);
     else
       return '*';   
   }
   
   /**
	 * A helper method which accepts a string of integer values which correspond to TicTacToe game 'plays': 'x', 'o', ' ', converts those 
	 * 		integers to their corresponding char representations and displays them on the Board GUI.
	 * 
	 * @author MurphyP1
	 * @date 4/5/18
	 * @method show
	 * @param s a String of integers to be evaluated and displayed
	 * @return void
	 */
	public void show(String s) {
		int pos = 0;
		String letter;
		for (int r = 0; r < TicTacToe.ROWS; r++)
			for (int c = 0; c < TicTacToe.COLS; c++) {
				char ch = s.charAt(pos);
				switch (ch) {
				case '1':
					letter = "x";
					break;
				case '2':
					letter = "o";
					break;
				case '0':
					letter = " ";
					break;
				default:
					letter = "" + ch;
				}
				buttons[r][c].setText(letter);
				pos++;
			}
	}

	/**
	 * A helper method which resets boardString to correspond to the currently displayed Board values 
	 * 
	 * @author MurphyP1
	 * @date 4/5/18
	 * @method resetBoardString
	 * @return void
	 */
	public void resetBoardString() {
		boardString = "";
		for (int r = 0; r < TicTacToe.ROWS; r++)
			for (int c = 0; c < TicTacToe.COLS; c++) {
				boardString += buttons[r][c].getText();
			}
	}

	/**
	 * A setter method which changes the boardString and updates the Board GUI to represent the new value
	 * 
	 * @author MurphyP1
	 * @date 4/5/18
	 * @method setBoardString
	 * @param s the String value which become the new boardString
	 * @return void
	 */
	public void setBoardString(String s) {
		boardString = s;
		show(s);
	}

	/**
	 * A getter method which returns the value of the current boardString
	 * 
	 * @author MurphyP1
	 * @date 4/5/18
	 * @method getBoardString
	 * @return the boardString
	 */
	public String getBoardString() {
		return boardString;
	}

	//resets the values of board to be random, new 
	/**
	 * A helper method which modifies the boardString and displayed buttons to new random values and updates the GUI accordingly
	 * 
	 * @author MurphyP1
	 * @date 4/5/18
	 * @method displayRandomString
	 * @return void
	 */
	public void displayRandomString() {
		for (int r = 0; r < TicTacToe.ROWS; r++)
			for (int c = 0; c < TicTacToe.COLS; c++) {
				char ch = randomXO();
				boardString += ch;
				buttons[r][c].setText("" + ch);
			}
		setHashCodeLabel(myHashCode());
		setWinnerLabel(isWin());
	}
}