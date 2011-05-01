/**
 * 
 */
package isa;

import arc_project.Global;
import arc_project.Instruction;

/**
 * @author zzj
 *
 */
public class InstructionSet_OUT extends Instruction {

	/**
	 * Output Character to Device from Register
	 */
	public InstructionSet_OUT() {
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
//		6)	Check if device is valid (1:Printer)
//		7) 	Character(DEVID) < Register(RSR)
		switch(Global.ALU.char2int(Global.DEVID.get())){
		
		case 1:		
			char c = (char) Global.ALU.char2int(Global.R[Global.ALU.char2int(Global.RSR.get())].get());
			
			Character cc = new Character(c);
			Global.GUIMAIN.outToConsole(cc.toString());
			break;
		
		default: 	throw new Exception("Invalid Device");

		}
//		8)	PC	< PC + 1
		Global.PC.set(Global.ALU.add(Global.PC.get(), 1));

	}

}
