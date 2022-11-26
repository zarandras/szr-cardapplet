package szr.gui;

import java.net.*;
import java.awt.*;
import java.awt.event.*;
import szr.card.*;
import szr.maincli.*;
/**
 * A kliens kártya szín és jelzés piktogram osztálya
 * Creation date: (2002.01.22. 16:22:43)
 */
public class SmallImgComponent extends Component {
	private static java.util.ResourceBundle resWidgetTexts = java.util.ResourceBundle.getBundle("szr.shared.WidgetTexts");  //$NON-NLS-1$
	private java.awt.Image szinImage = null;
	private java.awt.Image jelzesImage = null;
	private boolean szinImgAborted = false;
	private boolean jelzesImgAborted = false;
	private OsSzin szinObj = null;
	private szr.card.OsJelzes jelzesObj = null;
	private CardApplet parentApplet = null;
	private szr.maincli.InfoSystem infoSystem = null;
	private boolean resolveAllowed = false;		// a 'Mivé válna?' menüpont enabled-e?
public SmallImgComponent(CardApplet iParentApplet) {
	super();

	parentApplet = iParentApplet;
	
	szinImage = null;
	jelzesImage = null;
	szinImgAborted = false;
	jelzesImgAborted = false;
	addMouseListener(new SmallImgMouseAdapter(this));
	setCursor(new Cursor(Cursor.HAND_CURSOR));
}
public void clearJelzes() {
	jelzesImage = null;
	jelzesImgAborted = false;
	jelzesObj = null;
	repaint ();
}
public void clearSzin() {
	szinImage = null;
	szinImgAborted = false;
	szinObj = null;
	repaint ();
}
public szr.maincli.InfoSystem getInfoSystem() {
	return infoSystem;
}
public java.awt.Image getJelzesImage() {
	return jelzesImage;
}
public szr.card.OsJelzes getJelzesObj() {
	return jelzesObj;
}
public java.awt.Image getSzinImage() {
	return szinImage;
}
public OsSzin getSzinObj() {
	return szinObj;
}
public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
	if ((infoflags & (ERROR | ABORT)) != 0) {
		if (img == szinImage)
			szinImgAborted = true;
		else
			jelzesImgAborted = true;
		repaint ();
		return false;
	} else if ((infoflags & ALLBITS) != 0) {
		repaint ();
		return false;
	} else {
		return true;
	}
}
public boolean isResolveAllowed() {
	return resolveAllowed;
}
public void paint(Graphics g) {
	if (szinImage != null) {
		if (!szinImgAborted)
			g.drawImage (szinImage, 1, 1, Color.cyan, this);
		else {
			g.drawString (resWidgetTexts.getString("Kephiba1"), 1, 10); //$NON-NLS-1$
			g.drawString (resWidgetTexts.getString("Kephiba2"), 1, 25); //$NON-NLS-1$
		}
	}
	if (jelzesImage != null) {
		if (!jelzesImgAborted)
			g.drawImage (jelzesImage, 1, 1, this);
		else {
			g.drawString (resWidgetTexts.getString("Kephiba1"), 1, 10); //$NON-NLS-1$
			g.drawString (resWidgetTexts.getString("Kephiba2"), 1, 25); //$NON-NLS-1$
		}
	}
}
public void setInfoSystem(szr.maincli.InfoSystem newInfoSystem) {
	infoSystem = newInfoSystem;
}
public void setJelzes(java.awt.Image newJelzesImage) {
	jelzesImage = newJelzesImage;
	jelzesImgAborted = false;
	jelzesObj = null;
	repaint ();
}
public void setJelzes(String newJelzesPath) {
	try {
		jelzesImage = getToolkit ().getImage (new URL (parentApplet.getBaseURL() + newJelzesPath));
		jelzesImgAborted = false;
	} catch (MalformedURLException e) {
		jelzesImage = null;
		jelzesImgAborted = true;
	}
	jelzesObj = null;
	repaint ();
}
public void setJelzes(szr.card.OsJelzes newJelzes) {
	try {
		setJelzes(newJelzes.getSmallImgPath());
		jelzesObj = newJelzes;
	} catch (NullPointerException e) {
		clearJelzes();
	}
}
public void setResolveAllowed(boolean newResolveAllowed) {
	resolveAllowed = newResolveAllowed;
}
public void setSzin(java.awt.Image newSzinImage) {
	szinImage = newSzinImage;
	szinImgAborted = false;
	szinObj = null;
	repaint ();
}
public void setSzin(String newSzinPath) {
	try {
		szinImage = getToolkit ().getImage (new URL (parentApplet.getBaseURL() + newSzinPath));
		szinImgAborted = false;
	} catch (MalformedURLException e) {
		szinImage = null;
		szinImgAborted = true;
	}
	szinObj = null;
	repaint ();
}
public void setSzin(szr.card.OsSzin newSzin) {
	try {
		setSzin (newSzin.getSmallImgPath ());
		szinObj = newSzin;
	} catch (NullPointerException e) {
		clearSzin();
	}
}
public void showHelpPopupMenu(int x, int y) {
	try {
		synchronized (infoSystem.getParentController()) {
			infoSystem.setHelpJelzes(jelzesObj);
			infoSystem.setHelpSzin(szinObj);
			infoSystem.setCurrentSmallImg((resolveAllowed) ? this : null);
			infoSystem.getCardHelpPopupMenu().show(this, x, y);
		}
	} catch (NullPointerException e) {}
}
}
