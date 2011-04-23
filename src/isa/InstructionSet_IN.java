/**
 * 
 */
package isa;

import arc_project.Global;
import arc_project.Instruction;


import java.io.*;

/**
 * @author zzj
 *
 */
public class InstructionSet_IN extends Instruction {

	/**
	 * 
	 */
	public static Boolean bWaitForInput = false;
	public static int gInputIdx = 0; 
	public InstructionSet_IN() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see arc_project.Instruction#operate()
	 */
	@Override
	public void operate() throws Exception {
		// TODO Auto-generated method stub

//		5)  RSR		< IR 7-8
		Global.RSR.set(Global.IR.get(7,8));	
//			DEVID 	< IR 10-15
		Global.DEVID.set(Global.IR.get(11,15));	
		
		
		
		/* Process 6 move to default case in Process 7 */
//		6)	Check if device is valid (0:Keyboard, 2:Card Reader)
//		if( Global.ALU.char2int(Global.DEVID.get()) != 0 && Global.ALU.char2int(Global.DEVID.get()) != 2 )
//			throw new Exception( "Invalid Device" );
//		7) 	Register(RSR) < Character(DEVID)
		switch (Global.ALU.char2int(Global.DEVID.get())) {

		case 0:
			//String SS;
			// Global.GUIMAIN.enableKeyboard(true);
			//
			// while (GuiMain.InputPanel.textField.isEnabled()){
			// Global.GUIMAIN.inFromKeyboard(Global.RSR.get());// Input from the
			// keyboard to the register which RSR points to.
			// }
			EnableKeyboard ();

// to capture all numbers before procesing			
//			if (!Global.GUIMAIN.InputPanel.gInputDone)
//				EnableKeyboard ();
//			else
//			{
//				SS = Global.GUIMAIN.InputPanel.vStr.elementAt(gInputIdx++);			
//
//			//			String SS = getLine0();
//			
//			char[] c0 = SS.toCharArray();
//			
//			//System.out.println ("**** SS = "+SS);
//			Global.R[Global.ALU.char2int(Global.RSR.get())].set(c0);
//			System.out.print(Global.R[Global.ALU.char2int(Global.RSR.get())].get());
//			}
			
			//			if (SS.charAt(7)=='0' &&  SS.charAt(8)=='0') {
//				try {
//					Global.R[0].set(c0);
//				} catch (Exception e) {
//					// TODO Auto-generated ccatcch blocck
//					e.printStackTrace();
//				}
//			}
//
//			if (SS.charAt(7)=='0' &&  SS.charAt(8)=='1') {
//
//				try {
//
//					Global.R[1].set(c0);
//
//				} catch (Exception e) {
//					// TODO Auto-generated ccatcch blocck
//					e.printStackTrace();
//				}
//			}
//
//			if (SS.charAt(7)=='1' &&  SS.charAt(8)=='0') {
//				try {
//					Global.R[2].set(c0);
//				} catch (Exception e) {
//					// TODO Auto-generated ccatcch blocck
//					e.printStackTrace();
//				}
//			}
//
//			if (SS.charAt(7)=='1' &&  SS.charAt(8)=='1') {
//				try {
//					Global.R[3].set(c0);
//				} catch (Exception e) {
//					// TODO Auto-generated ccatcch blocck
//					e.printStackTrace();
//				}
//			}
			break;

		case 2:
			String S = getLine();

			char[] c = S.toCharArray();
			Global.R[Global.ALU.char2int(Global.RSR.get())].set(c);
//			System.out.print(Global.R[Global.ALU.char2int(Global.RSR.get())].get());
			break;
		default:
			throw new Exception("Invalid Device");

		}
		// 8) PC < PC + 1
//		if (Global.GUIMAIN.gThread.gKeyboardOn == false)
			Global.PC.set(Global.ALU.add(Global.PC.get(), 1));

		// Instruction.deCode();

	}

	public void EnableKeyboard ()
	{
		Global.GUIMAIN.gThread.gKeyboardOn = true;
	}
	
	public String getLine() {
		try {
			// Open the file that is the first
			// command line parameter

			if (Global.fstream == null) {
				Global.fstream = new FileInputStream("program_file/program2.txt");
				// Get the object of DataInputStream

				DataInputStream in = new DataInputStream(Global.fstream);
				Global.br = new BufferedReader(new InputStreamReader(in));
			}
			String strLine;
			// Read File Line By Line
			if ((strLine = Global.br.readLine()) != null) {

				return strLine;

			}
			// Close the input stream
			else {
				Global.fstream = null;
			} // in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return null;
	}
	
	public String getLine0() {
		try {
			// Open the file that is the first
			// command line parameter

			if (Global.fstream0 == null) {
				Global.fstream0 = new FileInputStream("program_file/input1.txt");
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
			} // in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return null;
	}
	
}
