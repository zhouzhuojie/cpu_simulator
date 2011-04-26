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
	public void operate() throws Exception {

		Global.RSR.set(Global.IR.get(8, 9));
		int index_number = Global.ALU.char2int(Global.R[Global.ALU
				.char2int(Global.RSR.get())].get());

		char[] memory_0 = { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
				'0', '0', '0', '0', '0', '0' };
		Global.MAR.set(memory_0);
		Global.MBR.set(Global.L1.get(Global.MAR.get()));
		Global.MAR.set(Global.MBR.get());

		for (int i = 0; i < index_number; i++) {

			Global.MBR.set(Global.ALU.int2char(i
					+ Global.ALU.char2int(Global.MAR.get())));
			Global.IR.set(Global.MBR.get());
			Instruction.deCode();
		}
	}
}