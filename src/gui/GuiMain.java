/*****************************************************************************
 * FILE    : GuiMain.java                                Rev. 1.0 05/04/2011 *
 *                                                                           *
 * AUTHOR  : Wo Chang                                                        *
 *                                                                           *
 * DATE    : May 4, 2011                                                     *
 *                                                                           *
 * PROJECT : GWU CS6461 Computer Architecture Class                          *
 *                                                                           *
 * This file contains the main GUI part for the ISA simulator project.       *
 *                                                                           *
 * DEPENDS : 1. arc_project.Global: global variables from the simulator      *
 *           2. Java(TM) SE Runtime Environment (build 1.6.0_15-b03)         *
 *                                                                           *
 * Design Approach:                                                          *
 *                                                                           *
 * GuiMain is the main graphical user interface which provides multi-threads *
 * control between GUI side for buttons, LED registers, and user's input via *
 * mouseclick and keyboard as well as communicate with the ISA simulator to  *
 * execute the ISA instructions.                                             * 
 *                                                                           *
 * From the GUI side, a set of LED registers were created to show the status *
 * of the PC, MAR, MBR, R, X0 R0-R3, MSR, MFR, and CC which corresponds to   *
 * ISA simulator.  Whenever the ISA registers are updated, so as the GUI side*
 * of registers. Each GUI register contains two set of values:               *
 *                                                                           *
 *    (a) oldval - earlier value, in case user wants to use earlier value    *  
 *    (b) val - current value which displayed on the GUI LED                 *
 *                                                                           *
 *****************************************************************************/
package gui;

import java.awt.Label;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Vector;
import arc_project.Global;
import arc_project.Instruction;
import arc_project.VectorDemo;


/*****************************************************************************
 * CLASS GuiMain                                                             *
 *                                                                           *
 * GUIMain extends the Frame with MouseListener and MouseMotionLister.       *
 *															                 * 
 *****************************************************************************/
public class GuiMain extends Frame implements MouseListener, MouseMotionListener 
{
	private static final long serialVersionUID = 1L;
	/**
	 * Global variables 
	 */
    public Dimension gWinDim = new Dimension (1210,780); 
  	public Dimension gScreenDim = Toolkit.getDefaultToolkit().getScreenSize();
	public String title = "CS6461-Computer Architecture";
	public String Authors = "Zhuojie Zhou, Leenarat Leelapanyalert, Wo Chang";
    public Color gBgColor = Color.BLACK;
    public Color gFgColor = Color.WHITE;
    public Color gDARKBLUE = new Color (62,103,153);
    public Color gDARKGREEN = new Color (0,74,74);

    // Global panels declaration 
    public String InternalRegister = "Internal Registers";
    public String Console = "Console";
    public String Input = "Input";
    static public GuiPanel ConsolePanel = null;
    static public GuiPanel InputPanel = null;
    static public GuiPanel ChoicePanel = null;
    public int gPanelX=750, gPanelW=420;

    // Global fonts declaration
    public Font H1font = null;
    public Font H2font = null;
    public Font H3font = null;
    public Font H4font = null;
    public FontMetrics h1fm;
    public FontMetrics h2fm;
    public FontMetrics h3fm;
    public FontMetrics h4fm;
    
    public Frame root = null;            // pointer to the window frame
	public Image img[] = new Image[30];  // buttons and registers images 
	public int gTimer=1000;              // thread timer in msec  
    public Vector<GuiObj> vobj = null;   // objects of buttons & registers
    public Vector<GuiRect> vrect = null; // mouse sensitive rectangles
    public GuiObj gObj = null;           // current selected object
    public GuiRect gRect = null;         // current sensitive rectangle 
    public String strEvent = "";         // for debugging purpose 
  	public GuiThread gThread;            // reference to the GuiThread
  	public String gUSR=null;             // user-defined register
  	public Label labStatus = null;       // execution status label
  	public Boolean binit = false;        // initialization complete or not
  	public long starttime = 0L;          // start timer
  	public long totaltime = 0L;          // accumulation execution time 
  	public Boolean gTimerOn = false;     // timer on/off
  	public int gNumISA = 0;              // # of ISA executed 
  	public Boolean bTraceOn = false;     // allows console printout
  	public Boolean bFirstTime = true;    // init Console panel
  	public int gNumPaint = 0;            // debug: # of paint occurs
  	public int gNuminitReg = 0;          // debug: # of initReg occurs
  	public int gNumDrawReg = 1;          // debug: # of drawReg occurs
  	public int gNumSkipISA = 14500;      // skip displaying # of ISAs 
  	public Boolean bDoneInitReg = false; // init all registers 
  	
	/*************************************************************************
	 * CLASS GuiMain                                                         *
	 *                                                                       *
	 * GUIMain extends the Frame with MouseListener and MouseMotionLister.   * 
	 *************************************************************************/
	public GuiMain () 
	{
		this.addMouseListener(this);

		this.addMouseMotionListener(this);	
		this.addWindowListener (new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});

		// Global variables initialization
		root = this;
	    vobj = new Vector <GuiObj>();
	    vrect = new Vector <GuiRect>();
	    gUSR = new String ("0000000000000000"); 
	    String family = "Lucida Sans Typewriter";
	    H1font = new Font(family, Font.PLAIN, 46);
	    H2font = new Font(family, Font.PLAIN, 26);
	    H3font = new Font(family, Font.ITALIC, 18);
	    H4font = new Font("TimesRoman", Font.PLAIN, 11);
	    
	    // setup GuiMain
		try {
			initGui ();
		} catch (Exception e) {
			e.printStackTrace();
		}
		initPanel (this);	
		try {
			refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
		binit = true;
	}

