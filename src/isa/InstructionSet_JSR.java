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
public class InstructionSet_JSR extends Instruction {

	/**
	 * Jump and Save Return Address
	 */
	public InstructionSet_JSR() {
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see arc_project.Instruction#operate()
	 */
	@Override
	public void operate() throws Exception {
		// TODO Auto-generated method stub

//		5)  IND 	< IR 6
		Global.IND.set(Global.IR.get(6),0);
//		   	IXI		< IR 7
		Global.IXI.set(Global.IR.get(7),0);		
//		   	ADDR	< IR 10-15
		Global.ADDR.set(Global.IR.get(10,15));
//		6) 	R3 	< PC + 1
		Global.R[3].set(Global.ALU.add(Global.PC.get(), 1));
//		7)	PC 		< EA
		Global.PC.set(Instruction.EA());
//		8) 	R0	< ponter to arguments
		//*** Don't know what to implement here yet *** 

	}

}
