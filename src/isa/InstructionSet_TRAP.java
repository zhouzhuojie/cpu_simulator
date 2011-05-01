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

public class InstructionSet_TRAP extends Instruction {
	
	/**
	 * Traps
	 */
	
	/* (non-Javadoc)
	 * @see arc_project.Instruction#operate()
	 */
	@Override
	public void operate() throws Exception {

		// RSR	< IR 8-9
		Global.RSR.set(Global.IR.get(8, 9));
		// IF Register(RSR) > 15
		int index_number = Global.ALU.char2int(Global.R[Global.ALU
				.char2int(Global.RSR.get())].get());
		if (index_number > 15){
			// Machine faults
			char[] machine_fault_1 = {'0','0','0','1'};
			Global.MFR.set(machine_fault_1);
			throw new Exception("Illegal TRAP code!");
		}

		// MAR < address 0
		char[] memory_0 = { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
				'0', '0', '0', '0', '0', '0' };
		Global.MAR.set(memory_0);
		// MBR < L1(MAR)
		Global.MBR.set(Global.L1.get(Global.MAR.get()));
		// MAR < MBR
		Global.MAR.set(Global.MBR.get());

		
		//User specified at most 16 user defined instructions.
		for (int i = 0; i < index_number; i++) {

			Global.MBR.set(Global.ALU.int2char(i
					+ Global.ALU.char2int(Global.MAR.get())));
			Global.IR.set(Global.MBR.get());
			
			//Note that user defined instructions should not change the value of PC
			Instruction.deCode();
		}
		//PC < PC + 1
		Global.PC.set(Global.ALU.add(Global.PC.get(), 1));
	
	}
}