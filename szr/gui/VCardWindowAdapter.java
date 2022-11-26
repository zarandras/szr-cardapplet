package szr.gui;

import java.awt.event.*;
import java.awt.*;
/**
 * K�l�n ablakban kinyitott VCardApplet bez�r�s�t vez�rl� adapter oszt�ly
 * Creation date: (2002.03.27. 10:11:41)
 */
public class VCardWindowAdapter extends WindowAdapter {
	private final Frame frame;
	private final VCardApplet applet;
public VCardWindowAdapter(Frame iFrame, VCardApplet iApplet) {
	super();
	frame = iFrame;
	applet = iApplet;
}
public void windowClosing(java.awt.event.WindowEvent e) {
	applet.destroy();
	frame.dispose();
}
}
