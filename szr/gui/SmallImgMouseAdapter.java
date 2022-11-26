package szr.gui;

import java.awt.event.*;
import java.awt.*;
import szr.maincli.*;
/**
 * A kliens SmallImgComponent (szín / jelzés piktogram) osztályának egéresemény-figyelõje
 * Creation date: (2002.02.13. 10:35:59)
 */
public class SmallImgMouseAdapter extends MouseAdapter {
	private final SmallImgComponent parentSIC;
public SmallImgMouseAdapter(SmallImgComponent iParentSIC) {
	super();
	parentSIC = iParentSIC;
}
public void mousePressed(MouseEvent e) {
	if (e.getSource() == parentSIC) {
		parentSIC.showHelpPopupMenu(e.getX(), e.getY());
	}
}
	
}
