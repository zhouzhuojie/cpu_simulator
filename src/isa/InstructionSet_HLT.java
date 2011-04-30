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
	public void operate() throws Exception {
		//Do something for the HLT instruction
		//Can have the access to any Global registers and Global Memory. They are all defined in class Global.
		//...
			
//		5)	PC	< PC + 1
			Global.PC.set(Global.ALU.add(Global.PC.get(), 1));		
//		6)	Stop
			//Nothing implement here
			Global.GUIMAIN.gThread.gRun=false;

	}
}
