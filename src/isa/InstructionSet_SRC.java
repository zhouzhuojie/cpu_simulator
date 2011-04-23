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
public class InstructionSet_SRC extends Instruction {

	/**
	 * 
	 */
	public InstructionSet_SRC() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see arc_project.Instruction#operate()
	 */
	@Override
	public void operate() throws Exception {
		// TODO Auto-generated method stub

		int count;
		
//		5)  DIR		< IR 6
		Global.LR.set(Global.IR.get(6),0);
//		   	RSR		< IR 7-8
		Global.RSR.set(Global.IR.get(7,8));	
//		   	STYPE 	< IR 9
		Global.AL.set(Global.IR.get(9),0);
//		   	COUNT 	< IR 12-15
		Global.COUNT.set(Global.IR.get(12,15));
		count = Global.ALU.char2int(Global.COUNT.get());
		char[] z = new char[count];
		for(int i = 0; i<count; i++) z[i] = '0';
//		6) 	SR 	< Register(RSR)
		Global.SR.set(Global.R[Global.ALU.char2int(Global.RSR.get())].get());
//		7) 	IF (AL == 0) THEN				// ARITHMATIC
		if( Global.AL.get(0) == '0' ){
//		+8)		IF (LR == 0) THEN			// SHIFT RIGHT
			if( Global.LR.get(0) == '0' ){
//		++9)			TR 	< SR 1
				Global.TR.reset();
				Global.TR.set(Global.SR.get(1,1+count-1),1);
//		++10)			SR 1-14	< SR 2-15
				Global.SR.set(Global.SR.get(1+count,15),1);
//		++11)			SR 15	< 0
				Global.SR.set(z,16-count);
//		++12)			IF (TR == 1) THEN cc(1) < 1	// OVERFLOW
				if( Global.ALU.char2int(Global.TR.get()) > 0 ) Global.CC.set('1',0);
//				ELSE 						// SHIFT LEFT
			} else {
//		++9)			TR 	< SR 15
				Global.TR.reset();
				Global.TR.set(Global.SR.get(15-count+1,15),15-count+1);
//		++10)			SR 2-15	< SR 1-14
				Global.SR.set(Global.SR.get(1,15-count),1+count);
//		++11)			SR 1	< 0
				Global.SR.set(z,1);
//		++12)			IF (TR == 1) THEN cc(2) < 1	// UNDERFLOW
				if( Global.ALU.char2int(Global.TR.get()) > 0 ) Global.CC.set('1',1);
			}
//			ELSE							// LOGICAL
		} else {			
//		+8)		IF (LR == 0) THEN			// SHIFT RIGHT
			if( Global.LR.get(0) == '0' ){
//		++9)			TR	< SR 0
				Global.TR.reset();
				Global.TR.set(Global.SR.get(0,count-1),0);
//		++10)			SR 0-14 < SR 1-15
				Global.SR.set(Global.SR.get(count,15),0);
//		++11)			SR 15	< 0
				Global.SR.set(z,16-count);
//				ELSE						// SHIFT LEFT
			} else {
//		++9)			TR	< SR 15
				Global.TR.reset();
				Global.TR.set(Global.SR.get(15-count+1,15),15-count+1);
//		++10)			SR 1-15 < SR 0-14
				Global.SR.set(Global.SR.get(0,15-count),count);
//		++11)			SR 0	< 0
				Global.SR.set(z,0);
			}
		}
//		13)	Register(RSR) < SR
		Global.R[Global.ALU.char2int(Global.RSR.get())].set(Global.SR.get());
//		14)	PC	< PC + 1
		Global.PC.set(Global.ALU.add(Global.PC.get(), 1));
		
		//Instruction.deCode();
		
	}

}
