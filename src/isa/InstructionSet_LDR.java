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
public class InstructionSet_LDR extends Instruction{
	public void operate() throws Exception {
		//Do something for the LDR instruction
		//Can have the access to any Global registers and Global Memory. They are all defined in class Global.
		//...		
			
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
		Global.MBR.set(Global.L1.get(Global.MAR.get()));	
//		8)	Register(RSR) < MBR
		Global.R[Global.ALU.char2int(Global.RSR.get())].set(Global.MBR.get());
//		9)	PC		< PC + 1
		Global.PC.set(Global.ALU.add(Global.PC.get(), 1));
		
		//Instruction.deCode();
		
	}
}


/**
 * @author zzj
 *
 */
 
