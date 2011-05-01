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
public class InstructionSet_LDX extends Instruction {

	/**
	 * Load Index Register from Memory
	 */
	public InstructionSet_LDX() {
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see arc_project.Instruction#operate()
	 */
	@Override
	public void operate() throws Exception{
		// TODO Auto-generated method stub

//		5)  IND 	< IR 6
		Global.IND.set(Global.IR.get(6),0);
//		   	IXI		< IR 7
		Global.IXI.set(Global.IR.get(7),0);	
//		   	ADDR	< IR 10-15
		Global.ADDR.set(Global.IR.get(10,15));
//		6)	MAR		< EA
		Global.MAR.set(Instruction.EA());
//		7)	MBR		< Memory(MAR)
		Global.MBR.set(Global.L1.get(Global.MAR.get()));
//		8)	X0 		< MBR
		Global.X0.set(Global.MBR.get());
//		9)	PC		< PC + 1		
		Global.PC.set(Global.ALU.add(Global.PC.get(), 1));

	}

}
