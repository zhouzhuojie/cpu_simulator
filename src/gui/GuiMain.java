/**
 * 
 */
package gui;

/**
 * @author rexzhou
 *
 */
import java.*;
import java.awt.Label;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Vector;

import arc_project.Global;
import arc_project.Instruction;

public class GuiMain extends Frame implements MouseListener,
		MouseMotionListener {

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public char gChar;
	public Image img[] = new Image[30];
	public String title = "CS6461-Computer Architecture";
	public String Authors = "Zhuojie Zhou, Leenarat Leelapanyalert, Wo Chang";
	public int gTimer = 1000;
	public Color gBgColor = Color.BLACK;
	public Color gFgColor = Color.WHITE;
	public Color gDARKBLUE = new Color(62, 103, 153);
	public Color gDARKGREEN = new Color(0, 74, 74);
	public Dimension gWinDim = new Dimension(1210, 780);
	public Dimension gScreenDim = Toolkit.getDefaultToolkit().getScreenSize();
	public String InternalRegister = "Internal Registers";
	public String MemoryBank = "Console";
	public String Console = "Console";
	public String Input = "Input";
	public Font H1font = null;
	public FontMetrics h1fm;
	public Font H2font = null;
	public FontMetrics h2fm;
	public Font H3font = null;
	public FontMetrics h3fm;
	static public GuiPanel MemoryBankPanel = null;
	static public GuiPanel ConsolePanel = null;
	static public GuiPanel InputPanel = null;
	public int gPanelX = 750, gPanelW = 420;
	public Vector<GuiObj> vobj = null;
	public Vector<GuiRect> vrect = null;
	public Frame root = null;
	public GuiObj gObj = null;
	public GuiRect gRect = null;
	public String strEvent = "Original";
	public GuiThread gThread;
	public String gUSR = "0000000000000000";
	public Label labStatus = null;
	public Boolean binit = false;
	public long starttime = 0L;
	public Boolean gTimerOn = false;
	public int gNumISA = 0;
	public Boolean bTraceOn = true;
	public Boolean bFirstTime = true;
	public int gNumPaint = 0;

	public GuiMain() {
		this.addMouseListener(this);

		this.addMouseMotionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});

		// Global init
		root = this;
		vobj = new Vector<GuiObj>();
		vrect = new Vector<GuiRect>();
		String family = "Lucida Sans Typewriter";
		H1font = new Font(family, Font.PLAIN, 46);
		H2font = new Font(family, Font.PLAIN, 26);
		H3font = new Font(family, Font.ITALIC, 18);

		// setup GuiMain
		try {
			initGui();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initPanel(this);
		try {
			refresh();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initRegisterValues();
		binit = true;
	}

	public void outToConsole(String msg) {
		if (msg != null) {
			if (ConsolePanel != null)
				ConsolePanel.printMsg(msg);
			// ConsolePanel.printMsg(msg+"\n");

		}
	}

	public void Out(String str) {
		if (bTraceOn)
			System.out.print(str + "\n");
	}

	public long getCurrentTime() {
		return new Date().getTime();
	}

	public void printExecutionTime() {
		long delTime = new Date().getTime() - starttime;
		float persec = gNumISA / (delTime + 1);
		labStatus.setText("Execution time (ms): " + delTime + " for " + gNumISA
				+ " ISA instructions"); // or "+persec+" ISA/sec");
	}

	public void initGui() {
		img[0] = Toolkit.getDefaultToolkit().getImage("image/POWERoff.png");
		img[1] = Toolkit.getDefaultToolkit().getImage("image/POWERon.png");
		img[2] = Toolkit.getDefaultToolkit().getImage("image/RUNoff.png");
		img[3] = Toolkit.getDefaultToolkit().getImage("image/RUNon.png");
		img[4] = Toolkit.getDefaultToolkit().getImage("image/HALToff.png");
		img[5] = Toolkit.getDefaultToolkit().getImage("image/HALTon.png");
		img[6] = Toolkit.getDefaultToolkit().getImage("image/IPLoff.png");
		img[7] = Toolkit.getDefaultToolkit().getImage("image/IPLon.png");
		img[8] = Toolkit.getDefaultToolkit().getImage("image/DEBUGoff.png");
		img[9] = Toolkit.getDefaultToolkit().getImage("image/DEBUGon.png");
		img[10] = Toolkit.getDefaultToolkit().getImage("image/STEPoff.png");
		img[11] = Toolkit.getDefaultToolkit().getImage("image/STEPon.png");
		img[12] = Toolkit.getDefaultToolkit().getImage("image/CONToff.png");
		img[13] = Toolkit.getDefaultToolkit().getImage("image/CONTon.png");
		img[14] = Toolkit.getDefaultToolkit().getImage("image/yellow.png");
		img[15] = Toolkit.getDefaultToolkit().getImage("image/green.png");
		img[16] = Toolkit.getDefaultToolkit().getImage("image/red.png");
		img[17] = Toolkit.getDefaultToolkit().getImage("image/empty.png");
		img[18] = Toolkit.getDefaultToolkit().getImage(
				"image/switch_yellow.png");
		img[19] = Toolkit.getDefaultToolkit()
				.getImage("image/switch_green.png");
		img[20] = Toolkit.getDefaultToolkit()
				.getImage("image/switch_empty.png");
		img[21] = Toolkit.getDefaultToolkit().getImage("image/led_empty.png");
		img[22] = Toolkit.getDefaultToolkit().getImage("image/set3.png");
		img[23] = Toolkit.getDefaultToolkit().getImage("image/undo3.png");
		img[24] = Toolkit.getDefaultToolkit().getImage("image/clear.png");

		int x = 1090, y = 75, w, h;
		int tx = 248, ty = 480 + 60, tw = -7;
		Dimension d = getSize();
		x = gWinDim.width - 100;

		int imgw = 87; // img[2].getWidth(root);
		int imgh = 49; // img[2].getHeight(root);
		String sz1bit = "1";
		String sz2bits = "11";
		String sz4bits = "1010";
		String sz6bits = "101010";
		String sz16bits = "1010101100110111";

		// type = 1 for button; = 0 for LED
		addButton(-1, 0, "Power", x, y, imgw, imgh);
		addButton(-1, 2, "Run", tx, ty, imgw, imgh);
		tx += imgw + tw;
		addButton(-1, 4, "Halt", tx, ty, imgw, imgh);
		tx += imgw + tw;
		addButton(-1, 6, "IPL", tx, ty, imgw, imgh);
		tx += imgw + tw;
		addButton(-1, 8, "Debug", tx, ty, imgw, imgh);
		tx += imgw + tw;
		addButton(-1, 10, "Step", tx, ty, imgw, imgh);
		tx += imgw + tw;
		addButton(-1, 12, "Cont", tx, ty, imgw, imgh);

		w = 28; // LED width
		h = 28; // LED heigh
		x = 50;
		y = 140; // beginning of set of LEDs
		int dy = w + 5;
		int id = 0;

		// Draw Main Registers
		addReg(id++, "USR", sz16bits, x, y, w, h, Color.GREEN, "V");
		y += dy;
		addReg(id++, " PC", sz16bits, x, y, w, h, Color.GREEN, "V");
		y += dy;
		addReg(id++, "MAR", sz16bits, x, y, w, h, Color.GREEN, "V");
		y += dy;
		addReg(id++, "MBR", sz16bits, x, y, w, h, Color.GREEN, "V");
		y += dy;
		addReg(id++, " IR", sz16bits, x, y, w, h, Color.GREEN, "V");
		y += dy;
		addReg(id++, " X0", sz16bits, x, y, w, h, Color.GREEN, "V");
		y += dy;
		addReg(id++, " R0", sz16bits, x, y, w, h, Color.GREEN, "V");
		y += dy;
		addReg(id++, " R1", sz16bits, x, y, w, h, Color.GREEN, "V");
		y += dy;
		addReg(id++, " R2", sz16bits, x, y, w, h, Color.GREEN, "V");
		y += dy;
		addReg(id++, " R3", sz16bits, x, y, w, h, Color.GREEN, "V");
		y += dy;
		addReg(id++, "DLN", sz16bits, x, y, w, h, Color.GREEN, "V");
		y += dy;
		addReg(id++, "MSR", sz16bits, x, y, w, h, Color.GREEN, "V");
		y += dy;
		addReg(id++, "MFR", sz4bits, x, y, w, h, Color.RED, "V");
		y += dy;
		addReg(id++, " CC", sz4bits, x, y, w, h, Color.RED, "V");
		y += dy;

		// Draw Internal Registers

		int dw = 5, gap = 7;
		x = 15;
		y += 60;
		id = 0;
		addReg(id++, "OPCODE", sz6bits, x, y, w, h, Color.GREEN, "H");
		x += sz6bits.length() * (w + dw) + gap;
		addReg(id++, "I", sz1bit, x, y, w, h, Color.GREEN, "H");
		x += sz1bit.length() * (w + dw) + gap;
		addReg(id++, "IX", sz1bit, x, y, w, h, Color.GREEN, "H");
		x += sz1bit.length() * (w + dw) + gap;
		addReg(id++, "RSR", sz2bits, x, y, w, h, Color.GREEN, "H");
		x += sz2bits.length() * (w + dw) + gap;
		addReg(id++, "ADDR", sz6bits, x, y, w, h, Color.GREEN, "H");
		x += sz6bits.length() * (w + dw) + gap;
		addReg(id++, "EA", sz6bits, x, y, w, h, Color.GREEN, "H");
		x += sz6bits.length() * (w + dw) + gap;
		addReg(id++, "TMP1", sz6bits, x, y, w, h, Color.GREEN, "H");
		x += sz6bits.length() * (w + dw) + gap;
		addReg(id++, "TMP2", sz6bits, x, y, w, h, Color.GREEN, "H");

		setRegister("USR", "0000000000000000");
	}

	public GuiObj setRegister(String regname, String regval) {
		GuiObj o = getObj(regname);
		if (o == null) {
			outToConsole("** ERROR from setRegister **");
			return null;
		}
		o.setValue(regval);
		if (binit)
			DrawRegister(o);

		// if (ConsolePanel != null && !regname.equals("USR"))
		// outToConsole ("setRegister: "+regname+" = "+regval);

		return o;
	}

	public GuiObj commitRegister(String regname, String regval) {
		GuiObj o = getObj(regname);
		if (o == null) {
			outToConsole("***********************");
			return null;
		}
		o.commitValue(regname, regval);
		return o;
	}

	public GuiObj resetRegister(String regname) {
		GuiObj o = getObj(regname);
		if (o == null) {
			outToConsole("***********************");
			return null;
		}
		o.resetValue();
		DrawRegister(o);
		return o;
	}

	public void displayMemoryBank(char[] address) {

		String S = null;
		try {
			String tmpS = new String(address);
			String tmpSS = new String(Global.MEMORY.get(address));
			S = new String(tmpS + "  " + tmpSS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MemoryBankPanel.textArea.append(S + "\n");

	}

	public void initPanel(GuiMain gMain) {
		// MemoryBankPanel = new GuiPanel(gPanelX,170,gPanelW,160,gDARKGREEN,
		// 1);
		// MemoryBankPanel.setLocation(gPanelX, 170);
		// MemoryBankPanel.setSize (gPanelW, 160);
		// MemoryBankPanel.setBackground(gDARKGREEN);

		// ConsolePanel = new GuiPanel(gPanelX,370,gPanelW,160, Color.DARK_GRAY,
		// 1);
		// ConsolePanel.setLocation(gPanelX, 370);
		// ConsolePanel.setSize (gPanelW, 160);
		// ConsolePanel.setBackground(Color.DARK_GRAY);
		ConsolePanel = new GuiPanel(gPanelX, 370, gPanelW, 360,
				Color.DARK_GRAY, 1);
		ConsolePanel.setLocation(gPanelX, 370);
		ConsolePanel.setSize(gPanelW, 360);
		ConsolePanel.setBackground(Color.DARK_GRAY);

		Color lightYellow = new Color(255, 255, 183);
		InputPanel = new GuiPanel(gPanelX, 370 + 20, gPanelW, 160, lightYellow,
				2);
		// MemoryBankPanel.textArea.setBackground(gDARKGREEN);
		ConsolePanel.textArea.setBackground(Color.DARK_GRAY);

		labStatus = new Label("Status: ");
		labStatus.setLocation(100, 700);
		labStatus.setBackground(Color.BLACK);
		labStatus.setForeground(Color.WHITE);

		// this.add(MemoryBankPanel);
		this.add(ConsolePanel);
		this.add(InputPanel);
		this.add(labStatus);
		this.setVisible(true);
		int x = (gScreenDim.width - gWinDim.width) / 2;
		int y = (gScreenDim.height - gWinDim.height) / 2;
		this.setLocation(x, y);
		this.setSize(gWinDim.width, gWinDim.height);
		this.setBackground(gBgColor);

		// f.setVisible(true);
		gThread = new GuiThread(this, "java", 50);
	}

	public void getLoader() {


		String[] S = { "1010010000010100", "1111010000000010",
				"0000100100000000", "0000010000010100", "0001100000000001",
				"0000100000010100", "0000010001010101", "0001110001000001",
				"0000100001010101", "0010110001000010", "0011010000111111",
				"0000000000000000", "0000000000000000", "0000000000000000",
				"0000000000000000", "0000000000000000", "0000000000000000",
				"0000000000000000", "0000000000010101" };

		for (int i = 2; i < S.length + 2; i++) {
			try {
				Global.MEMORY.set(Global.ALU.int2char(i),
						S[i - 2].toCharArray());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			Global.PC.set(Global.ALU.int2char(2));
			// System.out.println(Global.PC.get());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clearPreviousRect(Graphics g) {
		if (gRect != null) {
			int px = gRect.x + 3;
			int pw = gRect.w - 6;
			g.setColor(Color.black);
			if (gRect.name.equals("USR_CLEAR")) {
				px = gRect.x - 1;
				pw = gRect.w - 8;
			}
			g.drawRect(px, gRect.y + 1, pw, gRect.h - 2);
			gRect = null;
		}
	}

	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int px, pw;
		GuiRect r;
		Graphics g = root.getGraphics();
		strEvent = null;
		// strEvent = "Mouse Move: x="+x+", y="+y;

		int i = getRect(x, y);
		clearPreviousRect(g);

		if (i >= 0) {
			r = vrect.elementAt(i);
			gRect = r;
			px = r.x + 3;
			pw = r.w - 6;

			g.setColor(Color.red);
			if (r.name.equals("USR_CLEAR")) {
				px = r.x - 1;
				pw = r.w - 8;
			}
			g.drawRect(px, r.y + 1, pw, r.h - 2);
			strEvent = "****** Inside Rect " + r.name + ": x=" + x + ", y=" + y;
		}

		// DEBUG(strEvent);
	}

	/** Handle the key typed event from the text field. */
	public void keyTyped(KeyEvent e) {

	}

	/** Handle the key-pressed event from the text field. */
	public void keyPressed(KeyEvent e) {
		// gChar = e.getKeyChar();
		outToConsole("GGGGGGGG" + gChar);
	}

	/** Handle the key-released event from the text field. */
	public void keyReleased(KeyEvent e) {
		outToConsole("GGGGGGGG" + gChar);

	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int px, pw;
		GuiRect r;
		Graphics g = root.getGraphics();

		strEvent = "Mouse click: x=" + x + ", y=" + y;

		if (vrect != null) {
			int i = getRect(x, y);
			if (i >= 0) {
				clearPreviousRect(g);
				r = vrect.elementAt(i);
				gRect = r;
				px = r.x;
				pw = r.w;
				int idx;
				String str;
				if (r.name.equals("Power")) {

				}
				if (r.name.equals("Run")) {
					InputPanel.textField.setText("Input>");
					starttime = getCurrentTime();
					gTimerOn = true;
					gThread.gRun = true;
					outToConsole("Click RUN");
				}
				if (r.name.equals("Halt")) {
					gThread.gRun = false;
					gThread.gDebug = false;
					gThread.gStep = false;
					printExecutionTime();
					outToConsole("Click Halt");
				}
				if (r.name.equals("IPL")) {
					labStatus.setText("Status:");
					getLoader();
					outToConsole("Click IPL");
				}
				if (r.name.equals("Debug")) {
					gThread.gDebug = true;
				}
				if (r.name.equals("Step")) {
					gThread.gStep = true;
					outToConsole("Click CONT");
				}
				if (r.name.equals("Cont")) {
					gThread.gDebug = false;
					gThread.gStep = false;
					outToConsole("Click CONT");
				}
				if (r.name.equals("USR_CLEAR")) {
					gUSR = "0000000000000000";
					try {
						commitRegister("USR", gUSR);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					r.on = true;
				}
				if (r.name.contains("YES")) {
					str = r.name.substring(0, 3);
					try {
						setRegister(str, gUSR);
						commitRegister(str, gUSR);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// DEBUG
					// ("YES button: "+r.name+", str ="+str+", gUSR="+gUSR);
				}
				if (r.name.contains("NO")) {
					str = r.name.substring(0, 3);
					resetRegister(str);
					r.on = true;
					// DEBUG
					// ("NO button: "+r.name+", str ="+str+", gUSR="+gUSR);
				}

				if (r.id == -1) {
					if (r.on)
						g.drawImage(img[r.imgid], r.x, r.y, root);
					else
						g.drawImage(img[r.imgid + 1], r.x, r.y, root);
				}

				// user clicking on the user-define register
				if (r.name.contains("USR_") && !r.name.equals("USR_CLEAR")) {
					idx = Integer.parseInt(r.name.substring(4));
					if (r.on) {
						str = "0";
						g.drawImage(img[r.imgid], r.x, r.y, root);
					} else {
						str = "1";
						g.drawImage(img[r.imgid + 1], r.x, r.y, root);
					}
					gUSR = gUSR.substring(0, idx) + str
							+ gUSR.substring(idx + 1);
					r.on = !r.on;
					// DEBUG
					// ("USR LEDS: "+gUSR+",len ="+gUSR.length()+", idx ="+idx);

				} else
					g.drawImage(img[r.imgid], r.x, r.y, root);

				// r.on = !r.on;
				strEvent = "****** Inside MouseClick " + r.name + ": x=" + x
						+ ", y=" + y;
			}
		}
		// repaint();
		// DEBUG (strEvent);
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void DrawPanel(Graphics g, Color c) {
		int w = 28; // LED width
		int h = 28; // LED heigh
		int x = 50;
		int y = 140; // beginning of set of LEDs
		int dy = w + 5;

		Dimension d = getSize();
		int brdw = 10;
		int px = x, py = y;
		int textX, textY, textW, textT = 78;
		int powerX, powerY = 61;

		int imgwidth = 86;
		int imgheight = 49;
		h1fm = getFontMetrics(H1font);
		h2fm = getFontMetrics(H2font);
		h3fm = getFontMetrics(H3font);

		textW = h1fm.stringWidth(title);
		textX = (d.width - textW) / 2;
		textY = textT;
		powerX = textX + textW + 40;
		vrect.elementAt(0).x = powerX;

		// Draw headings
		g.setFont(H1font);
		g.setColor(Color.GREEN);
		g.drawString(title, textX, textY);
		g.setFont(H2font);
		textW = h2fm.stringWidth(Authors);
		textX = (d.width - textW) / 2;
		textY = h1fm.getHeight() + 62;
		g.drawString(Authors, textX, textY);
		int tx = 256, ty = 480 + 30, tw = -7;

		// draw outlines and labels
		g.setFont(H3font);
		g.setColor(gDARKBLUE);
		g.drawRoundRect(brdw, textY + brdw, d.width - 3 * brdw, d.height - 3
				* brdw - textY, 10, 10);

		// Draw Memory Bank label
		int textX2;
		ty = py + 10;
		textW = h3fm.stringWidth(MemoryBank);
		textX = gPanelX + (gPanelW - textW) / 2;
		textX2 = textX + textW;
		g.setColor(Color.WHITE);
		g.drawString(MemoryBank, textX, ty + 5);

		ty = py + 204;
		textW = h3fm.stringWidth(Console);
		textX = gPanelX + (gPanelW - textW) / 2;
		textX2 = textX + textW;
		g.setColor(Color.WHITE);
		g.drawString(Console, textX, ty + 5);

		ty = py + 402;
		textW = h3fm.stringWidth(Input);
		textX = gPanelX + (gPanelW - textW) / 2;
		textX2 = textX + textW;
		g.setColor(Color.WHITE);
		g.drawString(Input, textX, ty + 5);

		// Draw Internal Registers label
		ty = py + 430 + 43;
		textW = h3fm.stringWidth(InternalRegister);
		textX = (d.width - textW) / 2;
		textX2 = textX + textW;
		g.drawLine(3 * brdw, ty, textX - 3 * brdw, ty);
		g.drawLine(textX2 + 3 * brdw, ty, d.width - 4 * brdw, ty);
		g.setColor(Color.WHITE);
		g.drawString(InternalRegister, textX, ty + 5);

		// Draw buttons and registers
		DisplayButtons(g);
		ShowRegisters(g);
	}

	public void DisplayButtons(Graphics g) {
		int dw = 5, dy = 26, len;
		int size;
		int x, y, w, h;

		g.setFont(H2font);
		h2fm = getFontMetrics(H2font);
		g.setColor(Color.WHITE);

		// Draw buttons
		GuiRect r;
		for (int i = 0; i < vrect.size(); i++) {
			r = vrect.elementAt(i);
			if (r.id == -1) // for buttons
			{
				if (!r.on)
					g.drawImage(img[r.imgid], r.x, r.y, root);
				else
					g.drawImage(img[r.imgid + 1], r.x, r.y, root);
			} else // for "check" and "uncheck"
			if (r.name.substring(0, 4).equals("USR_")) // for buttons
			{
				if (!r.on)
					g.drawImage(img[r.imgid], r.x, r.y, root);
				else
					g.drawImage(img[r.imgid + 1], r.x, r.y, root);
			} else
				g.drawImage(img[r.imgid], r.x, r.y, root);
		}
	}

	public void DrawRegister(GuiObj o) {
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
		for (int j = 0; j < size; j++) {
			if (o.val.substring(j, j + 1).equals("0"))
				g.drawImage(img[o.imgOffIdx], x, y, this);
			else
				g.drawImage(img[o.imgOnIdx], x, y, this);
			imgIdx = o.imgOffIdx;
			if (o.val.substring(j, j + 1).equals("1"))
				imgIdx = o.imgOnIdx;
			if (o.name.equals("USR"))
				addButton(o.id, imgIdx, o.name + "_" + j, x, y, 34, 34);
			x += gw;
		}
	}

	public void DrawRegister(String regname) {
		GuiObj o = getObj(regname);
		DrawRegister(o);
	}

	public void ShowRegisters(Graphics g) {
		int dw = 5, dy = 26, len;
		int size;
		int x, y, w, h;

		g.setFont(H2font);
		h2fm = getFontMetrics(H2font);
		g.setColor(Color.WHITE);

		// Draw registers
		GuiObj o;
		for (int i = 0; i < vobj.size(); i++) {
			o = vobj.elementAt(i);
			size = o.val.length();
			w = o.w;
			h = o.h;
			x = o.x;
			y = o.y;

			if (o.dir.equals("V")) // common registers
			{
				len = h2fm.stringWidth(o.name);
				g.drawString(o.name, len, o.y + 27);
				x = o.x + len + 12;
				w = 28;

				DrawRegister(o);
				x = 16 * 33 + len + 65;
				if (o.name.equals("USR")) {
					if (getRect(o.name + "_CLEAR") == null)
						addButton(o.id, 24, o.name + "_CLEAR", x + 4, y, 84, 35);
				}
			}
			if (!o.name.equals("MFR") && !o.name.equals(" CC")
					&& !o.name.equals("USR") && o.dir.equals("V")) {
				if (getRect(o.name + "_YES") == null) {
					int tw = 42, ty = 35;
					addButton(o.id, 22, o.name + "_YES", x, y, tw, ty);
					addButton(o.id, 23, o.name + "_NO", x + 38, y, tw, ty);
				}
			}

			if (o.dir.equals("H")) // internal registers
			{
				int tx;
				dy = 10;
				w = 28;
				len = h2fm.stringWidth(o.name);
				size = o.val.length();
				tx = (size * (w + dw) - len) / 2;
				tx += x;
				g.drawString(o.name, tx, y + dy);
				DrawRegister(o);
			}
		}
	}

	public void ShowRegisters_OLD(Graphics g) {
		int dw = 5, dy = 26, len;
		int size;
		int x, y, w, h;

		g.setFont(H2font);
		h2fm = getFontMetrics(H2font);
		g.setColor(Color.WHITE);

		// Draw registers
		GuiObj o;
		for (int i = 0; i < vobj.size(); i++) {
			o = vobj.elementAt(i);
			size = o.val.length();
			w = o.w;
			h = o.h;
			x = o.x;
			y = o.y;

			if (o.dir.equals("V")) // common registers
			{
				len = h2fm.stringWidth(o.name);
				g.drawString(o.name, len, o.y + 27);
				x = o.x + len + 12;
				w = 28;

				boolean done = false;
				for (int j = 0; j < size; j++) {
					done = false;
					if (!done && o.name.equals("USR")
							&& o.val.substring(j, j + 1).equals("1")) {
						g.drawImage(img[19], x, y, this);
						addButton(o.id, 19, o.name + "_" + j, x, y, 34, 34);
						done = true;
					}
					if (!done && (o.name.equals("MFR") | o.name.equals(" CC"))
							&& o.val.substring(j, j + 1).equals("1")) {
						g.drawImage(img[16], x, y, this);
						done = true;
					}

					if (!done && o.val.substring(j, j + 1).equals("0")) {
						if (o.name.equals("USR")) {
							// g.drawImage(img[18], x, y, this);
							addButton(o.id, 18, o.name + "_" + j, x, y, 34, 34);
						} else
							g.drawImage(img[14], x, y, this);
						done = true;
					}

					if (!done) {
						if (o.name.equals("USR")
								&& o.val.substring(j, j + 1).equals("0")) {
							g.drawImage(img[18], x, y, this);
							addButton(o.id, 18, o.name + "_" + j, x, y, 34, 34);
							done = true;
						}

						else

						{
							g.drawImage(img[15], x, y, this);
							done = true;
						}
					}
					if (j == 5)
						x += 0;
					x += w + dw;
				}
				if (o.name.equals("USR")) {
					if (getRect(o.name + "_CLEAR") == null)
						addButton(o.id, 24, o.name + "_CLEAR", x + 4, y, 84, 35);
				}
			}
			if (!o.name.equals("MFR") && !o.name.equals(" CC")
					&& !o.name.equals("USR") && o.dir.equals("V")) {
				if (getRect(o.name + "_YES") == null) {
					int tw = 42, ty = 35;
					addButton(o.id, 22, o.name + "_YES", x, y, tw, ty);
					addButton(o.id, 23, o.name + "_NO", x + 38, y, tw, ty);
				}
			}

			if (o.dir.equals("H")) // internal registers
			{
				int tx;
				dy = 10;
				w = 28;
				len = h2fm.stringWidth(o.name);
				size = o.val.length();
				tx = (size * (w + dw) - len) / 2;
				tx += x;
				g.drawString(o.name, tx, y + dy);
				// y += dy+10;
				// boolean done = false;
				// for (int j=0; j<size; j++)
				// {
				// done = false;
				// if (!done && o.val.substring(j,j+1).equals("0"))
				// { g.drawImage(img[14], x, y, this); done =true; }
				// if (!done)
				// { g.drawImage(img[15], x, y, this); done = true; }
				// x += w+dw;
				// }
			}
		}
	}

	public GuiRect getRect(String name) {
		for (int i = 0; i < vrect.size(); i++) {
			if (vrect.elementAt(i).name.equalsIgnoreCase(name))
				return vrect.elementAt(i);
		}
		return null;
	}

	public int getRect(int x, int y) {
		GuiRect r;
		Boolean found = false;
		for (int i = 0; !found && i < vrect.size(); i++) {
			r = vrect.elementAt(i);
			if (x > r.x && y > r.y && x < r.x + r.w && y < r.y + r.h)
				return i;
		}
		return -1;
	}

	public GuiObj getObj(String name) {
		for (int i = 0; i < vobj.size(); i++) {
			if (vobj.elementAt(i).name.equalsIgnoreCase(name))
				return vobj.elementAt(i);
		}
		return null;
	}

	public int getObj(int x, int y) {
		GuiObj o;
		for (int i = 0; i < vobj.size(); i++) {
			o = vobj.elementAt(i);
			if (x > o.x && y > o.y && x < o.x + o.w && y < o.y + o.h)
				return i;
		}
		return -1;
	}

	public void addButton(int id, int imgid, String name, int x, int y, int w,
			int h) {
		GuiRect r;

		r = getRect(name);
		if (r == null) {
			r = new GuiRect();
			r.name = name;
			r.on = false;
			r.id = id;
			r.imgid = imgid;
			r.x = x;
			r.y = y;
			r.w = w;
			r.h = h;
			vrect.add(r);
		}
	}

	public void addReg(int id, String name, String val, int x, int y, int w,
			int h, Color c, String dir) {
		GuiObj o;

		o = getObj(name);
		if (o == null) {
			o = new GuiObj();
			x += 2;
			y -= 2;
			w = 40;
			h = 40;
			o.name = name;
			o.on = false;
			o.id = id;
			o.x = x;
			o.y = y;
			o.w = w;
			o.h = h;
			o.val = val;
			o.oldval = val;
			o.c = c;
			o.dir = dir;
			o.size = o.val.length();
			if (c.equals(Color.GREEN)) {
				o.imgOnIdx = 15;
				o.imgOffIdx = 14;
			}
			if (c.equals(Color.RED)) {
				o.imgOnIdx = 16;
				o.imgOffIdx = 14;
			}
			if (name.equals("USR")) {
				o.imgOnIdx = 19;
				o.imgOffIdx = 18;
			}
			vobj.add(o);
		}
	}

	public void initRegisterValues() {
		char[] values;
		String str;

		values = Global.PC.get();
		str = new String(values);
		setRegister(" PC", str);
		values = Global.MAR.get();
		str = new String(values);
		setRegister("MAR", str);
		values = Global.MBR.get();
		str = new String(values);
		setRegister("MBR", str);
		values = Global.R[0].get();
		str = new String(values);
		setRegister(" R0", str);
		values = Global.R[1].get();
		str = new String(values);
		setRegister(" R1", str);
		values = Global.R[2].get();
		str = new String(values);
		setRegister(" R2", str);
		values = Global.R[3].get();
		str = new String(values);
		setRegister(" R3", str);
		values = Global.DLN.get();
		str = new String(values);
		setRegister("DLN", str);
		values = Global.IR.get();
		str = new String(values);
		setRegister(" IR", str);
		values = Global.X0.get();
		str = new String(values);
		setRegister(" X0", str);
		values = Global.MSR.get();
		str = new String(values);
		setRegister("MSR", str);
		values = Global.MFR.get();
		str = new String(values);
		setRegister("MFR", str);
		values = Global.CC.get();
		str = new String(values);
		setRegister(" CC", str);
	}

	public void refresh() {

		// getRegisterValues ();
		// System.out.print("hereeee");
		// Graphics g = root.getGraphics();
		// paint (g);
	}

	public void paint(Graphics g) {
		DrawPanel(g, Color.BLACK);
		DisplayButtons(g);
		ShowRegisters(g);
		// MemoryBankPanel.setLocation(gPanelX, 161);
		// MemoryBankPanel.setSize (gPanelW, 160);
		// ConsolePanel.setLocation(gPanelX, 354);
		// ConsolePanel.setSize (gPanelW, 160);
		ConsolePanel.setLocation(gPanelX, 161);
		ConsolePanel.setSize(gPanelW, 360);
		InputPanel.setLocation(gPanelX, 552);
		InputPanel.setSize(gPanelW, 30);
		labStatus.setLocation(100, 720);
		labStatus.setSize(400, 15);
		labStatus.setText("number of paint: " + gNumPaint++);
	}

	public void enableKeyboard(Boolean b) {
		InputPanel.textField.setEnabled(b);
	}

	public void inFromKeyboard(char[] cs) {
		String s = new String(cs);

		char i = GuiPanel.textCh;

		char[] instr = new char[16];
		try {
			instr = toASCII(i);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (s.contains("00")) {
			try {
				Global.R[0].set(instr);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (s.contains("01")) {

			try {
				Out("ttttttttttt\n");
				setRegister(" R1", "0000000011111111");
				// Global.R[1].set(instr);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (s.contains("10")) {
			try {
				Global.R[2].set(instr);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (s.contains("11")) {
			try {
				Global.R[3].set(instr);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private char[] toASCII(char i) throws Exception {
		// TODO Auto-generated method stub
		return Global.ALU.int2char((int) i);
	}
}
