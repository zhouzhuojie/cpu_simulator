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
public class InstructionSet_IAR extends Instruction {

	/**
	 * Immediate Add to Register
	 */
	public InstructionSet_IAR() {
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see arc_project.Instruction#operate()
	 */
	@Override
	public void operate() throws Exception {
		// TODO Auto-generated method stub

//		5)  RSR		< IR 8-9
		Global.RSR.set(Global.IR.get(8,9));			
//		   	IMMED	< IR 10-15
		Global.IMMED.set(Global.IR.get(10,15));
//		6a)	IF (IMMED == 0) THEN
		if( Global.ALU.char2int(Global.IMMED.get()) == 0 ){
//		+7)		PC < PC + 1
			Global.PC.set(Global.ALU.add(Global.PC.get(), 1));
//		6b)	ELSE IF (Register(RSR) == 0 ) THEN
		} else if ( Global.ALU.char2int(Global.R[Global.ALU.char2int(Global.RSR.get())].get()) == 0 ){
//		+7)		Register(RSR) < IMMED
			Global.R[Global.ALU.char2int(Global.RSR.get())].set(Global.IMMED.get(),10);			
//		+8)		PC < PC + 1
			Global.PC.set(Global.ALU.add(Global.PC.get(), 1));
//			ELSE
		} else {
//		+7)		Register(RSR) < Register(RSR) + IMMED
			Global.R[Global.ALU.char2int(Global.RSR.get())].set(Global.ALU.add(Global.R[Global.ALU.char2int(Global.RSR.get())].get(), Global.IMMED.get()));
//		+8)		PC	< PC + 1
			Global.PC.set(Global.ALU.add(Global.PC.get(), 1));
		}

	}

}
