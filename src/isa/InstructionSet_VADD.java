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

		//IND <- IR 6
		Global.IND.set(Global.IR.get(6),0);
		//IXI <- IR 7
		Global.IXI.set(Global.IR.get(7),0);		
		//ADDR <- IR 10-15
		Global.ADDR.set(Global.IR.get(10,15));

		// FOR i=0; i<32
		for (int i = 0; i < 32; i++) {
			Global.VREG1[i] = new Register(16);  // Vector1
			Global.VREG2[i] = new Register(16);  // Vector2
		    
			/******************************************************************************
			 * Load vector from memory to Vector Register Array 1.
			 *****************************************************************************/
			
			// MAR < Effective Address
            Global.MAR.set(Instruction.EA());
            // MBR < L1(MAR)
			Global.MBR.set(Global.L1.get(Global.MAR.get()));
			// MAR < MBR + i
			Global.MAR.set(Global.ALU.int2char(
                        i + Global.ALU.char2int(Global.MBR.get())));
			// MBR < L1(MAR)
			Global.MBR.set(Global.L1.get(Global.MAR.get()));
			// Vector1[i] < MBR
			Global.VREG1[i].set(Global.MBR.get());
			
			
			/******************************************************************************
			 * Load vector from memory to Vector Register Array 2.						  *
			 *****************************************************************************/
			// MAR < Effective Address + 1
			Global.MAR.set(Global.ALU.int2char(
                        1 + Global.ALU.char2int(Instruction.EA())));
			// MBR < L1(MAR)
			Global.MBR.set(Global.L1.get(Global.MAR.get()));
			// MAR < MBR + i
			Global.MAR.set(Global.ALU.int2char(
                        i + Global.ALU.char2int(Global.MBR.get())));
			// MBR < L1(MAR)
			Global.MBR.set(Global.L1.get(Global.MAR.get()));
			// Vector2[i] < MBR
			Global.VREG2[i].set(Global.MBR.get());

			
			/******************************************************************************
			 * Add these two register arrays.											  *
			 *****************************************************************************/
			// Vector1[i] = Vector1[i] + Vector2[i]
			Global.VREG1[i].set(Global.ALU.num2char(Global.ALU
					.char2num(Global.VREG1[i].get())
					+ Global.ALU.char2num(Global.VREG2[i].get())));

			
			/******************************************************************************
			 * Write the results to memory.												  *
			 *****************************************************************************/
			// L1[Vector1[i]] < i + Register(RSR)
			Global.MAR.set(Instruction.EA());
			Global.MBR.set(Global.MEMORY.get(Global.MAR.get()));
			Global.MAR.set(Global.ALU.int2char(i
					+ Global.ALU.char2int(Global.MBR.get())));
			Global.L1.set(Global.MAR.get(), Global.VREG1[i].get());

		}
		// PC < PC + 1
		Global.PC.set(Global.ALU.add(Global.PC.get(), 1));
	}

}
