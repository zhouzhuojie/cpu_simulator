/**
 * Class Memory
 * 
 * @author Zhuojie Zhou
 * @version (a version number or a date)
 */
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
	 *            the address and word to write into the memory. Address and
	 *            Word are char[].
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
		if (null == Word) {
			char[] zeros = { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
					'0', '0', '0', '0', '0', '0' };
			return zeros;
		}
		return Word.toCharArray();
	}

}
