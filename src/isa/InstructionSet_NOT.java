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
public class InstructionSet_NOT extends Instruction {

	/**
	 * Logical Not of Register To Register
	 */
	public InstructionSet_NOT() {
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see arc_project.Instruction#operate()
	 */
	@Override
	public void operate() throws Exception {
		// TODO Auto-generated method stub

//		5) 	RSR		< IR 6-7
		Global.RSR.set(Global.IR.get(6,7));			
//		6)	Register(RSR) < NOT Register(RSR)
		Global.R[Global.ALU.char2int(Global.RSR.get())].set(Global.ALU.not(Global.R[Global.ALU.char2int(Global.RSR.get())].get()));
//		7)	PC	< PC + 1
		Global.PC.set(Global.ALU.add(Global.PC.get(), 1));

	}

}
