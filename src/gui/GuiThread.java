package gui;

import java.lang.*;

import arc_project.*;

public class GuiThread implements Runnable {

	private String str;
	private int num = 0;
	Thread thread;
	GuiMain gMain;
	public Boolean gRun = false;
	public Boolean gDebug = false;
	public Boolean gStep = false;
	public long gTimer = 0; // 10 mc
	public Boolean gKeyboardOn = false;

	GuiThread(GuiMain top, String s, int n) {
		gMain = top;
		str = new String(s);
		num = n;
		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		while (true) {
			if (gRun) {
				// gMain.gTimerOn = true;
				if (gDebug && gStep) {

					if (gKeyboardOn == false) {
//						gMain.Out("line     "
//								+ Global.ALU.char2int(Global.PC.get()));
						try {
							Instruction.deCode();
							gMain.gNumISA++;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						gStep = false;
					}
				} else if (gDebug == false) {
					if (gKeyboardOn == false) {
//						gMain.Out("line    "
//								+ Global.ALU.char2int(Global.PC.get()));
						try {
							Instruction.deCode();
							gMain.gNumISA++;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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

	public void stop() {
	}
}
