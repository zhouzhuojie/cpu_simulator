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
public class InstructionSet_SMR extends Instruction {

	/**
	 * 
	 */
	public InstructionSet_SMR() {
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
//		   	RSR		< IR 8-9
		Global.RSR.set(Global.IR.get(8,9));			
//		   	ADDR	< IR 10-15
		Global.ADDR.set(Global.IR.get(10,15));
//		6)	MAR		< EA
		Global.MAR.set(Instruction.EA());
//		7)	MBR		< Memory(MAR)
//		if (Global.ALU.char2int(Global.PC.get()) > 800 ) {
//			System.out.print("MAR = ");
//			System.out.println(Global.MAR.get());
//		}
		
		Global.MBR.set(Global.L1.get(Global.MAR.get()));
//		8)	Register(RSR) < Register(RSR) - MBR
//		if (Global.ALU.char2int(Global.PC.get()) > 700 ) {
//			System.out.print(Global.ALU.char2int(Global.R[Global.ALU.char2int(Global.RSR.get())].get()));
//			System.out.print(" - ");
//			System.out.print(Global.ALU.char2int(Global.MBR.get()));
//			System.out.print(" = ");
//			System.out.println(Global.ALU.char2num(Global.ALU.minus(Global.R[Global.ALU.char2int(Global.RSR.get())].get(), Global.MBR.get())));
//		}		
		Global.R[Global.ALU.char2int(Global.RSR.get())].set(Global.ALU.minus(Global.R[Global.ALU.char2int(Global.RSR.get())].get(), Global.MBR.get()));
//		9)	PC		< PC + 1
		Global.PC.set(Global.ALU.add(Global.PC.get(), 1));
		
		//Instruction.deCode();
		
	}

}