	/*************************************************************************
	 * outToConsole                                                          *
	 *                                                                       *
	 * Input  : msg - message to be displayed                                *
	 *                                                                       *
	 * Return : void                                                         *
	 *                                                                       *
	 * Output message to Console Panel.                                      * 
	 *************************************************************************/
    public void outToConsole (String msg) 
    {        
    	if (msg != null) 
    	{
    		if(ConsolePanel != null) ConsolePanel.printMsg(msg);
    	}
    }
    
	/*************************************************************************
	 * Out                                                                   *
	 *                                                                       *
	 * Input  : msg - message to be displayed                                *
	 *                                                                       *
	 * Return : void                                                         *
	 *                                                                       *
	 * A quick way to output message to System.out.print.                    *
	 * If bTraceOn is false, nothing will be displayed.                      * 
	 *************************************************************************/
    public void Out (String msg)
    {
    	if (bTraceOn) System.out.print(msg+"\n");
    }
    
	/*************************************************************************
	 * resetTimer                                                            *
	 *                                                                       *
	 * Input  : none                                                         *
	 *                                                                       *
	 * Return : void                                                         *
	 *                                                                       *
	 * reset the starttime timer in order to keep track the total execution  *
	 * time for Program 1 and 2.                                             *
	 *************************************************************************/
    public void resetTimer ()
    {
    	starttime = new Date().getTime();
    }

	/*************************************************************************
	 * addExecutionTime                                                      *
	 *                                                                       *
	 * Input  : none                                                         *
	 *                                                                       *
	 * Return : void                                                         *
	 *                                                                       *
	 * accumulate the total execution time for Program 1 and 2.              *
	 *************************************************************************/
    public void addExecutionTime ()
    {
    	totaltime += new Date().getTime()-starttime;
    }

	/*************************************************************************
	 * printExecutionTime                                                    *
	 *                                                                       *
	 * Input  : none                                                         *
	 *                                                                       *
	 * Return : void                                                         *
	 *                                                                       *
	 * Output the execution time & number of ISA instructions been processed *
	 *************************************************************************/
    public void printExecutionTime ()
    {
    	labStatus.setText("Execution time (ms): "+totaltime+" for "+gNumISA+" ISA instructions");
    }

	/*************************************************************************
	 * initGui                                                               *
	 *                                                                       *
	 * Input  : none                                                         *
	 *                                                                       *
	 * Return : void                                                         *
	 *                                                                       *
	 * Initialize basic GUI components: button images, buttons, registers... *
	 *************************************************************************/
    public void initGui ()
    {
    	img[0] = Toolkit.getDefaultToolkit().getImage("./image/POWERoff.png"); 
	    img[1] = Toolkit.getDefaultToolkit().getImage("./image/POWERon.png"); 
	    img[2] = Toolkit.getDefaultToolkit().getImage("./image/RUNoff.png"); 
	    img[3] = Toolkit.getDefaultToolkit().getImage("./image/RUNon.png");	      
	    img[4] = Toolkit.getDefaultToolkit().getImage("./image/HALToff.png");
	    img[5] = Toolkit.getDefaultToolkit().getImage("./image/HALTon.png");
	    img[6] = Toolkit.getDefaultToolkit().getImage("./image/IPLoff.png");
	    img[7] = Toolkit.getDefaultToolkit().getImage("./image/IPLon.png");
	    img[8] = Toolkit.getDefaultToolkit().getImage("./image/DEBUGoff.png");
	    img[9] = Toolkit.getDefaultToolkit().getImage("./image/DEBUGon.png");
	    img[10] = Toolkit.getDefaultToolkit().getImage("./image/STEPoff.png");
	    img[11] = Toolkit.getDefaultToolkit().getImage("./image/STEPon.png");
	    img[12] = Toolkit.getDefaultToolkit().getImage("./image/CONToff.png");
	    img[13] = Toolkit.getDefaultToolkit().getImage("./image/CONTon.png");
	    img[14] = Toolkit.getDefaultToolkit().getImage("./image/yellow.png");
	    img[15] = Toolkit.getDefaultToolkit().getImage("./image/green.png");
	    img[16] = Toolkit.getDefaultToolkit().getImage("./image/red.png");
	    img[17] = Toolkit.getDefaultToolkit().getImage("./image/empty.png");
	    img[18] = Toolkit.getDefaultToolkit().getImage("./image/switch_yellow.png");
	    img[19] = Toolkit.getDefaultToolkit().getImage("./image/switch_green.png");
	    img[20] = Toolkit.getDefaultToolkit().getImage("./image/switch_empty.png");
	    img[21] = Toolkit.getDefaultToolkit().getImage("./image/led_empty.png");
	    img[22] = Toolkit.getDefaultToolkit().getImage("./image/set3.png");
	    img[23] = Toolkit.getDefaultToolkit().getImage("./image/undo3.png");
	    img[24] = Toolkit.getDefaultToolkit().getImage("./image/clear.png");

	    int x=50, y=140, w=28, h=28;
	    int tx = 248, ty=480+60, tw=-7;
	    int dw=5, dy=w+5, id=0, gap=15;   
	    int imgw = 87; //img[2].getWidth(root);
	    int imgh = 49; //img[2].getHeight(root);
	    
	    String sz1bit = "0";
	    String sz2bits = "00";
	    String sz4bits = "0000";
	    String sz6bits = "000000";
	    String sz16bits = "0000000000000000";    
	      
	    // type = -1 for button; otherwise for LEDs
	    addButton (-1, 0,  "Power", x,72,imgw,imgh);
	    addButton (-1, 2,  "Run", tx,ty,imgw,imgh); tx += imgw+tw; 
	    addButton (-1, 4,  "Halt", tx, ty,imgw,imgh); tx += imgw+tw;
	    addButton (-1, 6,  "IPL", tx, ty,imgw,imgh); tx += imgw+tw;
	    addButton (-1, 8,  "Debug", tx, ty,imgw,imgh); tx += imgw+tw;
	    addButton (-1, 10, "Step", tx, ty,imgw,imgh); tx += imgw+tw;
	    addButton (-1, 12, "Cont", tx, ty,imgw,imgh); 

	    // Draw Main Registers
	    addReg (id++, "USR", sz16bits, x,y,w,h, Color.GREEN,"V"); y += dy;
	    addReg (id++, " PC", sz16bits, x,y,w,h, Color.GREEN,"V"); y += dy;
	    addReg (id++, "MAR", sz16bits, x,y,w,h, Color.GREEN,"V"); y += dy;
	    addReg (id++, "MBR", sz16bits, x,y,w,h, Color.GREEN,"V"); y += dy;
	    addReg (id++, " IR", sz16bits, x,y,w,h, Color.GREEN,"V"); y += dy;
	    addReg (id++, " X0", sz16bits, x,y,w,h, Color.GREEN,"V"); y += dy;
	    addReg (id++, " R0", sz16bits, x,y,w,h, Color.GREEN,"V"); y += dy;
	    addReg (id++, " R1", sz16bits, x,y,w,h, Color.GREEN,"V"); y += dy;
	    addReg (id++, " R2", sz16bits, x,y,w,h, Color.GREEN,"V"); y += dy;
	    addReg (id++, " R3", sz16bits, x,y,w,h, Color.GREEN,"V"); y += dy;
	    addReg (id++, "DLN", sz16bits, x,y,w,h, Color.GREEN,"V"); y += dy;
	    addReg (id++, "MSR", sz16bits, x,y,w,h, Color.GREEN,"V"); y += dy;	    
	    addReg (id++, "MFR", sz4bits, x,y,w,h, Color.RED,"V"); y += dy;	    
	    addReg (id++, " CC", sz4bits, x,y,w,h, Color.RED,"V"); y += dy;
	    
	    // Draw Internal Registers	    
	    x = 15;
	    y +=55;
	    id = 0;
	    addReg (id++, "OPCODE", sz6bits, x,y,w,h, Color.GREEN,"H"); 
    	x += sz6bits.length() * (w+dw)+gap;
	    addReg (id++, "I", sz1bit, x,y,w,h, Color.GREEN,"H");
    	x += sz1bit.length() * (w+dw)+gap;
	    addReg (id++, "IX", sz1bit, x,y,w,h, Color.GREEN,"H"); 
    	x += sz1bit.length() * (w+dw)+gap;
	    addReg (id++, "RSR", sz2bits, x,y,w,h, Color.GREEN,"H"); 
    	x += sz2bits.length() * (w+dw)+gap;
	    addReg (id++, "ADDR", sz6bits, x,y,w,h, Color.GREEN,"H"); 
    	x += sz6bits.length() * (w+dw)+gap;
	    addReg (id++, "EA", sz6bits, x,y,w,h, Color.GREEN,"H"); 
    	x += sz6bits.length() * (w+dw)+gap;
	    setRegister ("USR", "0000000000000000");
	}

