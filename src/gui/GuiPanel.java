/*****************************************************************************
 * FILE    : GuiPanel.java                               Rev. 1.0 05/04/2011 *
 *                                                                           *
 * AUTHOR  : Wo Chang                                                        *
 *                                                                           *
 * DATE    : May 4, 2011                                                     *
 *                                                                           *
 * PROJECT : GWU CS6461 Computer Architecture Class                          *
 *                                                                           *
 * This file contains the general GUI panel structures.                      *
 *                                                                           *
 * DEPENDS : 1. arc_project.Global: global variables from the simulator      *
 *           2. Java(TM) SE Runtime Environment (build 1.6.0_15-b03)         *
 *****************************************************************************/
package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import arc_project.Global;

/*****************************************************************************
 * CLASS GuiPanel                                                            *
 *                                                                           *
 * GuiPanel provides general panel structure for TextArea, TextField & Choice*
 *****************************************************************************/
public class GuiPanel extends Panel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public TextArea textArea = null;
	public TextField textField = null;
	public Choice choice = null; 
	public static char textCh; 
	public ScrollPane scrollpane = null;
	public static int gNumIn = 1;
	public static Boolean bNewLine = true;
	public Vector<String> vStr = null;
	public static Boolean gInputDone = false;
	public static int inputIdx = 0; 
	public static Boolean bFirstTime = true;
	public static String Alltext = "";
	public static String ProgramName = "Program 1";
	public static Boolean bUserInputDone = true;
	static Color gDARKGREEN = new Color(0,102,102);
	
	public void update (Graphics g) { paint(g); }

	/*************************************************************************
	 * printMsg                                                              *
	 *                                                                       *
	 * Input  : String msg - message to be displayed                         *       
	 *                                                                       *
	 * Show text at the Console Panel.                                       *
	 *************************************************************************/	
	public void printMsg (String msg) 
	{
		Alltext = Alltext + msg;
		this.textArea.setText(Alltext);
	}

	/*************************************************************************
	 * GuiPanel                                                              *
	 *                                                                       *
	 * Input  : int x, y: (x,y) position of a given panel                    *
	 *          int w, h: width & height of a given panel                    *
	 *          Color c: background color of a given panel                   *
	 *          int type: panel type: 1=TextArea, 2=TextField, 3=Choice      *       
	 *                                                                       *
	 * return : void                                                         *
	 *                                                                       *
	 * Restore the previous value to the displayable register.               *
	 *************************************************************************/	
	public GuiPanel(int x, int y, int w, int h, Color c, int type) {
		Font b = null;
		this.setLayout(new BorderLayout());
		
		if (type == 1)
		{
			b = new Font("TimesRoman", Font.PLAIN, 11);
			textArea = new TextArea(10, 10); 
			textArea.setBackground(c);
			textArea.setForeground(Color.WHITE);
			textArea.setFont(b);
			FontMetrics fm = textArea.getFontMetrics(b);
			h = fm.getHeight();
			textArea.setSize(w,h);
			textArea.setEditable(false);
			textArea.setEnabled(true);
			textArea.setVisible(true);
			add(textArea);
		}
		if (type == 2)
		{
			textField = new TextField ("input>", 35);
			textField.addKeyListener(new MyKeyBoard());
			textField.setBackground(c);	
			add(textField);
		}

		if (type == 3)
		{
			choice = new Choice();
			choice.add ("Program 1");
			choice.add ("Program 2");
			choice.add ("Vector and Cache Demo");
			choice.addItemListener(itemListener);
			add(choice);
		}

		setSize(w,h);
		setLocation (x,y);
		setBackground (c);
	}
	
	/*************************************************************************
	 * Create itemListener to handle Choice selection, which is either       *
	 * "Program 1" or "Program 2".                                           *                                                            *
	 *************************************************************************/	
    ItemListener itemListener = new ItemListener() {
      public void itemStateChanged(ItemEvent itemEvent) {
    	  ProgramName = choice.getSelectedItem(); 
    	  if (ProgramName.equals("Program 1"))
    		  Global.GUIMAIN.gNumSkipISA = 14500;
       	  if (ProgramName.equals("Program 2"))
    		  Global.GUIMAIN.gNumSkipISA = 32500;
       	  if (ProgramName.equals("Vector and Cache Demo"))
			try {
				Global.GUIMAIN.vector_cache_demo();
			} catch (Exception e) {

				e.printStackTrace();
			} 
      }
    };

    /*****************************************************************************
     * CLASS MyKeyBoard                                                          *
     *                                                                           *
     * Provides keyboard adapter for intercepting keyboard input.                *
     *****************************************************************************/
    public class MyKeyBoard extends KeyAdapter
	  {
	    public void keyTyped(KeyEvent ke)
	    {
	    	char c = ke.getKeyChar();
		    if (bFirstTime)
		    {
				bUserInputDone = false;
		    	Global.GUIMAIN.outToConsole ("\n"+gNumIn+"> ");
		    	bFirstTime = false;
		    }
		    textCh = c; 
		    if (textCh == ' ')
		    {
			    gNumIn++;
			    Global.GUIMAIN.outToConsole ("\n"+gNumIn+">");
		    }
		    ProcessKeyboardKey(c);
		}
	  }  
	  	  
	  /*************************************************************************
	   * ProcessKeyboardKey                                                    *
	   *                                                                       *
	   * Input  : char c - incoming keyboard key                               *       
	   *                                                                       *
	   * return : void                                                         *
	   *                                                                       *
	   * Process incoming key from keyboard.  If input is 10 (CR) then inform  *
	   * the ISA simulator to proess the rest of the program.                  *
	   *************************************************************************/	
	  public void ProcessKeyboardKey (char c)
	  {
		  int num;
		  num = (int)c;
		  if (num == 10) 
		  {
			  bUserInputDone = true;
		   	  if (ProgramName.equals("Program 1")) num = 13;
		   	  Global.GUIMAIN.outToConsole ("\nAnswer is: ");		    	  
		  }
		  char[] c0=null;
		  try {
			c0 = Global.ALU.int2char(num);
		  } catch (Exception e1) {
			  e1.printStackTrace();
		  }
		  try {
			  Global.R[Global.ALU.char2int(Global.RSR.get())].set(c0);
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		  Global.GUIMAIN.gThread.gKeyboardOn = false;
		}
}

