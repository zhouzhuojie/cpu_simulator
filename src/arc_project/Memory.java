/**
 * Class Memory
 * 
 * @author Zhuojie Zhou
 * @version (a version number or a date)
 */
package arc_project;

public class Memory {
	private int _memoryWordLength;
	public int _memorySize;
	private int _memoryAddressLength;
	private MemoryBank _membank0;
	private MemoryBank _membank1;

	public Memory(int _memoryWordlength, int _memoryAddressLength,
			int _memorySize) {
		this._memoryWordLength = _memoryWordlength;
		this._memorySize = _memorySize;
		this._memoryAddressLength = _memoryAddressLength;
		this._membank0 = new MemoryBank(this._memorySize);
		this._membank1 = new MemoryBank(this._memorySize);
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
			tmp = tmp
					&& ((address[i] == '0') || (address[i] == '1') || Global.ALU
							.char2int(address) > this._memorySize - 1);
		}
		if ((tmp == false) || (address.length != _memoryAddressLength)) {
			char[] machine_fault_0 = { '0', '0', '0', '0' };
			Global.MFR.set(machine_fault_0);
			throw new Exception("Illegal Memory Address");
		}
		// Throw exception when the word char[] contains char which is not 0 or
		// 1, or the word's length is not the predefined one.
		boolean tmp1 = true;
		for (int i = 0; i < word.length; i++) {
			tmp1 = tmp1 && ((word[i] == '0') || (word[i] == '1'));
		}
		if ((tmp1 == false) || (word.length != _memoryWordLength))
			throw new Exception("Illegal Word");

		String Address = new String(address);

		if (Address.charAt(this._memoryAddressLength - 1) == '0') {
			this._membank0.set(Address.substring(0,
					this._memoryAddressLength - 1).toCharArray(), word);
		} else {
			this._membank1.set(Address.substring(0,
					this._memoryAddressLength - 1).toCharArray(), word);
		}

	}

	/**
	 * @return get() returns the word, word's type is char[].
	 */
	public char[] get(char[] address) throws Exception {

		// Throw exception when the address char[] contains char which is not 0
		// or 1, or the address's length is not the predefined one.
		boolean tmp = true;
		for (int i = 0; i < address.length; i++) {
			tmp = tmp
					&& ((address[i] == '0') || (address[i] == '1') || Global.ALU
							.char2int(address) > this._memorySize - 1);
		}
		if ((tmp == false) || (address.length != _memoryAddressLength)) {
			char[] machine_fault_0 = { '0', '0', '0', '0' };
			Global.MFR.set(machine_fault_0);
			throw new Exception("Illegal Memory Address");
		}

		String Address = new String(address);

		if (Address.charAt(this._memoryAddressLength - 1) == '0') {
			return this._membank0.get(Address.substring(0,
					this._memoryAddressLength - 1).toCharArray());
		} else {
			return this._membank1.get(Address.substring(0,
					this._memoryAddressLength - 1).toCharArray());
		}
	}

}
