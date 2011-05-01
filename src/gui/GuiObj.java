/*****************************************************************************
 * FILE    : GuiObj.java                                 Rev. 1.0 05/04/2011 *
 *                                                                           *
 * AUTHOR  : Wo Chang                                                        *
 *                                                                           *
 * DATE    : May 4, 2011                                                     *
 *                                                                           *
 * PROJECT : GWU CS6461 Computer Architecture Class                          *
 *                                                                           *
 * This file contains the GUI object of buttons.                             *
 *                                                                           *
 * DEPENDS : 1. arc_project.Global: global variables from the simulator      *
 *           2. Java(TM) SE Runtime Environment (build 1.6.0_15-b03)         *
 *****************************************************************************/
package gui;

import java.awt.Color;
import arc_project.Global;

/*****************************************************************************
 * CLASS GuiObj                                                              *
 *                                                                           *
 * GUIObj holds the GUI side of register info.                               * 
 *****************************************************************************/
public class GuiObj {

	public String name;
	public int id, x=-1, y=-1, w=-1, h=-1, type=-1;
	public String val;
	public String oldval;
	public int imgOnIdx, imgOffIdx;
	public Color c;
	public String dir; 
	public int size;
	public Boolean on = false, sensitive = false;
	public int yx=-1, yy=-1, yw=-1, yh=-1;
	public int nx=-1, ny=-1, nw=-1, nh=-1;
	
	public void setValue (String newval)
	{
		if (this.size <= newval.length())
			this.val = newval.substring(0, this.size); 
	}
	
	/*************************************************************************
	 * commitValue                                                           *
	 *                                                                       *
	 * Input  : String regname - register name                               *
	 *          String newval = new register value                           *       
	 *                                                                       *
	 * return : void                                                         *
	 *                                                                       *
	 * Update ISA side of register content.                                  *
	 *************************************************************************/
	public void commitValue (String regname, String newval)
	{
		if (regname.equals(" PC"))
			try {
				Global.PC.set(newval.toCharArray());
			} catch (Exception e) {
				e.printStackTrace();
			}

		if (regname.equals(" CC"))
			try {
				Global.CC.set(newval.toCharArray());
			} catch (Exception e) {
				e.printStackTrace();
			}

		if (regname.equals(" IR"))
			try {
				Global.IR.set(newval.toCharArray());
			} catch (Exception e) {
				e.printStackTrace();
			}

		if (regname.equals("MAR"))
			try {
				Global.MAR.set(newval.toCharArray());
			} catch (Exception e) {
				e.printStackTrace();
			}

		if (regname.equals("MBR"))
			try {
				Global.MBR.set(newval.toCharArray());
			} catch (Exception e) {
				e.printStackTrace();
			}

//		if (regname == "MSR")
//			try {
//				Global.MSR.set(regval.toCharArray());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}

		if (regname.equals("MFR"))
			try {
				Global.MFR.set(newval.toCharArray());
			} catch (Exception e) {
				e.printStackTrace();
			}

		if (regname.equals(" X0"))
			try {
				Global.X0.set(newval.toCharArray());
			} catch (Exception e) {
				e.printStackTrace();
			}
    	
		if (regname.equals(" R0"))
			try {
				Global.R[0].set(newval.toCharArray());
			} catch (Exception e) {
				e.printStackTrace();
			}
		if (regname.equals(" R1"))
			try {
				Global.R[1].set(newval.toCharArray());
			} catch (Exception e) {
				e.printStackTrace();
			}
		if (regname.equals(" R2"))
			try {
				Global.R[2].set(newval.toCharArray());
			} catch (Exception e) {
				e.printStackTrace();
			}
		if (regname.equals(" R3"))
			try {
				Global.R[3].set(newval.toCharArray());
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		if (regname.equals("DLN"))
			try {
				Global.DLN.set(newval.toCharArray());
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	/*************************************************************************
	 * resetValue                                                            *
	 *                                                                       *
	 * Input  : none                                                         *       
	 *                                                                       *
	 * return : void                                                         *
	 *                                                                       *
	 * Restore the previous value to the displayable register.               *
	 *************************************************************************/	
	public void resetValue ()
	{
		this.val = this.oldval;
	}

}

