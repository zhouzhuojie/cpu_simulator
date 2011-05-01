/*****************************************************************************
 * FILE    : Register.java                                                   *
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
	 * Set the _regContent with whole 16bit of value
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
	}

	/**
	 * @param _regContent
	 * Set the _regContent[i] with the indexOf i
	 */
	public void set(char c, int i) throws Exception {
		_regContent[i] = c;


	}

	/**
	 * @param _regContent
	 * Set the _regContent start from the indexOf i
	 */
	public void set(char[] partContent, int startIndex) throws Exception {
		if (partContent.length > _regLength - startIndex)
			throw new Exception("Set too long content into the register!");

		for (int k = startIndex; k < partContent.length + startIndex; k++)
			_regContent[k] = partContent[k - startIndex];

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
