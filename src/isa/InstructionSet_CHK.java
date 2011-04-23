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
public class InstructionSet_CHK extends Instruction {

	/**
	 * 
	 */
	public InstructionSet_CHK() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see arc_project.Instruction#operate()
	 */
	@Override
	public void operate() throws Exception {
		// TODO Auto-generated method stub

//		5)  RSR		< IR 7-8
		Global.RSR.set(Global.IR.get(7,8));	
//			DEVID 	< IR 10-15
		Global.DEVID.set(Global.IR.get(10,15));	
		/* Process 6 move to default case in Process 7 */
//		6) 	Register(RSR) < Status(DEVID)
		switch(Global.ALU.char2int(Global.DEVID.get())){
		
		case 0:		
			Global.R[Global.ALU.char2int(Global.RSR.get())].set('1',15);
			break;
			
		case 1:		
			Global.R[Global.ALU.char2int(Global.RSR.get())].set('1',15);
			break;
		
		case 2:		
			Global.R[Global.ALU.char2int(Global.RSR.get())].set('1',15);
			break;
		
		default:
			Global.R[Global.ALU.char2int(Global.RSR.get())].set('0',15);
			break;

		}
//		7)	PC	< PC + 1
		Global.PC.set(Global.ALU.add(Global.PC.get(), 1));
		
		//Instruction.deCode();
		
	}

}
