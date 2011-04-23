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
public class InstructionSet_DIV extends Instruction {

	/**
	 * 
	 */
	public InstructionSet_DIV() {
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
//		6)	RSR1 and RSR 2 must be 0 or 2
		if (Global.ALU.char2int(Global.RSR.get()) != 0 && Global.ALU.char2int(Global.RSR.get()) != 2)
			throw new Exception ( "rx must be 0 or 2" );
		if (Global.ALU.char2int(Global.RSR2.get()) != 0 && Global.ALU.char2int(Global.RSR2.get()) != 2)
			throw new Exception ( "ry must be 0 or 2" );
//		7)	Register(RSR1+1) < Register(RSR1) MOD Register(RSR2)
		Global.R[Global.ALU.char2int(Global.RSR.get()) +1].set(Global.ALU.divide_remainder(Global.R[Global.ALU.char2int(Global.RSR.get())].get(), Global.R[Global.ALU.char2int(Global.RSR2.get())].get()));
//			Register(RSR1) < Register(RSR1) / Register(RSR2)
		Global.R[Global.ALU.char2int(Global.RSR.get())].set(Global.ALU.divide_quotient(Global.R[Global.ALU.char2int(Global.RSR.get())].get(), Global.R[Global.ALU.char2int(Global.RSR2.get())].get()));
//		8)	PC	< PC + 1
		Global.PC.set(Global.ALU.add(Global.PC.get(), 1));
		
		//Instruction.deCode();
		
	}

}
