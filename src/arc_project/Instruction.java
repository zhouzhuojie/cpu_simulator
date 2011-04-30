/**
 * All the Global instance will be here
 * 
 * @author Zhuojie Zhou
 * @version (a version number or a date)
 */
package arc_project;

import isa.*;

/**
 * @author zzj
 * 
 */
public abstract class Instruction {

	public static void deCode() throws Exception {
		
		
		String sPC = new String(Global.PC.get());
		String sDLN = new String(Global.DLN.get());
		if (sPC.equals(sDLN)) {
			Global.GUIMAIN.gThread.gDebug = true;
			Global.GUIMAIN.gThread.gStep = true;

		}

		Global.GUIMAIN.refresh();
		
		/* Changed by Leen */
		// 1) MAR < PC
		Global.MAR.set(Global.PC.get());
		// 2) MBR < Memory(MAR)
		Global.MBR.set(Global.L1.get(Global.MAR.get()));
		// 3) IR < MBR
		Global.IR.set(Global.MBR.get());
		// 4) OPCODE < IR 0-5
		Global.OPCODE.set(Global.IR.get(0, 5));
		// Convert Opcode to int in order to use it in the switch statement.
		int Opcode = 0;
		for (int i = 0; i < 6; i++) {
			if (Global.OPCODE.get(i) == '1')
				Opcode = Opcode + (int) Math.pow(2, 6 - 1 - i);
		}
		switch (Opcode) {

		case 0:
			InstructionSet_HLT hlt = new InstructionSet_HLT();
			hlt.operate();
			break;
		case 30:
			InstructionSet_TRAP trap = new InstructionSet_TRAP();
			trap.operate();
			break;
		case 1:
			InstructionSet_LDR ldr = new InstructionSet_LDR();
			ldr.operate();
			break;
		case 2:
			InstructionSet_STR str = new InstructionSet_STR();
			str.operate();
			break;
		case 3:
			InstructionSet_LDA lda = new InstructionSet_LDA();
			lda.operate();
			break;
		case 41:
			InstructionSet_LDX ldx = new InstructionSet_LDX();
			ldx.operate();
			break;
		case 42:
			InstructionSet_STX stx = new InstructionSet_STX();
			stx.operate();
			break;
		case 10:
			InstructionSet_JZ jz = new InstructionSet_JZ();
			jz.operate();
			break;
		case 11:
			InstructionSet_JNE jne = new InstructionSet_JNE();
			jne.operate();
			break;
		case 12:
			InstructionSet_JCC jcc = new InstructionSet_JCC();
			jcc.operate();
			break;
		case 13:
			InstructionSet_JMP jmp = new InstructionSet_JMP();
			jmp.operate();
			break;
		case 14:
			InstructionSet_JSR jsr = new InstructionSet_JSR();
			jsr.operate();
			break;
		case 15:
			InstructionSet_RFS rfs = new InstructionSet_RFS();
			rfs.operate();
			break;
		case 16:
			InstructionSet_SOB sob = new InstructionSet_SOB();
			sob.operate();
			break;
		case 4:
			InstructionSet_AMR amr = new InstructionSet_AMR();
			amr.operate();
			break;
		case 5:
			InstructionSet_SMR smr = new InstructionSet_SMR();
			smr.operate();
			break;
		case 6:
			InstructionSet_IAR iar = new InstructionSet_IAR();
			iar.operate();
			break;
		case 7:
			InstructionSet_ISR isr = new InstructionSet_ISR();
			isr.operate();
			break;
		case 20:
			InstructionSet_MUL mul = new InstructionSet_MUL();
			mul.operate();
			break;
		case 21:
			InstructionSet_DIV div = new InstructionSet_DIV();
			div.operate();
			break;
		case 22:
			InstructionSet_TST tst = new InstructionSet_TST();
			tst.operate();
			break;
		case 23:
			InstructionSet_AND and = new InstructionSet_AND();
			and.operate();
			break;
		case 24:
			InstructionSet_OR or = new InstructionSet_OR();
			or.operate();
			break;
		case 25:
			InstructionSet_NOT not = new InstructionSet_NOT();
			not.operate();
			break;
		case 31:
			InstructionSet_SRC src = new InstructionSet_SRC();
			src.operate();
			break;
		case 32:
			InstructionSet_RRC rrc = new InstructionSet_RRC();
			rrc.operate();
			break;
		case 61:
			InstructionSet_IN in = new InstructionSet_IN();
			in.operate();
			break;
		case 62:
			InstructionSet_OUT out = new InstructionSet_OUT();
			out.operate();
			break;
		case 63:
			InstructionSet_CHK chk = new InstructionSet_CHK();
			chk.operate();
			break;
		case 35:
			InstructionSet_VADD vadd = new InstructionSet_VADD();
			vadd.operate();
			break;
		case 36:
			InstructionSet_VSUB vsub = new InstructionSet_VSUB();
			vsub.operate();
			break;

		default:
			char[] machine_fault_2 = {'0','0','1','0'};
			Global.MFR.set(machine_fault_2);
			throw new Exception("Illegal Opcode!");

		}


	}

	/* Added by Leen */
	public static char[] EA() throws Exception {

		// 1) IF (IXI == 1) THEN
		if (Global.IXI.get(0) == '1') {
			// EA < X0 + ADDR
			Global.EA.set(Global.ALU.add(Global.X0.get(), Global.ADDR.get()));
			// ELSE
		} else {
			// EA < ADDR
			char[] x = Global.ADDR.get();
			char[] y = { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
					'0', '0', '0', '0', '0' };
			y[10] = x[0];
			y[11] = x[1];
			y[12] = x[2];
			y[13] = x[3];
			y[14] = x[4];
			y[15] = x[5];

			Global.EA.set(y);

		}
		// 2) IF (IND == 1) THEN
		if (Global.IND.get(0) == '1') {
			// MAR < EA
			Global.MAR.set(Global.EA.get());
			// MBR < Memory(MAR)
			Global.MBR.set(Global.L1.get(Global.MAR.get()));
			// EA < MBR
			Global.EA.set(Global.MBR.get());
		}

		return Global.EA.get();
	}

	public abstract void operate() throws Exception;

}