	/*************************************************************************
	 * updateRegister                                                        *
	 *                                                                       *
	 * Input  : String regname - name of register to be updated              *
	 *          String regval - value to be updated                          *
	 *                                                                       *
	 * Return : void                                                         *
	 *                                                                       *
	 * Update a specific register with its new value.                        *
	 *                                                                       *
	 * Note: due to the time it needs to draw the LEDs (doesn't matter       *
	 * drawing a small image or using AWT drawing tools), it takes ~125 secs *
	 * to updates 17000 ISA instructions. In order to speed-up the execution *
	 * time for Program 1 and Program 2, the "updateRegister" will only show *
	 * LED updates for the last 2000 ISA instructions. However, at the Debug *
	 * mode, "updateRegister" will display all LEDs for each instruction.    *
	 *************************************************************************/
    public void updateRegister (String regname, char[] regval)
	{
    	String strval = new String (regval); 
    	GuiObj o = getObj (regname);
	    if (o == null) 
	    {
	    	outToConsole ("** ERROR from updateRegister **");
	    }
	    else
	    {
		    if (binit)
		    {
		    	if (!bDoneInitReg || 
		    		(!o.val.equals(strval) && gNumDrawReg > gNumSkipISA) || 
	    			(gThread.gDebug && gThread.gStep) || 
	    			GuiPanel.bUserInputDone == false)
		    	{
		    		//o.setValue(strval);
		    		DrawReg (o, strval);
		    		o.setValue(strval);
		    	}
		    }
	    }
	}

	/*************************************************************************
	 * DrawReg                                                               *
	 *                                                                       *
	 * Input  : GuiObj o - register object                                   *
	 *          String newval - new value for a given register               *
	 *                                                                       *
	 * Return : void                                                         *
	 *                                                                       *
	 * Display register's new value.                                         *
	 * In order to optimize the execution, only re-draw LEDs with changed    *
	 * values.                                                               *
	 *************************************************************************/
	public void DrawReg (GuiObj o, String newval)
	{
		Graphics g = root.getGraphics();		
		int size = o.val.length();
		int gw = 33;
		int x = o.x;
		int y = o.y; 
		int imgIdx;
		if (o.dir.equals("H")) y += 10; // common registers
		else
			x = o.x + h2fm.stringWidth(o.name) + 12;
		for (int j=0; j<size; j++)
        {			
			if (!o.val.substring(j,j+1).equals(newval.substring(j,j+1)))
	        {		
	        	if (newval.substring(j,j+1).equals("0"))
	        	{
	        		//DrawCircle (g, x, y, 40, 40, Color.YELLOW);
	        		g.drawImage(img[o.imgOffIdx], x, y, this);
	        	}
	        	else
	        	{
	        		//DrawCircle (g, x, y, 40, 40, Color.GREEN);
	        		g.drawImage(img[o.imgOnIdx], x, y, this);
	        	}
	        }
	    imgIdx = o.imgOffIdx;
		if (o.val.substring(j,j+1).equals("1")) imgIdx = o.imgOnIdx;
		if (o.name.equals("USR"))
			addButton (o.id, imgIdx, o.name+"_"+j, x, y, 34, 34);
        x += gw;        
        }
	  	UpdateStatus ("Execution time (ms): "+totaltime+" for "+gNumISA+" ISA instructions");
	}

