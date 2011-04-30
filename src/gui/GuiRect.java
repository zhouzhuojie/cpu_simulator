/*****************************************************************************
 * FILE    : GuiRect.java                                Rev. 1.0 05/04/2011 *
 *                                                                           *
 * AUTHOR  : Wo Chang                                                        *
 *                                                                           *
 * DATE    : May 4, 2011                                                     *
 *                                                                           *
 * PROJECT : GWU CS6461 Computer Architecture Class                          *
 *                                                                           *
 * This file contains the GUI LED structure for register.                    *
 *                                                                           *
 * DEPENDS : 1. arc_project.Global: global variables from the simulator      *
 *           2. Java(TM) SE Runtime Environment (build 1.6.0_15-b03)         *
 *****************************************************************************/
package gui;

/*****************************************************************************
 * CLASS GuiRect                                                             *
 *                                                                           *
 * GUIRect provides a LED structure for register.                            * 
 *****************************************************************************/
public class GuiRect {
	public String name;
	public int id, imgid, x=-1, y=-1, w=-1, h=-1, type=-1;
	Boolean on = false; 
	int imgOnIdx, imgOffIdx;
	GuiObj o; 
}
