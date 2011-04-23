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
public class InstructionSet_LDA extends Instruction {

	/**
	 * 
	 */
	public InstructionSet_LDA() {
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
		
//		System.out.print("IND = ");
//		System.out.println(Global.IND.get());
		
//		   	IXI		< IR 7
		Global.IXI.set(Global.IR.get(7),0);
		
//		System.out.print("IXI = ");
//		System.out.println(Global.IXI.get());		
		
//		   	RSR		< IR 8-9
		Global.RSR.set(Global.IR.get(8,9));	
		
//		System.out.print("RSR = ");
//		System.out.println(Global.RSR.get());
		
//		   	ADDR	< IR 10-15
		Global.ADDR.set(Global.IR.get(10,15));
		
//		System.out.print("ADDR = ");
//		System.out.println(Global.ADDR.get());
		
//		6)	Register(RSR)	< EA		
		Global.R[Global.ALU.char2int(Global.RSR.get())].set(Instruction.EA());
		
//		System.out.print("R0 = ");
//		System.out.println(Global.R0.get());
		
//		7)	PC		< PC + 1
		Global.PC.set(Global.ALU.add(Global.PC.get(), 1));
		
//		System.out.print("PC = ");
//		System.out.println(Global.PC.get());
		
		//Instruction.deCode();
		
	}

}
