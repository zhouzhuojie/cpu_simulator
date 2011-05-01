
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
public class InstructionSet_VSUB extends Instruction {
	/*
	 * (non-Javadoc)
	 * 
	 * @see arc_project.Instruction#operate()
	 * 
	 * @Override
	 */
	public void operate() throws Exception {

		//IND <- IR 6
		Global.IND.set(Global.IR.get(6),0);
		//IXI <- IR 7
		Global.IXI.set(Global.IR.get(7),0);		
		//ADDR <- IR 10-15
		Global.ADDR.set(Global.IR.get(10,15));
		for (int i = 0; i < 32; i++) {
			Global.VREG1[i] = new Register(16);
			Global.VREG2[i] = new Register(16);
		    

            Global.MAR.set(Instruction.EA());
			Global.MBR.set(Global.L1.get(Global.MAR.get()));
			Global.MAR.set(Global.ALU.int2char(
                        i + Global.ALU.char2int(Global.MBR.get())));
			Global.MBR.set(Global.L1.get(Global.MAR.get()));
			Global.VREG1[i].set(Global.MBR.get());

			Global.MAR.set(Global.ALU.int2char(
                        1 + Global.ALU.char2int(Instruction.EA())));
			Global.MBR.set(Global.L1.get(Global.MAR.get()));
			Global.MAR.set(Global.ALU.int2char(
                        i + Global.ALU.char2int(Global.MBR.get())));
			Global.MBR.set(Global.L1.get(Global.MAR.get()));
			Global.VREG2[i].set(Global.MBR.get());



			Global.VREG1[i].set(Global.ALU.num2char(Global.ALU
					.char2num(Global.VREG1[i].get())
					- Global.ALU.char2num(Global.VREG2[i].get())));

            Global.MAR.set(Instruction.EA());
			Global.MBR.set(Global.MEMORY.get(Global.MAR.get()));
			Global.MAR.set(Global.ALU.int2char(
                        i + Global.ALU.char2int(Global.MBR.get())));
			Global.L1.set(Global.MAR.get(),
					Global.VREG1[i].get());
		}
		// PC < PC + 1
		Global.PC.set(Global.ALU.add(Global.PC.get(), 1));
	}

}



