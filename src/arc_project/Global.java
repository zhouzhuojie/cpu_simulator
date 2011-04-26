/**
 * All the Global instance will be here
 * 
 * @author Zhuojie Zhou
 * @version (a version number or a date)
 */
package arc_project;

import java.io.BufferedReader;
import java.io.FileInputStream;

import gui.*;

public class Global {

	public static Register PC = new Register(16); // Program Counter: address of
													// next instruction to be
													// executed

	public static Register CC = new Register(4); // Condition Code: set when
													// arithmetic/logical
													// operations are executed;
													// it has four 1-bit
													// elements: overflow,
													// underflow, division by
													// zero, equal-or-not. They
													// may be referenced as
													// cc(1), cc(2), cc(3),
													// cc(4). Or by the names
													// OVERFLOW, UNDERFLOW,
													// DIVZERO, EQUALORNOT

	public static Register IR = new Register(16); // Instruction Register: holds
													// the instruction to be
													// executed

	public static Register MAR = new Register(16);// Memory Address Register:
													// holds the address of the
													// word to be fetched from
													// memory

	public static Register MBR = new Register(16);// Memory Buffer Register:
													// holds the word just
													// fetched from or stored
													// into memory

	public static Register MSR = new Register(16);// Machine Status Register:
													// certain bits record the
													// status of the health of
													// the machine

	public static Register MFR = new Register(4); // Machine Fault Register:
													// contains the ID code if a
													// machine fault after it
													// occurs

	public static Register X0 = new Register(16); // Index Register: contains a
													// 16-bit base address that
													// supports base register
													// addressing of memory.

	/* Old Design */
	/*
	 * public static Register R0 = new Register(16); //General Purpose
	 * Registers, index 0 public static Register R1 = new Register(16);
	 * //General Purpose Registers, index 1 public static Register R2 = new
	 * Register(16); //General Purpose Registers, index 2 public static Register
	 * R3 = new Register(16); //General Purpose Registers, index 3
	 */

	/* Changed by Leen */

	public static Register R0 = new Register(16); // General Purpose Registers,
													// index 0
	public static Register R1 = new Register(16); // General Purpose Registers,
													// index 1
	public static Register R2 = new Register(16); // General Purpose Registers,
													// index 2
	public static Register R3 = new Register(16); // General Purpose Registers,
													// index 3

	public static Register R[] = { R0, R1, R2, R3 };; // General Purpose
														// Registers

	/* Added by Leen */
	public static Register OPCODE = new Register(6); // OPCODE
	public static Register IND = new Register(1); // Indirect Addressing
	public static Register IXI = new Register(1); // Indexing
	public static Register RSR = new Register(2); // Identify General Purpose
													// Register
	public static Register RSR2 = new Register(2); // Identify Second General
													// Purpose Register
	public static Register ADDR = new Register(6); // Address
	public static Register IMMED = new Register(6); // Immediate
	public static Register EA = new Register(16); // Effective Addressing ***
													// This is just temporary
													// register. It actually
													// doesn't exist in the
													// design ***
	public static Register Rx = new Register(2); // Identify General Purpose
													// Register for First
													// Operand ALU
	public static Register Ry = new Register(2); // Identify General Purpose
													// Register for Second
													// Operand ALU
	public static Register RX = new Register(16); // First Operand for ALU
	public static Register RY = new Register(16); // Second Operand for ALU
	public static Register LR = new Register(1); // Direction: Left/Right
	public static Register AL = new Register(1); // Type: Arithmetic/Logical
	public static Register COUNT = new Register(4); // Count for Shift/Rotate
	public static Register TR = new Register(16); // Temporary Register for
													// Shift/Rotate
	public static Register SR = new Register(16); // Shift Register
	public static Register RR = new Register(16); // Rotate Register
	public static Register DEVID = new Register(5); // Device ID
	public static Register [] VREG1 = new Register[32]; //32 vector Registers
	public static Register [] VREG2 = new Register[32]; //32 vector Registers
	public static Register DLN = new Register(16); // Line number that the
													// programmer want to start
													// to debug.

	// Add any Global tmp Register if you want...

	public static Memory MEMORY = new Memory(16, 16, 8096); // Memory with the
															// word length
															// 16bits, address
															// length 16bits,
															// and can contains
															// 8096 words.
	public static Alu ALU = new Alu(16); // Alu with the max capacity of 16bits.

	public static Cache L1 = new Cache(); // L1 is the L1 cache, which has eight
											// lines of 8 words each, using
											// direct mapping, write through and
											// write no-allocate policies.

	public static GuiMain GUIMAIN = new GuiMain();
	public static FileInputStream fstream = null;
	public static BufferedReader br = null;
	public static FileInputStream fstream0 = null;
	public static BufferedReader br0 = null;

}
