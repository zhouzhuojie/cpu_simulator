/*****************************************************************************
 * FILE    : GuiThread.java                              Rev. 1.0 05/04/2011 *
 *                                                                           *
 * AUTHOR  : Wo Chang                                                        *
 *                                                                           *
 * DATE    : May 4, 2011                                                     *
 *                                                                           *
 * PROJECT : GWU CS6461 Computer Architecture Class                          *
 *                                                                           *
 * This file contains the GUI thread to handle execution ISA instructions as *
 * as well capture user inputs.                                              *
 *                                                                           *
 * DEPENDS : 1. arc_project.Global: global variables from the simulator      *
 *           2. Java(TM) SE Runtime Environment (build 1.6.0_15-b03)         *
 *****************************************************************************/
package gui;

import arc_project.*;

/*****************************************************************************
 * CLASS GuiThread                                                           *
 *                                                                           *
 * GuiThread is a runnable thread to process user inputs as well as execute  *
 * ISA instructions.
 *****************************************************************************/
public class GuiThread implements Runnable {

    Thread thread;
    GuiMain gMain;
    public Boolean gRun=false;
    public Boolean gDebug=false;
    public Boolean gStep=false;
    public long gTimer=0;
    public Boolean gKeyboardOn = false;
    
    GuiThread (GuiMain top)     
    {        
    	gMain = top;
    	thread = new Thread (this);        
    	thread.start ( );    
    }   
    
	public void run() {
		while (true) {
			if (gRun) {
				// gMain.gTimerOn = true;
				if (gDebug && gStep) {
					if (gKeyboardOn == false) {
						gMain.resetTimer();
						gMain.Out("line     "
								+ Global.ALU.char2int(Global.PC.get()));
						try {
							Instruction.deCode();
							gMain.gNumISA++;
						} catch (Exception e) {
							e.printStackTrace();
						}
						gMain.addExecutionTime();
						gStep = false;
					}
				} else if (gDebug == false) {
					if (gKeyboardOn == false) {
						gMain.resetTimer();
						gMain.Out("line    "
								+ Global.ALU.char2int(Global.PC.get()));
						try {
							Instruction.deCode();
							gMain.gNumISA++;
						} catch (Exception e) {
							e.printStackTrace();
						}
						gMain.addExecutionTime();
					}
				}
			} else if (gMain.gTimerOn) {
				gMain.gTimerOn = false;
				gMain.printExecutionTime();
			}
			try {
				// delay for one second
				Thread.currentThread().sleep(gTimer);
			} catch (InterruptedException e) {
			}
		}
	}

    public void stop () {}
}
