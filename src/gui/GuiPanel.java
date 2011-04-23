package gui;

import java.awt.*;
import javax.swing.*;
import arc_project.*;

import java.awt.event.*;
import java.util.Vector;

import arc_project.Global;

public class GuiPanel extends Panel {
	public TextArea textArea = null;
	public TextField textField = null;
	public static char textCh; 
	public ScrollPane scrollpane = null;
	public static int gNumIn = 1;
	public static Boolean bNewLine = true;
	public Vector<String> vStr = null;
	public static Boolean gInputDone = false;
	public static int inputIdx = 0; 
	public static Boolean bFirstTime = true;
	public static String Alltext = "";

	static Color gDARKGREEN = new Color(0,102,102);
	
	public void update (Graphics g) { paint(g); }

	public void printMsg (String msg) 
	{
		Alltext = Alltext + msg;
		this.textArea.setText(Alltext);
		//this.textArea.setCaretPosition(Alltext.length());
	}

	public GuiPanel(int x, int y, int w, int h, Color c, int type) {
		Font b = null;
		this.setLayout(new BorderLayout());
		
		if (type == 1)
		{
		b = new Font("TimesRoman", Font.PLAIN, 11);
		textArea = new TextArea(10, 10); //,TextArea.SCROLLBARS_VERTICAL_ONLY);
		textArea.setBackground(c);
		textArea.setForeground(Color.WHITE);
		textArea.setFont(b);
		FontMetrics fm = textArea.getFontMetrics(b);
		h = fm.getHeight();
		textArea.setSize(w,h);
		textArea.setEditable(false);
		textArea.setEnabled(true);
		textArea.setVisible(true);
		//JScrollPane scrollingArea = new JScrollPane (textArea);

		//add(scrollingArea);
		add(textArea);
		}
		if (type == 2)
		{
			textField = new TextField ("input>", 35);
			textField.addKeyListener(new MyKeyBoard());
			textField.setBackground(c);	
			add(textField);
		}
		
		setSize(w,h);
		setLocation (x,y);
		setBackground (c);
	}
	
	  public class MyKeyBoard extends KeyAdapter{
		    //public void keyReleased (KeyEvent ke){
			    //public void keyPressed(KeyEvent ke){
		    	public void keyTyped(KeyEvent ke){
		      char c = ke.getKeyChar();
		      if (bFirstTime)
		      {
		    	  Global.GUIMAIN.outToConsole ("\n"+gNumIn+"> ");
		    	  bFirstTime = false;

		      }
		      textCh = c; 
		      if (textCh == ' ')
		      {
			      gNumIn++;
		    	  Global.GUIMAIN.outToConsole ("\n"+gNumIn+">");
//			      bNewLine = true;
		      }
//		      else
//		      {
//			      if (bNewLine) 
//			      {
//			    	  bNewLine = false;
//			      }
//		      }
		      ProcessKeyboardKey(c);
		      //textField.setEnabled(false);
		    }
	  }  
	  
	  public class MyKeyBoard_new extends KeyAdapter{
		    //public void keyPressed(KeyEvent ke){
		    	public void keyTyped(KeyEvent ke){

		      char c = ke.getKeyChar();
		      int num = (int)c; 
		      if (vStr == null) vStr = new Vector<String>(); 
		      if (num == 10) 
		      {
		    	  //num = 13;
		    	  Global.GUIMAIN.outToConsole ("\nAnswer is: ");		    	  
		      }
			  char[] c0=null;
				try {
					c0 = Global.ALU.num2char(num);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String str = new String(c0);
		      vStr.add(str); 
		      if (num == 13)
		      {
		    	  gInputDone = true;
		    	  Global.GUIMAIN.gThread.gKeyboardOn = false;
		    	  for (int i = 0; i<vStr.size(); i++)
		    		  System.out.println (i+"-"+vStr.elementAt(i));
		      }
		      //textField.setEnabled(false);
		    }
	  }  

	  public String getNextInput ()
	  {
		  return vStr.elementAt(inputIdx++); 
	  }
	  
	  public void ProcessKeyboardKey (char c)
		{
		  int num;
		  if (true)
		  { 
//			  if (gNumIn > 22) num = 0;
//			  else num = (int)c;
			   
			  num = (int)c;
		      if (num == 10) 
		      {
		    	 //num = 13;
		    	  Global.GUIMAIN.outToConsole ("\nAnswer is: ");		    	  
		      }
			  String SS = ""+c;
//			  Global.GUIMAIN.Out("**** SS = "+SS+", num = "+num+"\n");
			  System.out.println("**** SS = "+SS+", num = "+num+"\n");
			  char[] c0=null;
			try {
				c0 = Global.ALU.int2char(num);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Global.R[Global.ALU.char2int(Global.RSR.get())].set(c0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Global.GUIMAIN.outToConsole(""+Global.R[Global.ALU.char2int(Global.RSR.get())].get());
			Global.GUIMAIN.gThread.gKeyboardOn = false;
		  }
		  else 
				System.out.print("non-digital not allowed: "+c);
		  //textField.setEnabled(true);
		  Global.GUIMAIN.gThread.gKeyboardOn = false;
		}
		

	  public void paint(Graphics g) {
		//System.out.println("inside MyPanel:paint");

		/*
		textArea.setVisible(false);
		setLocation(740, 170);
		setSize (420, 160);
		textArea.setVisible(true);
		*/
		//mypaint(g);
//		    Global.GUIMAIN.ConsolePanel.setLocation(Global.GUIMAIN.gPanelX, 354);
//			Global.GUIMAIN.ConsolePanel.setSize(Global.GUIMAIN.gPanelW, 160);
	}

	public void mypaint(Graphics g) {
		setLocation(740, 170);
		setSize(420,160);
		
		//System.out.println("exit MyPanel:mypaint");
	}

	// void DEBUG (String msg) { canvas.top.displayThis (msg); }

}

