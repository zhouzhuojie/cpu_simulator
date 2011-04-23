package arc_project;

public class Cache {
	private int[] _validBit = new int[8];
	private String[] _tag = new String[8];
	public String[][] _data = new String[8][8];

	public Cache() {
		for (int i = 0; i < 8; i++) {
			_validBit[i] = 0;
		}

	}

	private int getRow(char[] address) {
		String addr = new String(address);
		String lineNumber = addr.substring(10, 13);

		int row = 0;
		for (int i = 0; i < 3; i++) {
			if (lineNumber.charAt(i) == '1') {
				row = row + (int) Math.pow(2, 3 - 1 - i);
			}
		}
		return row;
	}

	private int getCol(char[] address) {
		String addr = new String(address);
		String columnNumber = addr.substring(13, 16);

		int col = 0;
		for (int i = 0; i < 3; i++) {
			if (columnNumber.charAt(i) == '1') {
				col = col + (int) Math.pow(2, 3 - 1 - i);
			}
		}
		return col;
	}

	public boolean isInCache(char[] address) {
		int row = getRow(address);
		String addr = new String(address);
		String higherAddress = addr.substring(0, 10);
		if (_validBit[row] == 1 && higherAddress.equals(_tag[row])) {
			return true;
		} else {
			return false;
		}

	}

	public void set(char[] address, char[] word) throws Exception {

		Global.MEMORY.set(address, word);

		if (isInCache(address)) {

			int row = getRow(address);
			int col = getCol(address);

			String Word = new String(word);
			_data[row][col] = Word;
		}

	}

	public char[] get(char[] address) throws Exception {

		int row = getRow(address);
		if (isInCache(address)) {

			int col = getCol(address);
			return _data[row][col].toCharArray();

		} else {
			char[] word = Global.MEMORY.get(address);
			String addr = new String(address);
			String addr_block = addr.substring(0, 13) + "000";
			_validBit[row] = 1;
			_tag[row] = addr.substring(0, 10);
			for (int i = 0; i < 8; i++) {
				String tmpS = new String(Global.MEMORY.get
						(Global.ALU.int2char(Global.ALU
						.char2int(addr_block.toCharArray())
						+ i))
						);
				_data[row][i] = tmpS;
			}

			return word;
		}
	}

}
