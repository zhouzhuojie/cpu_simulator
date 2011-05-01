/*****************************************************************************
 * FILE    : MemoryBank.java                                                 *
 *                                                                           *
 * AUTHOR  : Zhuojie Zhou                                                    *
 *                                                                           *
 * DATE    : May 4, 2011                                                     *
 *                                                                           *
 * PROJECT : GWU CS6461 Computer Architecture Class                          *
 *                                                                           *
 * This file contains the main ALU part                                      *
 *                                                                           *
 * DEPENDS : arc_project and Hash map                                        *
 *                                                                           *
 * Design Approach:                                                          *
 * 		Memory bank is implemented in hashmap, key stand for address, while  *
 * 		value stand for the word (data) stored in the memory bank.           *
 *****************************************************************************/
package arc_project;

import java.util.HashMap;
import java.util.Map;

public class MemoryBank {

	public int _bankSize;

	private Map<String, String> _addressMap;

	public MemoryBank(int _memorySize) {
		this._bankSize = _memorySize;
		_addressMap = new HashMap<String, String>(_memorySize);
	}

	/**
	 * @param Input
	 *            the address and word to write into the memory. address and
	 *            word are char[].
	 */
	public void set(char[] address, char[] word) throws Exception {

		String Address = new String(address);
		String Word = new String(word);

		this._addressMap.put(Address, Word);

	}

	/**
	 * @return get() returns the word, word's type is char[].
	 */
	public char[] get(char[] address) throws Exception {

		String Address = new String(address);
		String Word = _addressMap.get(Address);
		
		// If address have never been put in the hashmap, which means that read
		// before write any value in the memory, then return a 16bit value which
		// content are all zeros.
		if (null == Word) {
			char[] zeros = { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
					'0', '0', '0', '0', '0', '0' };
			
			return zeros;
			
		}
		return Word.toCharArray();
	}

}
