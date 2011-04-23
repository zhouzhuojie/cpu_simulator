/**
 * Class Memory
 * 
 * @author Zhuojie Zhou
 * @version (a version number or a date)
 */
package arc_project;

import java.util.HashMap;
import java.util.Map;

public class Memory {
	private int _memoryWordLength;
	public int _memorySize;
	private int _memoryAddressLength;
	private Map<String, String> _addressMap;

	public Memory(int _memoryWordlength, int _memoryAddressLength,
			int _memorySize) {
		this._memoryWordLength = _memoryWordlength;
		this._memorySize = _memorySize;
		this._memoryAddressLength = _memoryAddressLength;
		_addressMap = new HashMap<String, String>(_memorySize);
	}

	/**
	 * @param Input
	 *            the address and word to write into the memory. Address and
	 *            Word are char[].
	 */
	public void set(char[] address, char[] word) throws Exception {

		// Throw exception when the address char[] contains char which is not 0
		// or 1, or the address's length is not the predefined one.
		boolean tmp = true;
		for (int i = 0; i < address.length; i++) {
			tmp = tmp && ((address[i] == '0') || (address[i] == '1'));
		}
		if ((tmp == false) || (address.length != _memoryAddressLength))
			throw new Exception("Illegal address");

		// Throw exception when the word char[] contains char which is not 0 or
		// 1, or the word's length is not the predefined one.
		boolean tmp1 = true;
		for (int i = 0; i < word.length; i++) {
			tmp1 = tmp1 && ((word[i] == '0') || (word[i] == '1'));
		}
		if ((tmp1 == false) || (word.length != _memoryWordLength))
			throw new Exception("Illegal word");

		// Throw exception when memory overflow.
		if (_addressMap.size() >= _memorySize)
			throw new Exception("Memory over flow");

		String Address = new String(address);
		String Word = new String(word);

		this._addressMap.put(Address, Word);

		// Global.GUIMAIN.displayMemoryBank(address);
	}

	/**
	 * @return get() returns the word, word's type is char[].
	 */
	public char[] get(char[] address) throws Exception {
		// Throw exception when the address char[] contains char which is not 0
		// or 1, or the address's length is not the predefined one.
		boolean tmp = true;

		for (int i = 0; i < address.length; i++) {
			tmp = tmp && ((address[i] == '0') || (address[i] == '1'));
		}
		if ((tmp == false) || (address.length != _memoryAddressLength))
			throw new Exception("Illegal address");

		String Address = new String(address);
		String Word = _addressMap.get(Address);
		if (null == Word){
			char[] zeros = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};
			return zeros;
		}
		return Word.toCharArray();
	}

}
