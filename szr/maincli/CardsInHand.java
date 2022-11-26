package szr.maincli;

import szr.msg.*;
import szr.gui.CardComponent;
import szr.gui.SmallImgComponent;
import java.awt.Scrollbar;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import szr.card.*;
/**
 * A kliens saját lapokat kezelõ osztálya
 * Creation date: (2002.01.27. 14:46:01)
 */
public class CardsInHand implements AdjustmentListener, ActionListener {
	private static java.util.ResourceBundle resWidgetTexts = java.util.ResourceBundle.getBundle("szr.shared.WidgetTexts");  //$NON-NLS-1$
	private ClientController parentController = null;
	private final java.awt.Button lerakButton;
	private final java.awt.Button passzButton;
	private final Vector cardVect;	// összes kártya
	private final Vector cardServerIdxVect;	// leképezés: a kliensbeli kártyaindexekhez tartozó szerverbeli kártyaindexek
	private final CardComponent[] cardSlots;	// képernyõn látható kártyák
	private final java.awt.Scrollbar slotScrollbar;
	private final szr.gui.SmallImgComponent smallImg;
	private int selectedIdx = -1;	// kijelölt kártya indexe
	private int dragSourceIdx = -1;	// draggelt kártya indexe
	private HandMouseAdapter myMouseAdapter = null;	// create on demand, mert a konstruktorhíváskor még nem hozható létre
	private final static java.lang.String SMILEYIMG_NYERT = "won.gif";//$NON-NLS-1$
	private final static java.lang.String SMILEYIMG_VESZTETT = "lost.gif";//$NON-NLS-1$
	private final static java.lang.String SMILEYIMG_EGYEDULMARADT = "draw.gif";//$NON-NLS-1$
	private int smileyPState = -1;
public CardsInHand(CardComponent[] iCardSlots, Scrollbar iSlotScrollbar, SmallImgComponent iSmallImg,
					Button iLerakButton, Button iPasszButton) {
	super();

	cardSlots = iCardSlots;
	slotScrollbar = iSlotScrollbar;
	smallImg = iSmallImg;
	lerakButton = iLerakButton;
	passzButton = iPasszButton;
	
	smallImg.setResolveAllowed(true);
	slotScrollbar.setEnabled (false);
	slotScrollbar.addAdjustmentListener (this);
	smallImg.clearSzin ();
	smallImg.clearJelzes ();
	lerakButton.addActionListener (this);
	passzButton.addActionListener (this);
	disableActionButtons();

	cardVect = new Vector ();
	cardServerIdxVect = new Vector ();
}
public void actionPerformed(ActionEvent e) {
  synchronized (parentController) {
	Object srcWidget = e.getSource ();
	if (srcWidget == lerakButton && lerakButton.isEnabled()) {
		parentController.lerakPressed();
	} else if (srcWidget == passzButton && passzButton.isEnabled()) {
		parentController.passzPressed();
	}
  }
}
public void addCard(Card cj) {
	// create myMouseAdapter on-demand
	if (myMouseAdapter == null) {
		myMouseAdapter = new HandMouseAdapter(this);
		for (int i = 0; i < cardSlots.length; ++i) {
			cardSlots[i].clearCard ();
			cardSlots[i].addMouseListener(myMouseAdapter);
		}
	}
	
	cancelDrag();
	slotScrollbar.setValues(0, cardSlots.length, 0, cardVect.size());
	try {
		Thread.sleep(250);
	} catch (InterruptedException e) {}
	slotScrollbar.setEnabled(true);
	cardVect.insertElementAt(cj, 0);
	cardServerIdxVect.insertElementAt(new Integer(cardServerIdxVect.size()), 0);
	slotScrollbar.setValues(0, cardSlots.length, 0, cardVect.size());
	updateSlots();
	setSelectedIdx(0);
}
public void adjustmentValueChanged(AdjustmentEvent e) {
  synchronized (parentController) {
	if (e.getSource () == slotScrollbar && slotScrollbar.isEnabled()) {
		updateSlots();
	}
  }
}
private void cancelDrag() {
	dragSourceIdx = -1;
	setDragCursor(false);
}
private void cardDragged(int sourceIdx, int destIdx) {
	if (sourceIdx != destIdx && sourceIdx >= 0 && sourceIdx < cardVect.size() && destIdx >= 0 && destIdx < cardVect.size()) {
		Card movedCard = (Card)cardVect.elementAt(sourceIdx);
		Integer movedServerIdx = (Integer)cardServerIdxVect.elementAt(sourceIdx);
		if (sourceIdx < destIdx) {
			for (int i = sourceIdx; i < destIdx; ++i) {
				cardVect.setElementAt(cardVect.elementAt(i+1), i);
				cardServerIdxVect.setElementAt(cardServerIdxVect.elementAt(i+1), i);
			}
		} else {
			for (int i = sourceIdx; i > destIdx; --i) {
				cardVect.setElementAt(cardVect.elementAt(i-1), i);
				cardServerIdxVect.setElementAt(cardServerIdxVect.elementAt(i-1), i);
			}
		}
		cardVect.setElementAt(movedCard, destIdx);
		cardServerIdxVect.setElementAt(movedServerIdx, destIdx);
		updateSlots();
		setSelectedIdx(destIdx);
	}
}
/**
 * Smiley kijelzésének megszüntetése
 */
public void clearSmileyPState() {
	setSmileyPState(-1);
}
public void disableActionButtons() {
	passzButton.setEnabled(false);
	lerakButton.setEnabled(false);
}
private void displaySmiley() {
	switch (smileyPState) {
		case ClientController.PS_NYERT:
			smallImg.setSzin((new AtlatszoSzin()).getSmallImgPath());
			smallImg.setJelzes(Card.getSmallImgRootPath () + SMILEYIMG_NYERT);
			break;
	
		case ClientController.PS_VESZTETT:
			smallImg.setSzin((new SzinesSzin(SzinesSzin.Fmask)).getSmallImgPath());
			smallImg.setJelzes(Card.getSmallImgRootPath () + SMILEYIMG_VESZTETT);
			break;
	
		case ClientController.PS_EGYEDULMARADT:
			smallImg.setSzin((new Lila()).getSmallImgPath());
			smallImg.setJelzes(Card.getSmallImgRootPath () + SMILEYIMG_EGYEDULMARADT);
			break;
	
		default:
			smallImg.clearSzin();
			smallImg.clearJelzes();
			break;
	}
}
/**
 * CardComponent (slot) indexének keresése
 */
private int findSlot(CardComponent cc) {
	if (cc == null)
		return -1;

	int i = cardSlots.length - 1;
	while (i >= 0 && cardSlots[i] != cc) {
		i--;
	}
	return i;
}
/**
 * Kártyalekérés szerverbeli index alapján
 */
public Card getCard(int serverIdx) {
	try {
		return (Card)cardVect.elementAt(serverToClientIdx(serverIdx));
	} catch (ArrayIndexOutOfBoundsException e) {
		return null;
	}
}
/**
 * Kártyaszám
 */
public int getCardCount() {
	return cardVect.size();
}
/**
 * slot (CardComponent) index kliens kártyaindexszé konvertálása
 */
private int getCardVectIdx(int slotIdx) {
	return slotIdx + slotScrollbar.getValue();
}
private ClientController getParentController() {
	return parentController;
}
/**
 * a kiválasztott kártya szerverbeli indexének lekérése
 */
public int getSelectedCardServerIdx() {
	if (selectedIdx < 0)
		return -1;
	else 
		return ((Integer)cardServerIdxVect.elementAt(selectedIdx)).intValue();
}
/**
 * kliensbeli kártyaindex CardComponent (slot) indexszé alakítása (<0 vagy >7: a kártya nincs kijelezve a képernyõn)
 */
private int getSlotIdx(int cardIdx) {
	return cardIdx - slotScrollbar.getValue();
}
public void mouseClicked(MouseEvent e) {
  synchronized (parentController) {
	int cardVectIdx = getCardVectIdx(findSlot((CardComponent)e.getSource()));
	if (cardVectIdx >= 0 && cardVectIdx < cardVect.size()) {
		if (dragSourceIdx < 0) {
			setSelectedIdx(cardVectIdx);
			if ((e.getModifiers() & (MouseEvent.BUTTON2_MASK | MouseEvent.BUTTON3_MASK)) != 0) {
				dragSourceIdx = cardVectIdx;
				setDragCursor(true);
			} else if (e.getClickCount() > 1) {
				if (lerakButton.isEnabled())
					parentController.lerakPressed();
			}
		} else {
			cardDragged(dragSourceIdx, cardVectIdx);
			dragSourceIdx = -1;
			setDragCursor(false);
		}
	} else {
		if (dragSourceIdx < 0) {
			setSelectedIdx(-1);
		} else {
			cardDragged(dragSourceIdx, cardVect.size() - 1);
			dragSourceIdx = -1;
			setDragCursor(false);
		}
	}
  }
}
public void removeAllCards() {
	cancelDrag();
	setSelectedIdx(-1);
	try {
		for (int i = 0; i < cardSlots.length; ++i)
			if (cardSlots[i].getCardImage1() != null)
				cardSlots[i].setBackCard();
		Thread.sleep(250);
	} catch (InterruptedException e) {}

	cardVect.removeAllElements();
	cardServerIdxVect.removeAllElements();
	slotScrollbar.setValues(0, cardSlots.length, 0, 0);
	updateSlots();
}
/**
 * kártyaeltávolítás szerverbeli index alapján
 */
public void removeCard(int serverIdx) {
  int clientIdx = serverToClientIdx(serverIdx);
  int slotIdx = getSlotIdx(clientIdx);
  cancelDrag();
  if (clientIdx < cardVect.size()) {
	if (slotIdx < 0) {
		slotScrollbar.setValue(clientIdx);
		slotIdx = getSlotIdx(clientIdx); // 0
		updateSlots();
	} else if (slotIdx > cardSlots.length) {
		slotScrollbar.setValue(clientIdx - cardSlots.length + 1);
		slotIdx = getSlotIdx(clientIdx); // cardSlots.length - 1
		updateSlots();
	}
	
	// hátlap kijelzése egy pillanatra
	try {
		Thread.sleep(250);
		cardSlots[slotIdx].setBackCard();
		Thread.sleep(250);
	} catch (InterruptedException e) {}
	
	setSelectedIdx(-1);
	int scrollPos = slotScrollbar.getValue();
	
	cardVect.removeElementAt(clientIdx);
	cardServerIdxVect.removeElementAt(clientIdx);
	for (int i = 0; i < cardServerIdxVect.size(); ++i) {
		Integer j = (Integer)cardServerIdxVect.elementAt(i);
		if (j.intValue() > serverIdx)
			cardServerIdxVect.setElementAt(new Integer(j.intValue() - 1), i);
	}
	
	slotScrollbar.setValues(scrollPos, cardSlots.length, 0, cardVect.size());
	updateSlots();
	
  } else {
	  Logger.log(new ErrStrLog(szr.shared.SubstituteStr.doIt("CardsInHand error: removing card #(client)%n", "%n", Integer.toString(clientIdx))), parentController.getGameName(), parentController.getPlayerName());//$NON-NLS-2$//$NON-NLS-1$
  }
}
/**
 * kártya lecserélése szerver index alapján
 */
public void replaceCard(Card c, int serverIdx) {
  int clientIdx = serverToClientIdx(serverIdx);
  cancelDrag();
  if (clientIdx < cardVect.size()) {
	cardVect.setElementAt(c, clientIdx);
	updateSlots();
	
	setSelectedIdx(clientIdx);
  } else {
	  Logger.log(new ErrStrLog(szr.shared.SubstituteStr.doIt("CardsInHand error: replacing card #(client)%n", "%n", Integer.toString(clientIdx))), parentController.getGameName(), parentController.getPlayerName());//$NON-NLS-2$//$NON-NLS-1$
  }
}
/**
 * kártyaindex konvertálása (serverIdx keresése a cardServerIdxVect-ben)
 */
public int serverToClientIdx(int serverIdx) {
	int i = 0;
	while (i < cardServerIdxVect.size() && ((Integer)cardServerIdxVect.elementAt(i)).intValue() != serverIdx) {
		i++;
	}
	if (i >= cardServerIdxVect.size())
		Logger.log(new ErrStrLog(szr.shared.SubstituteStr.doIt("CardsInHand error: translating card index #(server)%n to client card index", "%n", Integer.toString(serverIdx))), parentController.getGameName(), parentController.getPlayerName());//$NON-NLS-2$//$NON-NLS-1$
	return i;
}
/**
 * Akciógombok állapotának beállítása játékosállapot-kód alapján
 */
public void setButtonPState(int playState) {
	cancelDrag();
	switch (playState) {
		case ClientController.PS_NOTJOINED:
		case ClientController.PS_DISCONNECTED:
		case ClientController.PS_ACTIVE_VETOBLOCKED:
		case ClientController.PS_VESZTETT:
		case ClientController.PS_NYERT:
		case ClientController.PS_EGYEDULMARADT:
			passzButton.setLabel(resWidgetTexts.getString("PasszButton_label")); //$NON-NLS-1$
			lerakButton.setLabel(resWidgetTexts.getString("LerakButton_label")); //$NON-NLS-1$
			passzButton.setEnabled(false);
			lerakButton.setEnabled(false);
			break;
			
		case ClientController.PS_JOINED:
			passzButton.setLabel(resWidgetTexts.getString("KezdButton_label")); //$NON-NLS-1$
			lerakButton.setLabel(resWidgetTexts.getString("LerakButton_label")); //$NON-NLS-1$
			passzButton.setEnabled(false);
			lerakButton.setEnabled(false);
			break;
			
		case ClientController.PS_JOINED_FIRST:
			passzButton.setLabel(resWidgetTexts.getString("KezdButton_label")); //$NON-NLS-1$
			lerakButton.setLabel(resWidgetTexts.getString("LerakButton_label")); //$NON-NLS-1$
			passzButton.setEnabled(true);
			lerakButton.setEnabled(false);
			break;
			
		case ClientController.PS_ACTIVE:
		case ClientController.PS_ACTIVE_EXS:
			passzButton.setLabel(resWidgetTexts.getString("PasszButton_label")); //$NON-NLS-1$
			lerakButton.setLabel(resWidgetTexts.getString("LerakButton_label")); //$NON-NLS-1$
			passzButton.setEnabled(true);
			lerakButton.setEnabled(true);
			break;

		case ClientController.PS_ACTIVE_UT:
			passzButton.setLabel(resWidgetTexts.getString("PasszButton_label")); //$NON-NLS-1$
			lerakButton.setLabel(resWidgetTexts.getString("HalmozButton_label")); //$NON-NLS-1$
			passzButton.setEnabled(true);
			lerakButton.setEnabled(true);
			break;
		
		case ClientController.PS_ACTIVE_SOROZHAT:
		case ClientController.PS_ACTIVE_EXS_SOROZHAT:
			passzButton.setLabel(resWidgetTexts.getString("BefejezButton_label")); //$NON-NLS-1$
			lerakButton.setLabel(resWidgetTexts.getString("SorozButton_label")); //$NON-NLS-1$
			passzButton.setEnabled(true);
			lerakButton.setEnabled(true);
			break;

		case ClientController.PS_ACTIVE_LILA:
			passzButton.setLabel(resWidgetTexts.getString("BefejezButton_label")); //$NON-NLS-1$
			lerakButton.setLabel(resWidgetTexts.getString("SorozButton_label")); //$NON-NLS-1$
			passzButton.setEnabled(true);
			lerakButton.setEnabled(false);
			break;

		case ClientController.PS_ACTIVE_KEZDOLAP:
			passzButton.setLabel(resWidgetTexts.getString("PasszButton_label")); //$NON-NLS-1$
			lerakButton.setLabel(resWidgetTexts.getString("LerakButton_label")); //$NON-NLS-1$
			passzButton.setEnabled(true);
			lerakButton.setEnabled(false);
			break;

		case ClientController.PS_INACTIVE:
			passzButton.setLabel(resWidgetTexts.getString("PasszButton_label")); //$NON-NLS-1$
			lerakButton.setLabel(resWidgetTexts.getString("BeszúrButton_label")); //$NON-NLS-1$
			passzButton.setEnabled(false);
			lerakButton.setEnabled(false);
			break;

		case ClientController.PS_INACTIVE_BESZURHAT:
			passzButton.setLabel(resWidgetTexts.getString("PasszButton_label")); //$NON-NLS-1$
			lerakButton.setLabel(resWidgetTexts.getString("BeszúrButton_label")); //$NON-NLS-1$
			passzButton.setEnabled(false);
			lerakButton.setEnabled(true);
			break;

		case ClientController.PS_INACTIVE_VETOZHAT:
			passzButton.setLabel(resWidgetTexts.getString("PasszButton_label")); //$NON-NLS-1$
			lerakButton.setLabel(resWidgetTexts.getString("VétóButton_label")); //$NON-NLS-1$
			passzButton.setEnabled(false);
			lerakButton.setEnabled(true);
			break;

		case ClientController.PS_ACTIVE_SZINKERO:
		case ClientController.PS_ACTIVE_JELZESKERO:
		case ClientController.PS_ACTIVE_IRANYKERO:
			passzButton.setLabel(resWidgetTexts.getString("KérButton_label")); //$NON-NLS-1$
			lerakButton.setLabel(resWidgetTexts.getString("LerakButton_label")); //$NON-NLS-1$
			passzButton.setEnabled(true);
			lerakButton.setEnabled(false);
			break;

		case ClientController.PS_ACTIVE_GYENGE_LAPKERO:
			passzButton.setLabel(resWidgetTexts.getString("SemmiButton_label")); //$NON-NLS-1$
			lerakButton.setLabel(resWidgetTexts.getString("VálasztButton_label")); //$NON-NLS-1$
			passzButton.setEnabled(true);
			lerakButton.setEnabled(true);
			break;

		case ClientController.PS_ACTIVE_LAPKERO:
			passzButton.setLabel(resWidgetTexts.getString("SemmiButton_label")); //$NON-NLS-1$
			lerakButton.setLabel(resWidgetTexts.getString("VálasztButton_label")); //$NON-NLS-1$
			passzButton.setEnabled(false);
			lerakButton.setEnabled(true);
			break;

		default:
			Logger.log(new ErrStrLog(szr.shared.SubstituteStr.doIt("CardsInHand error: Invalid play state code: %n for setButtonPState().", "%n", Integer.toString(playState))), parentController.getGameName(), parentController.getPlayerName());//$NON-NLS-2$//$NON-NLS-1$
			break;
	}
}
/**
 * drag&drop kurzor beállítása
 */
private void setDragCursor(boolean on) {
	Cursor c = new Cursor((on) ? Cursor.E_RESIZE_CURSOR : Cursor.DEFAULT_CURSOR);
	for (int i = 0; i < cardSlots.length; ++i) {
		cardSlots[i].setCursor(c);
	}
}
/**
 * A ClientController hívja meg, ha létrejött
 */
public void setParentController(ClientController newParentController) {
	parentController = newParentController;
	smallImg.setInfoSystem(parentController.getInfoSystem());
}
/**
 * Kártyakijelölés változtatása	(-1: nincs kijelölt kártya)
 */
public void setSelectedIdx(int clientIndex) {
	int oldSlotIdx = getSlotIdx(selectedIdx);
	int newSlotIdx = getSlotIdx(clientIndex);
	cancelDrag();
	if (oldSlotIdx >= 0 && oldSlotIdx < cardSlots.length) {
		cardSlots[oldSlotIdx].setSelected(false);
	}
	if (newSlotIdx >= 0 && newSlotIdx < cardSlots.length) {
		int newCardVectIdx = getCardVectIdx(newSlotIdx);
		if (newCardVectIdx >= 0 && newCardVectIdx < cardVect.size()) {
			cardSlots[newSlotIdx].setSelected(true);
		} else {
			clientIndex = -1;
		}
	}
	selectedIdx = clientIndex;
	updateSmallImg();
}
/**
 * Smiley kijelzés beállítása (-1: nincs smiley) a smallImg-be
 */
public void setSmileyPState(int iSmileyPState) {
	smileyPState = iSmileyPState;
	setSelectedIdx(-1);
}
/**
 * A képernyõn kijelzendõ kártyák aktualizálása
 */
private void updateSlots() {
	for (int i = 0; i < cardSlots.length; ++i) {
		int j = getCardVectIdx(i);
		if (j < cardVect.size()) {
			cardSlots[i].setCard((Card)cardVect.elementAt(j));
		} else {
			cardSlots[i].clearCard();
		}
	}

	int slotSelIdx = getSlotIdx(selectedIdx);
	if (slotSelIdx >= 0 && slotSelIdx < cardSlots.length) {
		cardSlots[slotSelIdx].setSelected(true);
	}
}
/**
 * A szín/jelzés piktogram aktualizálása
 */
private void updateSmallImg() {
	if (selectedIdx >= 0 && selectedIdx < cardVect.size()) {
		smallImg.setSzin(((Card)cardVect.elementAt(selectedIdx)).getSzin());
		smallImg.setJelzes(((Card)cardVect.elementAt(selectedIdx)).getJelzes());
	} else if (smileyPState < 0) { // no smiley
		smallImg.clearSzin();
		smallImg.clearJelzes();
	} else {
		displaySmiley();
	}
}
}
