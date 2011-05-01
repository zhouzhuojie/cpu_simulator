/*************************************************************************
 * Part2_Cache_Memory                                                    *
 *                                                                       *
 * Authors: Zhuojie Zhou                                                 *
 *                                                                       *
 * Demo for Part 2:													     *
 * 		(a) Loading from a file to memory.								 *
 * 		(b) Using VADD instruction to calculate vector add operation.    *
 * 		(c) Meanwhile, we also output the cacheRecord.txt file to show   *
 * 			the changes in L1 cache.									 *
 * 																		 *
 * Note: L1 cache is always on, so after calculating program1.txt and 	 *
 * program2.txt, also can find the cache log report in cacheRecord.txt   *
 *************************************************************************/
package arc_project;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class VectorDemo {

	
	/*************************************************************************
	 * Memory preparation for the VADD instruction.                          *
	 * Read from a txt file.												 *
	 * The file's format is "16bits 16bits" separated by space.              *
	 *************************************************************************/
	public void demo() throws Exception {

		Global.GUIMAIN.outToConsole("Loading 'program_file/VectorData.txt' "
				+ "\n");

		char[] address_V1 = { '0', '0', '0', '0', '0', '0', '0', '0', '1', '0',
				'0', '0', '0', '0', '0', '0' };
		char[] address_V2 = { '0', '1', '0', '0', '0', '0', '0', '0', '0', '0',
				'0', '0', '0', '0', '0', '0' };
		int k = 0;

		String s;
		while (null != (s = getLine0())) {

			String v1 = s.split(" ")[0];
			String v2 = s.split(" ")[1];
			Global.GUIMAIN.outToConsole("V1[" + k + "]: " + v1 + "   V2[" + k
					+ "]: " + v2 + "\n");
			char[] w1 = v1.toCharArray();
			char[] w2 = v2.toCharArray();

			Global.L1.set(
					Global.ALU.int2char(k + Global.ALU.char2int(address_V1)),
					w1);

			Global.L1.set(
					Global.ALU.int2char(k + Global.ALU.char2int(address_V2)),
					w2);

			k++;
		}
		
		
		char[] address_EA = { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
				'0', '0', '0', '0', '1', '0' };
		char[] address_EA_Plus1 = { '0', '0', '0', '0', '0', '0', '0', '0',
				'0', '0', '0', '0', '0', '0', '1', '1' };
		Global.L1.set(address_EA, address_V1);
		Global.L1.set(address_EA_Plus1, address_V2);

		
		
		/*************************************************************************
		 * Calculation for VADD instruction *
		 *************************************************************************/
		String IR = "1000110000000010";
		String PC = "0000000000000000";
		Global.PC.set(PC.toCharArray());
		Global.L1.set(PC.toCharArray(), IR.toCharArray());
		Instruction.deCode();
		
		Global.GUIMAIN.outToConsole("VADD results are: " + "\n");
		
		for (int j = 0; j < 32; j++) {
			String tmp = new String(Global.L1.get(Global.ALU.int2char(j
					+ Global.ALU.char2int(address_V1))));
			Global.GUIMAIN.outToConsole("V1[" + j + "]: " + tmp + "\n");
		}
		Global.GUIMAIN
				.outToConsole("Cache record has been saved in 'program_file/cacheRecord.txt' "
						+ "\n");

	}
		
	
	/*************************************************************************
	 * Get a line from the file, and point to the next line. If it is the	 * 
	 * end of the file, then will return null.                               *
	 *************************************************************************/
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
