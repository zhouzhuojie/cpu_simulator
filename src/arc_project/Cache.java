/*****************************************************************************
 * FILE    : Cache.java                                                      *
 *                                                                           *
 * AUTHOR  : Zhuojie Zhou                                                    *
 *                                                                           *
 * DATE    : May 4, 2011                                                     *
 *                                                                           *
 * PROJECT : GWU CS6461 Computer Architecture Class                          *
 *                                                                           *
 * This file contains the main ALU part                                      *
 *                                                                           *
 * DEPENDS : arc_project.Global FileWriter, BufferWriter                     *
 *                                                                           *
 * Design Approach:                                                          *
 * 		(a) 8 lines with 8 16bits data each.                                 *
 * 		(b) Using direct mapping method, the last 3 bits (bit 13th - 15th)   *
 * 			are used for Block offset (identify the position of a word we    *
 * 			need). The next last 3 bits (bit 10th - 12th) are used for Index * 
 * 			(identify the line number we need). The first 10 bits are used   *
 * 			for tag.														 *
 * 		(c) Using write-through technique. i.e, if write hit, both write to  *
 * 			the cache and memory, if write miss, just write to the memory.	 *		
 *****************************************************************************/

package arc_project;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Cache {
	private int[] _validBit = new int[8];  // valid bit
	private String[] _tag = new String[8];  // Tag
	public String[][] _data = new String[8][8];  // store data for cache

	private FileWriter fstream;
	private BufferedWriter cacheOut;

	/* This is structure of cache */
	public Cache() {
		
		/* create virtual empty cache memory by set all valid bits into 0 */
		for (int i = 0; i < 8; i++) {
			_validBit[i] = 0;
		}
		
		/* create file to record a trace of cache activity */
		try {
			fstream = new FileWriter("program_file/cacheRecord.txt");
			cacheOut = new BufferedWriter(fstream);
			cacheOut.write("PROCESS      TAG          LINE   WORD   DATA");
			cacheOut.newLine();
			cacheOut.flush();  // flush string into the file
		} catch (Exception e) {  // catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}

	/* Get index ( or the position of the desired line number ) */
	private int getRow(char[] address) {
		String addr = new String(address);  // convert the address from char[] to string
		String lineNumber = addr.substring(10, 13);  // get index (line number)

		/* convert the desired line number from binary into decimal */
		int row = 0;
		for (int i = 0; i < 3; i++) {
			if (lineNumber.charAt(i) == '1') {
				row = row + (int) Math.pow(2, 3 - 1 - i);
			}
		}
		return row;
	}

	/* Get block offset (or the position of the desired word ) */
	private int getCol(char[] address) {
		String addr = new String(address);  // convert the address from char[] to string
		String columnNumber = addr.substring(13, 16);  // get block offset (word position)

		/* convert the desired word position form binary into decimal */
		int col = 0;
		for (int i = 0; i < 3; i++) {
			if (columnNumber.charAt(i) == '1') {
				col = col + (int) Math.pow(2, 3 - 1 - i);
			}
		}
		return col;
	}

	/* Check whether the address is in cache or not */
	public boolean isInCache(char[] address) {
		int row = getRow(address);  // get index (line)
		String addr = new String(address);  // convert the address from char[] to string
		String higherAddress = addr.substring(0, 10);  // get tag
		if (_validBit[row] == 1 && higherAddress.equals(_tag[row])) {  // compare index and tag
			return true;  // the address is in the cache
		} else {
			return false;  // the address is not in the cache
		}

	}

	/* write data into the designated address via cache */
	public void set(char[] address, char[] word) throws Exception {

		/* We use write through.  Then the data is suddenly written to main memory */
		Global.MEMORY.set(address, word);
		
		/* We use no-write allocate
		 * If it is write hit, the data is also written to the cache
		 * If it is write miss, it skips writing process to the cache
		 */
		if (isInCache(address)) {  // check write hit

			int row = getRow(address);  // get index (line number)
			int col = getCol(address);  // get block offset (word position)

			String Word = new String(word);  // convert the data from char[] to string
			_data[row][col] = Word;  // write data into cache memory
			
			/* record write hit */
			cacheOut.write("WRITE HIT    ");  // print PROCESS
			String addr = new String(address);  // convert the address from char[] to string
			String higherAddress = addr.substring(0, 10);  // get tag
			cacheOut.write(higherAddress + "   ");  // print TAG
			cacheOut.write(row + "      ");  // print LINE
			cacheOut.write(col + "      ");  // print WORD
			cacheOut.write(Word); // print DATA
			cacheOut.newLine();
			cacheOut.flush();  // flush string into the file
				
		/* record write miss */
		} else {
			
			int row = getRow(address);  // get index (line number)
			int col = getCol(address);  // get block offset (word position)
			
			cacheOut.write("WRITE MISS   ");  // print PROCESS
			String addr = new String(address);  // convert the address from char[] to string
			String higherAddress = addr.substring(0, 10);  // get tag
			cacheOut.write(higherAddress + "   ");  // print TAG
			cacheOut.write(row + "      ");  // print LINE
			cacheOut.write(col + "      ");  // print WORD
			cacheOut.newLine();
			cacheOut.flush();  // flush string into the file
		}

	}

	/* read data from the designated address via cache */
	public char[] get(char[] address) throws Exception {

		int row = getRow(address);  // get index (line number)
		if (isInCache(address)) {  // check read hit

			int col = getCol(address);  // get block offset (word position)
					
			/* record read hit */
			cacheOut.write("READ HIT     ");  // print PROCESS
			String addr = new String(address);  // convert the address from char[] to string
			String higherAddress = addr.substring(0, 10);  // get tag
			cacheOut.write(higherAddress + "   ");  // print TAG
			cacheOut.write(row + "      ");  // print LINE
			cacheOut.write(col + "      ");  // print WORD
			cacheOut.write(_data[row][col]); // print DATA
			cacheOut.newLine();
			cacheOut.flush();  // flush string into the file
			
			return _data[row][col].toCharArray();  // return the desired data from cache

		/* if it is read miss, cache will store the whole block of data into the designated cache location */
		} else {
			char[] word = Global.MEMORY.get(address);  // get the desired data from main memory
			String addr = new String(address);  // convert the address from char[] to string
			String addr_block = addr.substring(0, 13) + "000";  // set the block address (first 13 bits)
			_validBit[row] = 1;  // change the valid bit into 1 because this address is valid now
			_tag[row] = addr.substring(0, 10);  // set tag with first 10 bits from the address
			
			/* record read miss */
			cacheOut.write("READ MISS    ");  // print PROCESS
			String higherAddress = addr.substring(0, 10);  // get tag
			cacheOut.write(higherAddress + "   ");  // print TAG
			cacheOut.write(row + "      ");  // print LINE
			cacheOut.write("0-7    ");  // print WORD
			
			
			
			/* because this is 8-word cache, the cache has to copy all 8 word (1 block) from main memory */
			for (int i = 0; i < 8; i++) {
				String tmpS = new String(Global.MEMORY.get
						(Global.ALU.int2char(Global.ALU
						.char2int(addr_block.toCharArray())
						+ i))
						);  // get data (1 word) from main memory
				_data[row][i] = tmpS;  // set data (1 word) into cache
				
				cacheOut.write(tmpS + " ");  // print DATA
				
			}
			
			cacheOut.newLine();
			cacheOut.flush();  // flush string into the file
			
			return word;  // return the desired data from main memory
		}
	}

}
