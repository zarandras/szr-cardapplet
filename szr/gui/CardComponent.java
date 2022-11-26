package szr.gui;

import szr.msg.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import szr.card.*;
import szr.maincli.ClientController;
/**
 * Kártyalap mint GUI komponens
 * Creation date: (2002.01.22. 16:22:43)
 */
public class CardComponent extends Component {
	private static java.util.ResourceBundle resWidgetTexts = java.util.ResourceBundle.getBundle("szr.shared.WidgetTexts");  //$NON-NLS-1$
	private java.awt.Image cardImage1 = null;	// alsó kártyakép
	private java.awt.Image cardImage2 = null;	// felsõ kártyakép, nem kötelezõ hogy legyen
	private boolean img1Aborted = false;	// hiba történt-e a cardImage1 betöltésekor?
	private boolean img2Aborted = false;	// hiba történt-e a cardImage2 betöltésekor?
	private boolean selected = false;	// kijelölt-e a kártya?
	private Card cardObj = null;	// nem kötelezõ hogy legyen
	private java.awt.Image selImage = null;	// a kijelölés képe
	private boolean selImgAborted = false;
	private final static java.lang.String BACK_IMAGE_PATH = "cardimg/c.gif";//$NON-NLS-1$
	private final static java.lang.String BACK_ROT_IMAGE_PATH = "cardimg/cv.gif";//$NON-NLS-1$
	private final static java.lang.String SEL_IMAGE_PATH = "cardimg/sel.gif";//$NON-NLS-1$
	private CardApplet parentApplet = null;
public CardComponent(CardApplet iParentApplet) {
	super();

	parentApplet = iParentApplet;
	
	cardImage1 = null;
	img1Aborted = false;
	cardImage2 = null;
	img2Aborted = false;
//	initSelImage();	<- on-demand módon hozza létre, mert még nem lehet inicializálni
}
public void clearCard() {
	cardImage1 = null;
	img1Aborted = false;
	cardImage2 = null;
	img2Aborted = false;
	cardObj = null;
	setSelected(false);
}
public java.awt.Image getCardImage1() {
	return cardImage1;
}
public java.awt.Image getCardImage2() {
	return cardImage2;
}
public Card getCardObj() {
	return cardObj;
}
public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
	if ((infoflags & (ERROR | ABORT)) != 0) {
		if (img == cardImage1)
			img1Aborted = true;
		else if (img == cardImage2)
			img2Aborted = true;
		else /* selImage */
			selImgAborted = true;
		repaint ();
		return false;
	} else if ((infoflags & ALLBITS) != 0) {
		repaint ();
		return false;
	} else {
		return true;
	}
}
public void initSelImage() {
	try {
		selImage = getToolkit ()/*parentApplet*/.getImage (new URL (parentApplet.getBaseURL() + SEL_IMAGE_PATH));
		selImgAborted = false;
	} catch (MalformedURLException e) {
		selImage = null;
		selImgAborted = true;
	}
}
public boolean isSelected() {
	return selected;
}
public void paint(Graphics g) {
	if (cardImage1 != null) {
		if (!img1Aborted)
			g.drawImage (cardImage1, 0, 0, this);
		else {
			g.drawString (resWidgetTexts.getString("Kephiba1"), 10, 25); //$NON-NLS-1$
			g.drawString (resWidgetTexts.getString("Kephiba2"), 10, 40); //$NON-NLS-1$
		}
	}

	if (cardImage2 != null) {
		if (!img2Aborted)
			g.drawImage (cardImage2, 0, 0, this);
		else {
			g.drawString (resWidgetTexts.getString("Kephiba1"), 10, 25); //$NON-NLS-1$
			g.drawString (resWidgetTexts.getString("Kephiba2"), 10, 40); //$NON-NLS-1$
		}
	}
	if (selected) {
		if (!selImgAborted && selImage != null)
			g.drawImage (selImage, 0, 0, this);
		else
			g.drawString (resWidgetTexts.getString("CardSelected"), 10, 20); //$NON-NLS-1$
	}
	if (img1Aborted || img2Aborted) {
			try {
				g.drawString (cardObj.getSzin().toString(), 10, 55);
			} catch (NullPointerException e) {}
			try {
				g.drawString (cardObj.getJelzes().toString(), 10, 65);
			} catch (NullPointerException e) {}
	}
}
/**
 * Hátlapra állítás
 */
public void setBackCard() {
	setCard (BACK_IMAGE_PATH);
	cardObj = null;
}
/**
 * Vízszintes hátlap
 */
public void setBackRotCard() {
	setCard (BACK_ROT_IMAGE_PATH);
	cardObj = null;
}
public void setCard(java.awt.Image newCardImage) {
	cardImage1 = newCardImage;
	img1Aborted = false;
	cardImage1 = null;
	img2Aborted = false;
	cardObj = null;
	setSelected(false);
}
public void setCard(java.awt.Image newCardImage1, java.awt.Image newCardImage2) {
	cardImage1 = newCardImage1;
	img1Aborted = false;
	cardImage1 = newCardImage2;
	img2Aborted = false;
	cardObj = null;
	setSelected(false);
}
public void setCard(String newCardPath) {
	try {
		cardImage1 = getToolkit ().getImage (new URL (parentApplet.getBaseURL() + newCardPath));
		img1Aborted = false;
	} catch (MalformedURLException e) {
		cardImage1 = null;
		img1Aborted = true;
	}
	cardImage2 = null;
	img2Aborted = false;
	cardObj = null;
	setSelected(false);
}
public void setCard(String newCardPath1, String newCardPath2) {
	try {
		cardImage1 = getToolkit ().getImage (new URL (parentApplet.getBaseURL() + newCardPath1));
		img1Aborted = false;
	} catch (MalformedURLException e) {
		cardImage1 = null;
		img1Aborted = true;
	}

	try {
		cardImage2 = getToolkit ().getImage (new URL (parentApplet.getBaseURL() + newCardPath2));
		img2Aborted = false;
	} catch (MalformedURLException e) {
		cardImage2 = null;
		img2Aborted = true;
	}
	cardObj = null;
	setSelected(false);
}
public void setCard(szr.card.Card newCard) {
	try {
		String PathStr = newCard.getImg2Path ();
		if (PathStr == null) {
			setCard (newCard.getImg1Path ());
		} else {
			setCard (newCard.getImg1Path (), PathStr);
		}
		cardObj = newCard;
	} catch (NullPointerException e) {
		clearCard();
	}
}
/**
 * Kijelölés
 */
public void setSelected(boolean newSelected) {
	if (selImage == null && !selImgAborted) {
		initSelImage();
	}
	selected = newSelected;
	repaint();
}
}
