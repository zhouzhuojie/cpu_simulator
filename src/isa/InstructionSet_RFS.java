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
public class InstructionSet_RFS extends Instruction {

	/**
	 * Return From Subroutine with return code in Immed
	 */
	public InstructionSet_RFS() {
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see arc_project.Instruction#operate()
	 */
	@Override
	public void operate() throws Exception {
		// TODO Auto-generated method stub

//		5)  IMMED 	< IR 10-15
		Global.IMMED.set(Global.IR.get(10,15));
//		6) 	R0 	< IMMED
		Global.R[0].reset();
		Global.R[0].set(Global.IMMED.get(),10);
//		7) 	PC	< c(R3)
		Global.PC.set(Global.R[3].get());

	}

}
