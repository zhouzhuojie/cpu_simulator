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
public class InstructionSet_JCC extends Instruction {

	/**
	 * 
	 */
	public InstructionSet_JCC() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see arc_project.Instruction#operate()
	 */
	@Override
	public void operate() throws Exception {
		// TODO Auto-generated method stub

//		5)  IND 	< IR 6
		Global.IND.set(Global.IR.get(6),0);
//		   	IXI		< IR 7
		Global.IXI.set(Global.IR.get(7),0);
//		   	RSR		< IR 8-9
		Global.RSR.set(Global.IR.get(8,9));			
//		   	ADDR	< IR 10-15
		Global.ADDR.set(Global.IR.get(10,15));
//		6) 	IF (CC(RSR) == 1) THEN
		if(Global.CC.get(Global.ALU.char2int(Global.RSR.get())) == '1' ){
//		+7)		PC < EA
			Global.PC.set(Instruction.EA());
//		   	ELSE
		} else {
//		+7) 		PC < PC + 1
			Global.PC.set(Global.ALU.add(Global.PC.get(), 1));
		}
		
		//Instruction.deCode();
		
	}

}
