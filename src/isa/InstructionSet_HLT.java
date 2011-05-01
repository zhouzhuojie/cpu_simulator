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
public class InstructionSet_HLT extends Instruction{
	
	/**
	 * Stop the machine
	 */
	
	/* (non-Javadoc)
	 * @see arc_project.Instruction#operate()
	 */
	public void operate() throws Exception {
			
//		5)	PC	< PC + 1
			Global.PC.set(Global.ALU.add(Global.PC.get(), 1));		
//		6)	Stop
			Global.GUIMAIN.gThread.gRun=false;

	}
}