	/*************************************************************************
	 * DrawCircle                                                            *
	 *                                                                       *
	 * Input  : Graphics g - graphic handler                                 *
	 *          int x, y, w, h - position where circle supposed to draw      *
	 *          Color c - color the circle supposed to draw                  *
	 *                                                                       *
	 * Return : void                                                         *
	 *                                                                       *
	 * Draw a circle at a given location (x,y) with its width & height (w,h).*
	 * Note: it was hoping using AWT drawing tools would speed-up the LEDs   *
	 * displaying time, but it didn't.                                       * 
	 *************************************************************************/
    public void DrawCircle (Graphics g, int x, int y, int w, int h, Color c)
    {
	    g.setColor(c);
	    g.fillRoundRect (x, y, w-12, h-12, 20, 20);
	    g.setColor(Color.black);
	    g.drawRoundRect (x, y, w-12, h-12, 20, 20);
    }

	/*************************************************************************
	 * setRegister                                                           *
	 *                                                                       *
	 * Input  : String regname - register name                               *
	 *          String regval - register value                               *
	 *                                                                       *
	 * Return : GuiObj - register object got updated.                        *
	 *                                                                       *
	 * Update the given GUI register with its new value without updating the *
	 * ISA side of register.                                                 *
	 *************************************************************************/
    public GuiObj setRegister (String regname, String regval)
	{
    	GuiObj o = getObj (regname);
	    if (o == null) 
	    {
	    	outToConsole ("** ERROR from setRegister **");
	    	return null;
	    }
	    o.setValue(regval);
	    if (binit) DrawRegister (o);
	    
	    return o;
	}

	/*************************************************************************
	 * resetRegister                                                         *
	 *                                                                       *
	 * Input  : String regname - register name                               *
	 *                                                                       *
	 * Return : GuiObj - register object got updated.                        *
	 *                                                                       *
	 * Update the given register with its new value without updating the ISA *
	 * side of register.                                                     *
	 *************************************************************************/
    public GuiObj resetRegister (String regname)
	{
	    GuiObj o = getObj (regname);
	    if (o == null) 
	    {
	    	outToConsole ("***********************");
	    	return null;
	    }
	    o.resetValue ();
	    DrawRegister (o);
	    return o;
	}

	/*************************************************************************
	 * commitRegister                                                        *
	 *                                                                       *
	 * Input  : String regname - register name                               *
	 *          String regval - register value                               *
	 *                                                                       *
	 * Return : GuiObj - register object got updated.                        *
	 *                                                                       *
	 * Update the given register with its new value AND also updating the    *
	 * ISA side of register.                                                 *
	 *************************************************************************/
    public GuiObj commitRegister  (String regname, String regval)
	{
	    GuiObj o = getObj (regname);
	    if (o == null) 
	    {
	    	outToConsole ("***********************");
	    	return null;
	    }
	    o.commitValue(regname, regval);
	    return o;
	}
    
	/*************************************************************************
	 * initPanel                                                             *
	 *                                                                       *
	 * Input  : GuiMain gMain - points to the root Frame handler             *
	 *                                                                       *
	 * Return : void                                                         *
	 *                                                                       *
	 * Initialize basic GUI components: button images, buttons, registers... *
	 *************************************************************************/	
    public void initPanel (GuiMain gMain)
	{    	
		ConsolePanel = new GuiPanel(gPanelX,370,gPanelW,360, Color.DARK_GRAY, 1);
		ConsolePanel.setLocation(gPanelX, 370);
		ConsolePanel.setSize (gPanelW, 360);
        ConsolePanel.setBackground(Color.DARK_GRAY);

        Color lightYellow = new Color (255, 255, 183);
		InputPanel = new GuiPanel(gPanelX,370+20,gPanelW,160, lightYellow,2);
		ConsolePanel.textArea.setBackground(Color.DARK_GRAY);

		ChoicePanel = new GuiPanel(gPanelX+90,370+20+280,gPanelW-90,20, Color.WHITE,3);
		ChoicePanel.choice.setBackground(Color.WHITE);

		labStatus = new Label ("Status: ");
		labStatus.setLocation(100,700);
		labStatus.setFont(H4font);  
		labStatus.setBackground(Color.BLACK);
		labStatus.setForeground(Color.WHITE);
		
		this.add(ConsolePanel);
		this.add(InputPanel);
		this.add(ChoicePanel);
		this.add(labStatus);
		this.setVisible(true);
	    int x = (gScreenDim.width - gWinDim.width) / 2;
	    int y = (gScreenDim.height - gWinDim.height) / 2;
	    this.setLocation(x, y);
	    this.setSize(gWinDim.width, gWinDim.height);
	    this.setBackground (gBgColor);
	    
	    gThread = new GuiThread (this); 
	 }
	
