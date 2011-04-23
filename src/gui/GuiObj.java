package gui;

import java.awt.Color;
import java.awt.Image;

import arc_project.Global;

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
	//	System.out.println("setValue: name ="+this.name+", val="+newval); 
	
	
	}
	
	public void commitValue (String regname, String newval)
	{
		if (regname.equals(" PC"))
			try {
				Global.PC.set(newval.toCharArray());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		if (regname.equals(" CC"))
			try {
				Global.CC.set(newval.toCharArray());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		if (regname.equals(" IR"))
			try {
				Global.IR.set(newval.toCharArray());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		if (regname.equals("MAR"))
			try {
				Global.MAR.set(newval.toCharArray());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		if (regname.equals("MBR"))
			try {
				Global.MBR.set(newval.toCharArray());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//		if (regname == "MSR")
//			try {
//				Global.MSR.set(regval.toCharArray());
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

		if (regname.equals("MFR"))
			try {
				Global.MFR.set(newval.toCharArray());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		if (regname.equals(" X0"))
			try {
				Global.X0.set(newval.toCharArray());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
		if (regname.equals(" R0"))
			try {
				Global.R[0].set(newval.toCharArray());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (regname.equals(" R1"))
			try {
				Global.R[1].set(newval.toCharArray());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (regname.equals(" R2"))
			try {
				Global.R[2].set(newval.toCharArray());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (regname.equals(" R3"))
			try {
				Global.R[3].set(newval.toCharArray());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		if (regname.equals("DLN"))
			try {
				Global.DLN.set(newval.toCharArray());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
//		if (this.size <= newval.length())
//			this.val = newval.substring(0, this.size);
//		this.oldval = this.val;
//		try {
//			Global.PC.set(newval.toCharArray());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//System.out.println("setValue: name ="+this.name+", val="+newval); 
	}

	public void resetValue ()
	{
		this.val = this.oldval;
		//System.out.println("resetValue: name ="+this.name+", val="+this.val); 
	}

}

