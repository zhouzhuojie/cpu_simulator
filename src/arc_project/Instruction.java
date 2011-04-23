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

	// public static void start()throws Exception{
	// /* Changed by Leen */
	// // 1) MAR < PC
	// Global.MAR.set(Global.PC.get());
	// // 2) MBR < Memory(MAR)
	// Global.MBR.set(Global.MEMORY.get(Global.MAR.get()));
	// // 3) IR < MBR
	// Global.IR.set(Global.MBR.get());
	// // 4) OPCODE < IR 0-5
	// Global.OPCODE.set(Global.IR.get(0,5));
	//
	// //Convert Opcode to int in order to use it in the switch statement.
	// int Opcode = 0;
	// for (int i = 0; i < 6; i++){
	// if (Global.OPCODE.get(i) == '1')
	// Opcode = Opcode + (int)Math.pow(2,6-1-i);
	// }
	//
	//
	// while ()
	//
	//
	// }

	public static void deCode() throws Exception {
		/* Old Design */
		/*
		 * char[] c = Global.IR.get(0, 5); int Opcode = 0;
		 * 
		 * //Convert Opcode to int in order to use it in the switch statement.
		 * for (int i = 0; i < c.length; i++){ if (c[i] == '1') Opcode = Opcode
		 * + (int)Math.pow(2, c.length-1-i); }
		 */

		String sPC = new String(Global.PC.get());
		String sDLN = new String(Global.DLN.get());
		if (sPC.equals(sDLN)) {
			Global.GUIMAIN.gThread.gDebug = true;
			Global.GUIMAIN.gThread.gStep = true;

		}

		// if (Global.ALU.char2int(Global.PC.get()) == 121 ||
		// Global.ALU.char2int(Global.PC.get()) == 122) {
		// System.out.println();
		// System.out.print("*********** = ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(51))));
		// System.out.print("R0 = ");
		// System.out.println(Global.ALU.char2int(Global.R[0].get()));
		// }

		// Global.GUIMAIN.refresh();

		/* Changed by Leen */
		// 1) MAR < PC
		Global.MAR.set(Global.PC.get());
		// 2) MBR < Memory(MAR)
		Global.MBR.set(Global.L1.get(Global.MAR.get()));
		// 3) IR < MBR
		Global.IR.set(Global.MBR.get());
		// 4) OPCODE < IR 0-5
		Global.OPCODE.set(Global.IR.get(0, 5));

		// if (Global.ALU.char2int(Global.PC.get()) == 120) {
		// System.out.println();
		// System.out.println("Memory 1200's 1300's = ");
		// System.out.print(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1201))));
		// System.out.print("  ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1301))));
		// System.out.print(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1202))));
		// System.out.print("  ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1302))));
		// System.out.print(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1203))));
		// System.out.print("  ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1303))));
		// System.out.print(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1204))));
		// System.out.print("  ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1304))));
		// System.out.print(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1205))));
		// System.out.print("  ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1305))));
		// System.out.print(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1206))));
		// System.out.print("  ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1306))));
		// System.out.print(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1207))));
		// System.out.print("  ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1307))));
		// System.out.print(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1208))));
		// System.out.print("  ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1308))));
		// System.out.print(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1209))));
		// System.out.print("  ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1309))));
		// System.out.print(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1210))));
		// System.out.print("  ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1310))));
		// System.out.print(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1211))));
		// System.out.print("  ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1311))));
		// System.out.print(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1212))));
		// System.out.print("  ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1312))));
		// System.out.print(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1213))));
		// System.out.print("  ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1313))));
		// System.out.print(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1214))));
		// System.out.print("  ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1314))));
		// System.out.print(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1215))));
		// System.out.print("  ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1315))));
		// System.out.print(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1216))));
		// System.out.print("  ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1316))));
		// System.out.print(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1217))));
		// System.out.print("  ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1317))));
		// System.out.print(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1218))));
		// System.out.print("  ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1318))));
		// System.out.print(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1219))));
		// System.out.print("  ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1319))));
		// System.out.print(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1220))));
		// System.out.print("  ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1320))));
		// System.out.print(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1221))));
		// System.out.print("  ");
		// System.out.println(Global.ALU.char2int(Global.MEMORY.get(Global.ALU.int2char(1321))));
		//
		// }

		// Convert Opcode to int in order to use it in the switch statement.
		int Opcode = 0;
		for (int i = 0; i < 6; i++) {
			if (Global.OPCODE.get(i) == '1')
				Opcode = Opcode + (int) Math.pow(2, 6 - 1 - i);
		}

//		switch (Opcode) {
//
//		case 0:
//			Global.GUIMAIN.Out("HLT" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 30:
//			Global.GUIMAIN.Out("TRAP" + "" + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 1:
//			Global.GUIMAIN.Out("LDR" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 2:
//			Global.GUIMAIN.Out("STR" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 3:
//			Global.GUIMAIN.Out("LDA" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 41:
//			Global.GUIMAIN.Out("LDX" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 42:
//			Global.GUIMAIN.Out("STX" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 10:
//			Global.GUIMAIN.Out("JZ" + "  " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 11:
//			Global.GUIMAIN.Out("JNE" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 12:
//			Global.GUIMAIN.Out("JCC" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 13:
//			Global.GUIMAIN.Out("JMP" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 14:
//			Global.GUIMAIN.Out("JSR" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 15:
//			Global.GUIMAIN.Out("RFS" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 16:
//			Global.GUIMAIN.Out("SOB" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 4:
//			Global.GUIMAIN.Out("AMR" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 5:
//			Global.GUIMAIN.Out("SMR" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 6:
//			Global.GUIMAIN.Out("IAR" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 7:
//			Global.GUIMAIN.Out("ISR" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 20:
//			Global.GUIMAIN.Out("MUL" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 21:
//			Global.GUIMAIN.Out("DIV" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 22:
//			Global.GUIMAIN.Out("TST" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 23:
//			Global.GUIMAIN.Out("AND" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 24:
//			Global.GUIMAIN.Out("OR" + "  " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 25:
//			Global.GUIMAIN.Out("NOT" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 31:
//			Global.GUIMAIN.Out("SRC" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 32:
//			Global.GUIMAIN.Out("RRC" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 61:
//			Global.GUIMAIN.Out("IN" + "  " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 62:
//			Global.GUIMAIN.Out("OUT" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//		case 63:
//			Global.GUIMAIN.Out("CHK" + " " + Global.IR.get(6) + " "
//					+ Global.IR.get(7) + " " + Global.IR.get(8)
//					+ Global.IR.get(9) + " " + Global.IR.get(10)
//					+ Global.IR.get(11) + Global.IR.get(12) + Global.IR.get(13)
//					+ Global.IR.get(14) + Global.IR.get(15));
//			break;
//
//		default:
//			throw new Exception("Illegal Opcode!");
//
//		}

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

		default:
			throw new Exception("Illegal Opcode!");

		}

		// Global.GUIMAIN.refresh();

//		System.out.print("   PC  ");
//		System.out.print(Global.ALU.char2int(Global.PC.get()));
//		System.out.print("   R0  ");
//		System.out.print(Global.R[0].get());
//		System.out.print("  ");
//		System.out.print("   R1  ");
//		System.out.print(Global.R[1].get());
//		System.out.print("  ");
//		System.out.print("   R2  ");
//		System.out.print(Global.R[2].get());
//		System.out.print("  ");
//		System.out.print("   R3  ");
//		System.out.print(Global.R[3].get());
//		System.out.print("  ");
//		System.out.print("   X0  ");
//		System.out.print(Global.X0.get());
//		System.out.print("  ");

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
