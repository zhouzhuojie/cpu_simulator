/**
 * Alu
 * 
 * @author Zhuojie Zhou
 * @version  Have bugs here, need revise
 */
package arc_project;

public class Alu {

	private int _maxLength; // Set bound for the overflow
	
	public Alu(int _maxLength){
		this._maxLength = _maxLength;
	}
	
	/* Changed by Leen */
	/* Convert Binary into ONLY Positive Number */
	public int char2int(char[] c){
		int sum = 0;

		for (int i = 0; i < c.length; i++){
			if (c[i] == '1')
				sum = sum + (int)Math.pow(2, c.length-1-i);
		}

		return sum;
	}
	
	/* Changed by Leen */
	/* Convert Binary into positive or negative number (This is old char2int) */
	public int char2num(char[] c){
		int sum = 0;

		if (c.length == _maxLength){
		
			for (int i = 1; i < c.length; i++){
				if (c[i] == '1')
					sum = sum + (int)Math.pow(2, c.length-1-i);
			}

		if (c[0] == '1') // For the sign bit
			sum = 0 - sum;

		return sum;
		
		} else {
			return char2int(c);
		}
		
	}
	
	/* Changed by Leen */
	/* Convert ONLY Positive Number into Binary */
	public char[] int2char(int b) throws Exception{
		
		// Convert int bb to 0,1 char[]
		char[] barr = (Integer.toString(b,2)).toCharArray();
		
		// Overflow condition
		if (barr.length > _maxLength - 1)
			throw new Exception( "Overflow" );

		// Initialize char[] c
		char[] c = new char[_maxLength];
		for (int i = 0; i < c.length; i++){
			c[i] = '0';
		}

		// Copy the value of barr to char[] c
		for (int i = barr.length-1; i >= 0 ; i--){
			c[i+c.length-barr.length] = barr[i];
		} 
		
		return c;
		
	}
	
	/* Changed by Leen */
	/* Convert Positive or Negative Number into Binary (This is old int2char) */
	public char[] num2char(int b) throws Exception{
		
		// Copy int b to a positive int bb, in order to avoid the negative Integer.toString conversion
		int bb = b; 
		if (b < 0)
			bb = 0 - b;
		
		// Convert int bb to 0,1 char[]
		char[] barr = (Integer.toString(bb,2)).toCharArray();
		
		// Overflow condition
		if (barr.length > _maxLength - 1)
			throw new Exception( "Overflow" );

		// Initialize char[] c
		char[] c = new char[_maxLength];
		for (int i = 0; i < c.length; i++){
			c[i] = '0';
		}

		// Copy the value of barr to char[] c
		for (int i = barr.length-1; i >= 0 ; i--){
			c[i+c.length-barr.length] = barr[i];
		}
		
		// For the sign bit
		if (b < 0)
			c[0] = '1'; 
		
		return c;
		
	}
	
	/* Added by Leen: This will convert int into 2 words. It's particularly used for multiplication in ALU */
	public char[] num22char(int b) throws Exception{
		
		// Copy int b to a positive int bb, in order to avoid the negative Integer.toString conversion
		int bb = b; 
		if (b < 0)
			bb = 0 - b;
		
		// Convert int bb to 0,1 char[]
		char[] barr = (Integer.toString(bb,2)).toCharArray();
		
		// Overflow condition
		if (barr.length > 2*_maxLength - 2)
			throw new Exception( "Overflow" );

		// Initialize char[] c
		char[] c = new char[2*_maxLength];
		for (int i = 0; i < c.length; i++){
			c[i] = '0';
		}

		// Copy the value of barr to char[] c
		for (int i = barr.length-1; i >= 0 ; i--){
			c[i+c.length-barr.length] = barr[i];
		}
		
		// Separate into 2 words
		for (int i = 1; i < _maxLength; i++){
			c[i] = c[i+1];
		}
		
		// For the sign bit
		if (b < 0){
			c[0] = '1';
			c[_maxLength] = '1';
		}
		
		return c;
		
	}
	
	// char[] + char[]
	public char[] add( char[] RX, char[] RY ) throws Exception{
		return num2char((char2num(RX) + char2num(RY)));
	}
	
	// char[] + int
	public char[] add( char[] RX, int i) throws Exception{
		return num2char((char2num(RX) + i));
	}
	
	// char[] - char[]
	public char[] minus( char[] RX, char[] RY ) throws Exception{
		return num2char((char2num(RX) - char2num(RY)));
	}
	
	// char[] - int
	public char[] minus( char[] RX, int i) throws Exception{
		return num2char((char2num(RX) - i));
	}

	/* Changed by Leen */
	// char[] * char[]     *******Something have to be done to implete the MUL instruction*********
	public char[] multiply( char[] RX, char[] RY ) throws Exception{
		return num22char((char2num(RX) * char2num(RY)));
	}

	// char[] / char[]
	public char[] divide_quotient( char[] RX, char[] RY ) throws Exception{
		return num2char((char2num(RX) / char2num(RY)));
	}
	
	// char[] % char[]
	public char[] divide_remainder( char[] RX, char[] RY ) throws Exception{
		return num2char((char2num(RX) % char2num(RY)));
    }
	
	/* Added by Leen */
	
	// Equality
	public boolean equal( char[] RX, char[] RY ) throws Exception{
		for (int i = 0; i < 16; i++){
			if ( RX[i] != RY[i] ) return false;
		}
		return true;
	}
	
	// AND
	public char[] and( char[] RX, char[] RY ) throws Exception{
		char[] temp = new char[16];
		for (int i = 0; i < 16; i++){
			if ( RX[i] == '1' && RY[i] == '1' ){
				temp[i] = '1';
			} else {
				temp[i] = '0';
			}
		}
		return temp;
	}
	
	// OR
	public char[] or( char[] RX, char[] RY ) throws Exception{
		char[] temp = new char[16];
		for (int i = 0; i < 16; i++){
			if ( RX[i] == '0' && RY[i] == '0' ){
				temp[i] = '0';
			} else {
				temp[i] = '1';
			}
		}
		return temp;
	}	
	
	// NOT
	public char[] not( char[] RX ) throws Exception{
		char[] temp = new char[16];
		for (int i = 0; i < 16; i++){
			if ( RX[i] == '0' ){
				temp[i] = '1';
			} else {
				temp[i] = '0';
			}
		}
		return temp;
	}	
	
}