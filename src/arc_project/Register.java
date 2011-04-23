/**
 * Class Register
 * 
 * _regContent ---> Register's Content
 * 
 * 
 * @author Zhuojie Zhou
 * @version (a version number or a date)
 */

package arc_project;

public class Register {
	// _regContent is the 16 bits char array which contains the data in
	// register.
	private char[] _regContent;
	private int _regLength;

	/**
	 * Constructor for objects of class Register, define the length of the
	 * register.
	 */
	public Register(int length) {
		// initialize _regContent
		_regLength = length;
		_regContent = new char[_regLength];
		for (int i = 0; i < _regLength; i++) {
			_regContent[i] = '0';
		}
	}

	/* Add by Leen */
	public void reset() throws Exception {
		for (int i = 0; i < _regLength; i++) {
			_regContent[i] = '0';
		}
	}

	/**
	 * @param _regContent
	 *            the _regContent to set
	 */
	public void set(char[] _regContent) throws Exception {

		// Throw exception when the register contains char which is not 0 or 1,
		// or the register's length is not the predefined one.
		boolean tmp = true;
		for (int i = 0; i < _regContent.length; i++) {
			tmp = tmp && ((_regContent[i] == '0') || (_regContent[i] == '1'));
		}
		if ((tmp == false) || (_regContent.length != _regLength))
			throw new Exception(" Illegal register's content");

		this._regContent = _regContent;

		// System.out.println("Set into the register 1");

		// Global.GUIMAIN.refresh();
		//
		// Global.GUIMAIN.outToConsole(this._regContent.toString());
	}

	/**
	 * @param _regContent
	 *            the _regContent[i] to set
	 */
	public void set(char c, int i) throws Exception {
		_regContent[i] = c;
		// Global.GUIMAIN.refresh();
		// Global.GUIMAIN.outToConsole(this._regContent.toString());

		// System.out.println("Set into the register 2");

	}

	/**
	 * @param _regContent
	 *            the _regContent to set start from startIndex
	 */
	public void set(char[] partContent, int startIndex) throws Exception {
		if (partContent.length > _regLength - startIndex)
			throw new Exception("Set too long content into the register!");

		for (int k = startIndex; k < partContent.length + startIndex; k++)
			_regContent[k] = partContent[k - startIndex];

		// System.out.println("Set into the register 3");
		//
		//
		// Global.GUIMAIN.refresh();
		// Global.GUIMAIN.outToConsole(this._regContent.toString());

	}

	/**
	 * @return the _regContent
	 */
	public char[] get() {
		return _regContent;
	}

	/**
	 * @return the _regContent[i]
	 */
	public char get(int i) {
		return _regContent[i];
	}

	/* Old designed */
	/*
	 * /**
	 * 
	 * @return the _regContent[i to j] /* public char[] get(int i, int j) throws
	 * Exception{ if (j <= i) throw new Exception("Not exist this char[]");
	 * 
	 * char[] c = new char[j - i + 1]; for (int k = 0; k < c.length; k++) c[k] =
	 * _regContent[i + k];
	 * 
	 * return c; }
	 */

	/* changed by Leen */
	/**
	 * @return the _regContent[i to j]
	 */
	public char[] get(int i, int j) throws Exception {
		if (j < i)
			throw new Exception("Not exist this char[]");

		if (i == j) {
			char[] c = new char[1];
			c[0] = this.get(i);
			return c;
		}

		char[] c = new char[j - i + 1];
		for (int k = 0; k < c.length; k++)
			c[k] = _regContent[i + k];

		return c;
	}
}
