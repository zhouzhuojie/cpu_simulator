/**
 * 
 */
package isa;

import arc_project.Global;
import arc_project.Instruction;
import arc_project.Register;

/**
 * @author zzj
 * 
 */
public class InstructionSet_VADD extends Instruction {
	/*
	 * (non-Javadoc)
	 * 
	 * @see arc_project.Instruction#operate()
	 * 
	 * @Override
	 */
	public void operate() throws Exception {

		Global.RSR.set(Global.IR.get(6, 7));
		Global.RSR2.set(Global.IR.get(8, 9));

		for (int i = 0; i < 32; i++) {
			Global.VREG1[i] = new Register(16);
			Global.VREG2[i] = new Register(16);
			
			Global.MAR.set(Global.ALU.int2char(i
					+ Global.ALU.char2int(Global.R[Global.ALU
							.char2int(Global.RSR.get())].get())));
			Global.MBR.set(Global.L1.get(Global.MAR.get()));
			Global.VREG1[i].set(Global.MBR.get());

			Global.MAR.set(Global.ALU.int2char(i
					+ Global.ALU.char2int(Global.R[Global.ALU
							.char2int(Global.RSR2.get())].get())));
			Global.MBR.set(Global.L1.get(Global.MAR.get()));
			Global.VREG2[i].set(Global.MBR.get());

			Global.VREG1[i].set(Global.ALU.num2char(Global.ALU
					.char2num(Global.VREG1[i].get())
					+ Global.ALU.char2num(Global.VREG2[i].get())));

			Global.L1.set(Global.ALU.int2char(i
					+ Global.ALU.char2int(Global.R[Global.ALU
							.char2int(Global.RSR.get())].get())),
					Global.VREG1[i].get());
		}
		// PC < PC + 1
		Global.PC.set(Global.ALU.add(Global.PC.get(), 1));
	}

}
