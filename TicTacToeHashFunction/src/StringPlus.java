public class StringPlus {
	private String value;
	private int SIZE;
	
	StringPlus(String val) { value = val; SIZE = 1024; }
	StringPlus(String val, int s) { value = val; SIZE = s;}

	@Override
	public int hashCode() {
		int sum = 0;
		char current;
		int move;
		
		for(int i = 0; i < value.length(); i++) {
			current = value.charAt(i);
			move = charCode(current);
			sum += move * Math.pow(3, i);
		}
		
		return sum % SIZE;
	}
	
	static int charCode(char x) {
 		//Use single quotes to indicate it's a char not a String
 		if( x == ' ') return 0;
 		if( x == 'x') return 1;
 		if( x == 'o') return 2;
 		return -1; //invalid char
 	}
	
	@Override
	public boolean equals(Object x) {
		if(x instanceof String)
			return value.equals( (String) x); 
		else return false;
	}
}