	/*************************************************************************
	 * getLoader                                                             *
	 *                                                                       *
	 * Input  : none                                                         *
	 *                                                                       *
	 * Return : void                                                         *
	 *                                                                       *
	 * Upon user clicks on the IPL button, "getLoader" is called to load the *
	 * basic ISA instructions into the ISA simulator.                        *
	 *************************************************************************/
	public void getLoader () 
	{	
		String[] S = {
		"1010010000010100",
		"1111010000000010",
	    "0000100100000000",
		"0000010000010100",
		"0001100000000001",
		"0000100000010100",
		"0000010001010101",
		"0001110001000001",
		"0000100001010101",
		"0010110001000010",
		"0011010000111111",		
		"0000000000000000",
		"0000000000000000",
		"0000000000000000",
		"0000000000000000",
		"0000000000000000",
		"0000000000000000",
		"0000000000000000",
		"0000000000010101"
		};
		
		for (int i = 2; i < S.length + 2; i ++){
			try {
				Global.MEMORY.set(Global.ALU.int2char(i), S[i-2].toCharArray());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {		
			Global.PC.set(Global.ALU.int2char(2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*************************************************************************
	 * clearPreviousRec                                                      *
	 *                                                                       *
	 * Input  : Graphics g - graphic handler for drawing                     *
	 *                                                                       *
	 * Return : void                                                         *
	 *                                                                       *
	 * Clear the previous sensible rectangle box.                            *
	 *************************************************************************/
	public void clearPreviousRect (Graphics g)
	{
		if (gRect != null)
		{
			int px = gRect.x+3;
			int pw = gRect.w-6;
			g.setColor (Color.black);
			if (gRect.name.equals("USR_CLEAR")) { px = gRect.x-1; pw = gRect.w-8; }
			g.drawRect(px, gRect.y+1, pw, gRect.h-2);
			gRect = null;
		}
	}
	
	/*************************************************************************
	 * mouseMoved                                                            *
	 *                                                                       *
	 * Input  : MouseEvent e - mouse event handler                           *
	 *                                                                       *
	 * Return : void                                                         *
	 *                                                                       *
	 * Tracking mouse movement (x,y); if is within a sensitive rectangle,    *
	 * draw a thin red box around the object.                                *
	 *************************************************************************/
    public void mouseMoved(MouseEvent e) 
    {       
    	int x = e.getX();
    	int y = e.getY();
    	int px, pw;
    	GuiRect r;
    	Graphics g = root.getGraphics();
    	strEvent = null;

  		int i = getRect (x, y);
  		clearPreviousRect (g);

   		if (i >= 0)
   		{
   			r = vrect.elementAt(i);
   			gRect = r;
   			px = r.x+3;
   			pw = r.w-6;
 
   			g.setColor (Color.red);
			if (r.name.equals("USR_CLEAR")) { px = r.x-1; pw = r.w-8; }
			g.drawRect(px, r.y+1, pw, r.h-2);
    		strEvent = "****** Inside Rect "+r.name+": x="+x+", y="+y;
    	}    
   	}      
 
	/*************************************************************************
	 * mouseClicked                                                          *
	 *                                                                       *
	 * Input  : MouseEvent e = mouse event handler                           *
	 *                                                                       *
	 * Return : void                                                         *
	 *                                                                       *
	 * Executive appropriately upon mouse click on sensitive boxes.          *
	 *************************************************************************/
    public void mouseClicked(MouseEvent e)
    {       
       	int x = e.getX();
    	int y = e.getY();
    	GuiRect r;
    	Graphics g = root.getGraphics();
    	
    	strEvent = "Mouse click: x="+x+", y="+y;
    	
    	if (vrect != null)
    	{
    		int i = getRect (x,y); 
    		if (i >= 0)
	   		{
    	  		clearPreviousRect (g);
	   			r = vrect.elementAt(i);
	   			gRect = r;
	   			int idx;
	   			String str;
	   			if (r.name.equals("Power")) {
	   				
	   			}
	   			if (r.name.equals("Run")) 
	   			{ 
	   				InputPanel.textField.setText("Input>");
	   				resetTimer ();
	   				gTimerOn = true; 
	   				gThread.gRun=true; 
	   				outToConsole ("Click RUN"); 
	   			}
	   			if (r.name.equals("Halt")) 
	   			{ 
	   				gThread.gRun=false; 
	   				gThread.gDebug=false; 
	   				gThread.gStep=false;
	   				printExecutionTime();
	   				outToConsole ("Click Halt"); 
	   			}
	   			if (r.name.equals("IPL")) 
	   			{ 
	   				labStatus.setText ("Status:"); 
	   				getLoader(); 
	   				gNumDrawReg = 1; 
	   				InputPanel.textField.setText("Input>");
	   				ConsolePanel.gNumIn = 1;
	   				ConsolePanel.bFirstTime = true;
	   				ConsolePanel.Alltext = "";
	   				outToConsole ("Click IPL"); 
	   			}
	   			if (r.name.equals("Debug")){ gThread.gDebug=true; }
	   			if (r.name.equals("Step")) { gThread.gStep=true; }
	   			if (r.name.equals("Cont")) { gThread.gDebug=false; gThread.gStep=false; outToConsole ("Click CONT"); }
	   			if (r.name.equals("USR_CLEAR")) 
	   			{ 
	   				gUSR="0000000000000000"; 
	   				DrawRegister ("USR");
	   				try {
	   					commitRegister ("USR", gUSR);
	   				} catch (Exception e1) {
	   					e1.printStackTrace();
	   				} 
	   				r.on = true;
	   			}
	   			if (r.name.contains("YES")) 
	   			{
	   				str = r.name.substring(0,3);
	   				try {
						setRegister (str, gUSR);
						commitRegister (str, gUSR);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
	   			}
	   			if (r.name.contains("NO")) 
	   			{
	   				str = r.name.substring(0,3);
	   				resetRegister (str);
	   				r.on = true; 
	   			}
		
	   			if (r.id == -1)
	   			{
		   			if (r.on)
		    			g.drawImage(img[r.imgid], r.x, r.y, root);
	    			else
		    			g.drawImage(img[r.imgid+1], r.x, r.y, root);
	   			}
	   			
	   			// user clicking on the user-define register
	   			if (r.name.contains("USR_") && !r.name.equals("USR_CLEAR"))
	   			{
	   				idx = Integer.parseInt(r.name.substring (4));
	   				if (r.on)
		   			{
		   				str = "0"; 
		    			g.drawImage(img[r.imgid], r.x, r.y, root);
		   			}
	    			else
	    			{
	    				str = "1";
	    				g.drawImage(img[r.imgid+1], r.x, r.y, root);	   				
	    			}
	   				gUSR = gUSR.substring(0,idx)+str+gUSR.substring(idx+1);
	   				r.on = !r.on;
	   				//DEBUG ("USR LEDS: "+gUSR+",len ="+gUSR.length()+", idx ="+idx);

	   			}
	   			else
	    			g.drawImage(img[r.imgid], r.x, r.y, root);
	   				
	    		//r.on = !r.on;    		
	    		strEvent = "****** Inside MouseClick "+r.name+": x="+x+", y="+y;
	    	}
    	}
    	Out (strEvent);    
    }       

    // No need to executive the following mouse events
    public void mouseDragged(MouseEvent  e) {}
    public void mouseExited(MouseEvent   e) {} 
	public void mousePressed(MouseEvent  e) {}		
	public void mouseReleased(MouseEvent e) {} 
	public void mouseEntered(MouseEvent  e) {} 
    
	/*************************************************************************
	 * DrawPanel                                                             *
	 *                                                                       *
	 * Input  : Graphics g - graphic handler                                 *
	 *          Color c - given a color name                                 *
	 *                                                                       *
	 * Draw the basic GUI components: button images, buttons, registers...   *
	 *************************************************************************/
	public void DrawPanel (Graphics g, Color c)
	  {
	    int y=140;   // beginning of set of LEDs 
		Dimension d = getSize();
	    int brdw = 10;
	    int py = y;
	    int textX, textY, textW, textT=78;
	    int powerX;

	    h1fm = getFontMetrics(H1font);
	    h2fm = getFontMetrics(H2font);
	    h3fm = getFontMetrics(H3font);
	    h4fm = getFontMetrics(H4font);

	    textW = h1fm.stringWidth (title);
	    textX = (d.width - textW) / 2;
	    textY = textT;
	    powerX = textX + textW+40;
	    vrect.elementAt(0).x = powerX;
	        
	    // Draw headings
	    g.setFont(H1font);
	    g.setColor(Color.GREEN);
	    g.drawString(title, textX, textY);     
	    g.setFont(H2font);
	    textW = h2fm.stringWidth (Authors);
	    textX = (d.width - textW) / 2;
	    textY = h1fm.getHeight() + 62;
	    g.drawString(Authors, textX, textY);
	    int ty=480+30;
	    
	    // draw outlines and labels
	    g.setFont(H3font);
	    g.setColor(gDARKBLUE);
        g.drawRoundRect(brdw, textY+brdw, d.width-3*brdw,d.height-3*brdw-textY,10,10);
    	
        // Draw labels
    	int textX2;
        ty = py+10;
        textW = h3fm.stringWidth(Console);
        textX = gPanelX + (gPanelW - textW) / 2;
        textX2 = textX + textW; 
        g.setColor(Color.WHITE);
        g.drawString(Console,textX, ty+5);
             
        ty = py+402;
        textW = h3fm.stringWidth(Input);
        textX = gPanelX + (gPanelW - textW) / 2;
        textX2 = textX + textW; 
        g.setColor(Color.WHITE);
        g.drawString(Input,textX, ty+5);
        
        // Draw Internal Registers label
        ty = py+430+43;
        textW = h3fm.stringWidth(InternalRegister);
        textX = (d.width - textW) / 2;
        textX2 = textX + textW; 
        g.drawLine(3*brdw, ty, textX-3*brdw, ty);
        g.drawLine(textX2+3*brdw, ty, d.width-4*brdw, ty);
        g.setColor(Color.WHITE);
        g.drawString(InternalRegister,textX, ty+5);
        g.drawString("Available Programs",textX+390, ty+5+44);

        // Draw buttons and registers
        DisplayButtons (g);
        ShowRegisters (g); 
	}

	/*************************************************************************
	 * DisplayButtons                                                        *
	 *                                                                       *
	 * Input  : Graphics g - graphic handler                                 *
	 *                                                                       *
	 * Display all necessary buttons.                                        *
	 *************************************************************************/
	public void DisplayButtons (Graphics g) 
	{   
	    g.setFont(H2font);
	    h2fm = getFontMetrics(H2font);
	    g.setColor(Color.WHITE); 
	 
	    // Draw buttons
		GuiRect r;
		for (int i=0; i<vrect.size(); i++)
		{
			r = vrect.elementAt(i);
			if (r.id == -1)  //for buttons
			{
				if (!r.on) 
					g.drawImage(img[r.imgid], r.x, r.y, root);			
				else 
					g.drawImage(img[r.imgid+1], r.x, r.y, root);
			}
			else // for "check" and "uncheck"
			if (r.name.substring(0,4).equals("USR_"))  //for buttons
			{
				if (!r.on) 
					g.drawImage(img[r.imgid], r.x, r.y, root);			
				else 
					g.drawImage(img[r.imgid+1], r.x, r.y, root);
			}
			else
				g.drawImage(img[r.imgid], r.x, r.y, root);
			
		}
	}


	/*************************************************************************
	 * DrawRegister                                                          *
	 *                                                                       *
	 * Input  : GuiObj o - pointer to given register object                  *
	 *                                                                       *
	 * Draw a given register LEDs by the register object.                    *
	 *************************************************************************/
	public void DrawRegister (GuiObj o)
	{
		Graphics g = root.getGraphics();		
		int size = o.val.length();
		int gw = 33;
		int x = o.x;
		int y = o.y; 
		int imgIdx;
		if (o.dir.equals("H")) 
			y += 10; // common registers
		else
			x = o.x + h2fm.stringWidth(o.name) + 12;
		for (int j=0; j<size; j++)
        {			
        if (o.val.substring(j,j+1).equals("0"))
           g.drawImage(img[o.imgOffIdx], x, y, this);
        else
	       g.drawImage(img[o.imgOnIdx], x, y, this);
        imgIdx = o.imgOffIdx;
		if (o.val.substring(j,j+1).equals("1")) imgIdx = o.imgOnIdx;
		if (o.name.equals("USR"))
			addButton (o.id, imgIdx, o.name+"_"+j, x, y, 34, 34);
        x += gw;        
        }
	}

	/*************************************************************************
	 * DrawRegister                                                          *
	 *                                                                       *
	 * Input  : String regname - register name                               *
	 *                                                                       *
	 * Draw a given register LEDs by the register name.                      *
	 *************************************************************************/
	public void DrawRegister (String regname)
	{
		GuiObj o = getObj (regname);
		DrawRegister (o);
	}

	/*************************************************************************
	 * ShowRegisters                                                         *
	 *                                                                       *
	 * Input  : Graphics g - graphic handler                                 *
	 *                                                                       *
	 * Instantiate all registers with their locations.                       *
	 *************************************************************************/
	public void ShowRegisters (Graphics g) 
	{
		int dw=5, dy=26, len;
		int size;
		int x, y, w;
	    
	    g.setFont(H2font);
	    h2fm = getFontMetrics(H2font);
	    g.setColor(Color.WHITE); 
	 
		// Draw registers
		GuiObj o;
		for (int i=0; i<vobj.size(); i++)
		{
			o = vobj.elementAt(i);
			size = o.val.length();
			w = o.w; 
			x = o.x;
			y = o.y;
			
			if (o.dir.equals("V")) // common registers
			{
				len = h2fm.stringWidth(o.name);
				g.drawString (o.name, len, o.y+27);
				x = o.x + len + 12;
				w = 28;

				DrawRegister (o);
				x = 16 * 33 + len + 65; 
			    if (o.name.equals("USR")) 
		    	{
		       		if (getRect (o.name+"_CLEAR") == null)
		       			addButton (o.id, 24, o.name+"_CLEAR", x+4, y, 84, 35);
		       	}
			    }
				if (!o.name.equals("MFR") && !o.name.equals(" CC") && !o.name.equals("USR") && o.dir.equals("V")) 
			    {
		       		if (getRect (o.name+"_YES") == null)
		       		{
					int tw = 42, ty = 35;
			    	addButton (o.id, 22, o.name+"_YES", x, y, tw, ty);
			    	addButton (o.id, 23, o.name+"_NO", x+38, y, tw, ty);
		       		}
				}

		    if (o.dir.equals("H")) // internal registers
		    {
		    	int tx;
		    	dy = 10;
		    	w = 28;
		    	len = h2fm.stringWidth(o.name);
		    	size = o.val.length();
	    		tx = (size * (w+dw) - len)/2;
		    	tx += x;
		    	g.drawString(o.name, tx, y+dy);
		    	DrawRegister (o); 
		    }	 
		}
	}

	/*************************************************************************
	 * getRect                                                               *
	 *                                                                       *
	 * Input  : String regname - register name                               *
	 *                                                                       *
	 * return : GuiRect - rectangle object                                   *
	 *                                                                       *
	 * Fetch the rectangle object based from the register name.              *
	 *************************************************************************/
	public GuiRect getRect (String name)
	{
		for (int i=0; i<vrect.size(); i++)
		{
			if (vrect.elementAt(i).name.equalsIgnoreCase(name)) return vrect.elementAt(i); 
		}
		return null;
	}

	/*************************************************************************
	 * getRect                                                               *
	 *                                                                       *
	 * Input  : int x, int y - mouse position in (x,y)                       *
	 *                                                                       *
	 * return : int - index to the rectangle object                          *
	 *                                                                       *
	 * Return the index number for the given mouse (x,y) position            *
	 *************************************************************************/
	public int getRect (int x, int y)
	{
		GuiRect r;
		Boolean found = false;
		for (int i=0; !found && i<vrect.size(); i++)
		{
			r = vrect.elementAt(i);
			if (x > r.x && y > r.y && x < r.x+r.w && y < r.y+r.h) return i;
		}
		return -1;
	}

	/*************************************************************************
	 * getObj                                                                *
	 *                                                                       *
	 * Input  : String name - mouse sensible object name                     *
	 *                                                                       *
	 * return : GuiObj - object handler                                      *
	 *                                                                       *
	 * Retrieve the mouse sensible object by the given object name.          *
	 *************************************************************************/
	public GuiObj getObj (String name)
	{
		for (int i=0; i<vobj.size(); i++)
		{
			if (vobj.elementAt(i).name.equalsIgnoreCase(name)) return vobj.elementAt(i); 
		}
		return null;
	}

	/*************************************************************************
	 * getObj                                                                *
	 *                                                                       *
	 * Input  : int x, y - current (x,y) mouse position                      * 
	 *                                                                       *
	 * return : GuiObj - object handler                                      *
	 *                                                                       *
	 * Retrieve the mouse sensible object by the given mouse (x,y) position  *
	 *************************************************************************/
	public int getObj (int x, int y)
	{
		GuiObj o;
		for (int i=0; i<vobj.size(); i++)
		{
			o = vobj.elementAt(i);
			if (x > o.x && y > o.y && x < o.x+o.w && y < o.y+o.h) return i;
		}
		return -1;
	}

	/*************************************************************************
	 * addButton                                                             *
	 *                                                                       *
	 * Input  : int id - button id                                           *
	 *          int imgid - image index number                               *
	 *          String name - button name                                    *
	 *          int x, y - (x,y) position of the given button                *
	 *          int w, h - width & height of the given button                *
	 *                                                                       *
	 * return : void                                                         *
	 *                                                                       *
	 * Create a button given by the button id, image index, (x,y) position,  *
	 * and given width and height                                            *
	 *************************************************************************/
	public void addButton (int id, int imgid, String name, int x, int y, int w, int h)
	{
		GuiRect r;

		r = getRect (name);
		if (r == null)
		{
			r = new GuiRect ();
			r.name = name; r.on = false; r.id = id; r.imgid = imgid;
			r.x = x; r.y = y; r.w = w; r.h = h;
			vrect.add(r);
		}
	}

	/*************************************************************************
	 * addReg                                                                *
	 *                                                                       *
	 * Input  : int id - register id                                         *
	 *          String name - given register name                            *
	 *          String val - given register value                            *
	 *          int x, y - (x,y) position of the given register              *
	 *          int w, h - width & height of the given register              *
	 *          Color c - register color at the ON state                     *
	 *                                                                       *
	 * return : void                                                         *
	 *                                                                       *
	 * Create a register given by the register id, name, (x,y) position,     *
	 * width & height, and register color.                                   *
	 *************************************************************************/
	public void addReg (int id, String name, String val, int x, int y, int w, int h, Color c, String dir)
	{
		GuiObj o;

		o = getObj (name);
		if (o == null)
		{
			o = new GuiObj ();
			x +=2; y -= 2;
			w = 40; h = 40; 
			o.name = name; o.on = false; o.id = id;
			o.x = x; o.y = y; o.w = w; o.h = h;
			o.val = val; o.oldval = val; o.c = c; o.dir = dir;
			o.size = o.val.length();
			if (c.equals(Color.GREEN)) {o.imgOnIdx = 15; o.imgOffIdx = 14;}
			if (c.equals(Color.RED)) {o.imgOnIdx = 16; o.imgOffIdx = 14; }
			if (name.equals("USR")) { o.imgOnIdx = 19; o.imgOffIdx = 18; }
			vobj.add(o);
		}
	}

	/*************************************************************************
	 * fetchRegistersValue                                                   *
	 *                                                                       *
	 * Input  : int id - button id                                           *
	 *          int imgid - image index number                               *
	 *          String name - button name                                    *
	 *          int x, y - (x,y) position of the given button                *
	 *          int w, h - width & height of the given button                *
	 *                                                                       *
	 * return : void                                                         *
	 *                                                                       *
	 * Create a button given by the button id, image index, (x,y) position,  *
	 * and given width and height                                            *
	 *************************************************************************/
	public void fetchRegistersValue ()
	{
		updateRegister (" PC",Global.PC.get());
		updateRegister ("MAR",Global.PC.get());
		updateRegister ("MBR",Global.PC.get());
		updateRegister (" R0",Global.R[0].get());
		updateRegister (" R1",Global.R[1].get());
		updateRegister (" R2",Global.R[2].get());
		updateRegister (" R3",Global.R[3].get());
		updateRegister ("DLN",Global.DLN.get());
		updateRegister (" IR",Global.IR.get());
		updateRegister (" X0",Global.X0.get());
		updateRegister ("MSR",Global.MSR.get());
		updateRegister ("MFR",Global.MFR.get());
		updateRegister (" CC",Global.CC.get());
		gNumDrawReg++;
		if (!bDoneInitReg) 
		{
			Graphics g = this.getGraphics(); 
			bDoneInitReg = true;
			ShowRegisters (g); 
		}
		gNuminitReg++;
	}
	
	/*************************************************************************
	 * refresh                                                               *
	 *                                                                       *
	 * Input  : none                                                         *
	 *                                                                       *
	 * return : void                                                         *
	 *                                                                       *
	 * Call from the ISA simulator "decode" to refresh all register values.  *
	 *************************************************************************/
	public void refresh ()
	{
		fetchRegistersValue();
	}
	
	/*************************************************************************
	 * paint                                                                 *
	 *                                                                       *
	 * Input  : Graphics g - graphic handler                                 *
	 *                                                                       *
	 * return : void                                                         *
	 *                                                                       *
	 * Re-paint the top Frame window with panels, buttons, registers, etc.   *
	 *************************************************************************/
	public void paint(Graphics g)
	  {
		gNumPaint++;
		DrawPanel (g, Color.BLACK);
        DisplayButtons (g);
        ShowRegisters (g); 
	    ConsolePanel.setLocation(gPanelX, 161);
		ConsolePanel.setSize (gPanelW, 360);
	    InputPanel.setLocation(gPanelX, 552);
		InputPanel.setSize (gPanelW, 30);
	  	UpdateStatus ("Execution time (ms): "+totaltime+" for "+gNumISA+" ISA instructions");
	  }

	/*************************************************************************
	 * UpdateStatus                                                          *
	 *                                                                       *
	 * Input  : String str - message string                                  *
	 *                                                                       *
	 * return : void                                                         *
	 *                                                                       *
	 * Message to be display at the status line.                             *
	 *************************************************************************/
	public void UpdateStatus (String str)
	{
		int textX, textW;
		Dimension d = getSize();
        textW = h4fm.stringWidth(str)+10;
        textX = 2+(d.width - textW) / 2;
        
		labStatus.setLocation (textX,720);
		labStatus.setSize (textW,15);
		labStatus.setText(str); 
	}

	public void vector_cache_demo() throws Exception
	{
		VectorDemo vectordemo = new VectorDemo();
		vectordemo.demo();
	}
	
}
