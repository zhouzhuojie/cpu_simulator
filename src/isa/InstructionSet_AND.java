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
public class InstructionSet_AND extends Instruction {

	/**
	 * Logical And of Register and Register
	 */
	public InstructionSet_AND() {
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see arc_project.Instruction#operate()
	 */
	@Override
	public void operate() throws Exception {
		// TODO Auto-generated method stub

//		5) 	RSR1	< IR 6-7
		Global.RSR.set(Global.IR.get(6,7));			
//		   	RSR2	< IR 8-9
		Global.RSR2.set(Global.IR.get(8,9));
//		6)	Register(RSR1) < Register(RSR1) AND Register(RSR2)
		Global.R[Global.ALU.char2int(Global.RSR.get())].set(Global.ALU.and(Global.R[Global.ALU.char2int(Global.RSR.get())].get(), Global.R[Global.ALU.char2int(Global.RSR2.get())].get()));
//		7)	PC	< PC + 1
		Global.PC.set(Global.ALU.add(Global.PC.get(), 1));
		
	}

}
