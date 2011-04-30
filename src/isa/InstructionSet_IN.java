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
			EnableKeyboard ();
			break;

		case 2:
			String S = getLine();

			char[] c = S.toCharArray();
			Global.R[Global.ALU.char2int(Global.RSR.get())].set(c);
			break;
		default:
			throw new Exception("Invalid Device");

		}
		// 8) PC < PC + 1
		Global.PC.set(Global.ALU.add(Global.PC.get(), 1));
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
				String filename = "program.txt";
				if (Global.GUIMAIN.ChoicePanel.ProgramName.equals("Program 2"))
						filename = "program2.txt"; 
				Global.fstream = new FileInputStream("program_file/"+filename);
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
}
