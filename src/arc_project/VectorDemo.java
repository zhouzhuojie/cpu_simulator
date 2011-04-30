/**
 * 
 */
package arc_project;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @author zzj
 *
 */
public class VectorDemo {

	/*************************************************************************
	 * Part2_Cache_Memory                                                    *
	 *                                                                       *
	 * Authors: Zhuojie Zhou and Leenarat Leelapanyalert                     *
	 *                                                                       *
	 * Input  : none                                                         *
	 *                                                                       *
	 * return : void                                                         *
	 *                                                                       *
	 * Part 2 - perform cache memory                                         *
	 *************************************************************************/
	public void demo() throws Exception
	{
		/*************************************************************************
		 * Memory preparation for the VADD instruction.                          *
		 * Read from a txt file.												 *
		 * The file's format is "16bits 16bits" separated by space.              *
		 *************************************************************************/
		Global.GUIMAIN.outToConsole("Loading 'program_file/VectorData.txt' " + "\n");
		
		char[] address_V1 = {'0','0','0','0','0','0','0','0','1','0','0','0','0','0','0','0'};
		char[] address_V2 = {'0','0','0','0','0','0','0','1','0','0','0','0','0','0','0','0'};
		int k = 0;
		
		String s;
		while (null != (s = getLine0()) ){

			String v1 = s.split(" ")[0];
			String v2 = s.split(" ")[1];
			Global.GUIMAIN.outToConsole("V1["+k+"]: " + v1 + "   V2["+k+"]: " + v2 + "\n");
			char[] w1 = v1.toCharArray();
			char[] w2 = v2.toCharArray();
			
			Global.L1.set(Global.ALU.int2char(k+Global.ALU.char2int(address_V1)), w1);
			Global.L1.set(Global.ALU.int2char(k+Global.ALU.char2int(address_V2)), w2);
			k++;
		}
		char[] address_EA = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};
		char[] address_EA_Plus1 = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','1'};
		Global.L1.set(address_EA, address_V1);
		Global.L1.set(address_EA_Plus1, address_V2);
		
		
		/*************************************************************************
		 * Calculation for VADD instruction                                      *
		 *************************************************************************/
		String IR = "1000110000000000";
		Global.IR.set(IR.toCharArray());
		Instruction.deCode();
		Global.GUIMAIN.outToConsole("VADD results are: " + "\n");
		for (k=0;k<32;k++){
			String tmp = new String(Global.L1.get(Global.ALU.int2char(k+Global.ALU.char2int(address_V1))));
			Global.GUIMAIN.outToConsole("V1["+k+"]: "+ tmp + "\n");
		}
		Global.GUIMAIN.outToConsole("Cache record has been saved in 'program_file/cacheRecord.txt' " + "\n");

	}
	
	public String getLine0() {
		try {
			// Open the file that is the first
			// command line parameter

			if (Global.fstream0 == null) {
				Global.fstream0 = new FileInputStream("program_file/VectorData.txt");
				// Data format is "int int" at line #i, stand for V1(i) V2(i)
				// Get the object of DataInputStream

				DataInputStream in = new DataInputStream(Global.fstream0);
				Global.br0 = new BufferedReader(new InputStreamReader(in));
			}
			String strLine;
			// Read File Line By Line
			if ((strLine = Global.br0.readLine()) != null) {

				return strLine;

			}
			// Close the input stream
			else {
				Global.fstream0 = null;
				return null;
			} // in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return null;
	}
	
}
