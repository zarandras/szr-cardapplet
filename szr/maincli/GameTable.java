package szr.maincli;

import java.awt.*;
import java.awt.event.*;
import szr.gui.*;
import szr.card.*;
import szr.shared.*;
import java.util.*;
/**
 * A kliens JátékTér osztálya
 * Creation date: (2002.01.27. 14:42:59)
 */
public class GameTable implements ItemListener, AdjustmentListener {
	private static java.util.ResourceBundle resWidgetTexts = java.util.ResourceBundle.getBundle("szr.shared.WidgetTexts");  //$NON-NLS-1$
	private ClientController parentController = null;
	private final CardComponent lerakottLap;
	private final Scrollbar lerakottScrollbar;
	private final CardComponent pakli;
	private final SmallImgComponent aktSzinJel;
	private final CardComponent jelzeslap;
	private final SmallImgComponent jelzesSzinJel;
	private final SmallImgComponent kertSzin;
	private final SmallImgComponent kertJel;
	private final Choice kertSzinChoice;
	private final Choice kertIranyChoice;
	private final Choice kertJelChoice;
	private final Vector lerakottLapok;
	private TableStateRec tableState = null;
	private szr.card.Jelzes[] kerhetoJelek = null;
	private int kerhetoSzinMask = 0;
public GameTable(CardComponent iLerakottLap, Scrollbar iLerakottScrollbar, SmallImgComponent iAktSzinJel,
				 CardComponent iPakli, CardComponent iJelzeslap, SmallImgComponent iJelzesSzinJel,
				 Choice iKertSzinChoice, SmallImgComponent iKertSzin,
				 Choice iKertJelChoice, SmallImgComponent iKertJel,
				 Choice iKertIranyChoice) {
	super ();
	
	lerakottLap = iLerakottLap;
	lerakottScrollbar = iLerakottScrollbar;
	aktSzinJel = iAktSzinJel;
	pakli = iPakli;
	jelzeslap = iJelzeslap;
	jelzesSzinJel = iJelzesSzinJel;
	kertSzin = iKertSzin;
	kertJel = iKertJel;
	kertSzinChoice = iKertSzinChoice;
	kertJelChoice = iKertJelChoice;
	kertIranyChoice = iKertIranyChoice;
	
	kertSzinChoice.addItemListener(this);
	kertJelChoice.addItemListener(this);
	kertIranyChoice.addItemListener(this);
	lerakottScrollbar.addAdjustmentListener(this);
	
	//pakli.setCard (szr.gui.CardApplet.baseURL + "/cardimg/cv.gif");
	kertSzinChoice.insert (resWidgetTexts.getString("(...)"), 0); //$NON-NLS-1$
	// setKerhetoSzinMask() fills it
	
	kertJelChoice.insert (resWidgetTexts.getString("(...)"), 0); //$NON-NLS-1$
	// setKerhetoJelek() fills it
	
	kertIranyChoice.insert(resWidgetTexts.getString("(...)"), 0); //$NON-NLS-1$
	kertIranyChoice.insert (resWidgetTexts.getString("Fel"), 1); //$NON-NLS-1$
	kertIranyChoice.insert (resWidgetTexts.getString("Le"), 2); //$NON-NLS-1$

	lerakottLapok = new Vector();
	lerakottScrollbar.setValues(0, 1, 0, 0);

	tableState = new TableStateRec(null, null, null);
}
public void adjustmentValueChanged(java.awt.event.AdjustmentEvent e) {
  synchronized (parentController) {
	if (e.getAdjustable () == lerakottScrollbar) {
		try {
			lerakottLap.setCard((Card)lerakottLapok.elementAt(lerakottScrollbar.getValue()));
		} catch (ArrayIndexOutOfBoundsException ex) {
			lerakottLap.clearCard();
		}
	}
  }
}
public void clear() {
	setJelzeslap(null);
	setKertSzin(null);
	setKertJel(null);
	setAktSzinJel(null, null);
	purgeLerakottLapok();
	hidePakli();
}
public void disableKertChoices() {
	kertSzinChoice.setEnabled(false);	
	kertJelChoice.setEnabled(false);	
	kertIranyChoice.setEnabled(false);	
}
public void enableKertIrany() {
	kertIranyChoice.setEnabled(true);
}
public void enableKertJel() {
	kertJelChoice.setEnabled(true);
}
public void enableKertSzin() {
	kertSzinChoice.setEnabled(true);
}
public Jelzes getAktJelzes() {
	try {
		return tableState.aktJelzes;
	} catch (NullPointerException e) {
		return null;
	}
}
public Szin getAktSzin() {
	try {
		return tableState.aktSzin;
	} catch (NullPointerException e) {
		return null;
	}
}
public Card getDisplayedCardObj() {
	return lerakottLap.getCardObj();
}
public Card getJelzeslap() {
	try {
		return tableState.jelzeslap;
	} catch (NullPointerException e) {
		return null;
	}
}
private Jelzes getKerhetoJel(int idx) {
	if (kerhetoJelek == null || idx < 0 || idx >= kerhetoJelek.length) {
		return null;
	} else {
		return kerhetoJelek[idx];
	}
}
public Irany getKertIrany() {
	int idx = kertIranyChoice.getSelectedIndex();
	switch (idx) {
		case 0:  return null;
		case 1:	 return new Irany(Irany.Felfele);
		case 2:  return new Irany(Irany.Lefele);
		default: 
			szr.msg.Logger.log(new szr.msg.ErrStrLog(szr.shared.SubstituteStr.doIt("GameTable error: invalid kertIranyChoice item index %i", "%i", Integer.toString(idx))), parentController.getGameName(), parentController.getPlayerName());//$NON-NLS-2$//$NON-NLS-1$
			return null;
	}
}
public Jelzes getKertJel() {
	return (Jelzes)kertJel.getJelzesObj();
}
public SzinesSzin getKertSzin() {
	return (SzinesSzin)kertSzin.getSzinObj();
}
public ClientController getParentController() {
	return parentController;
}
public TableStateRec getTableState() {
	return tableState;
}
public void hidePakli() {
	pakli.clearCard();
}
private boolean isKerhetoJel(Jelzes j) {
	if (kerhetoJelek == null)
		return false;
		
	int i = 0;
	while (i < kerhetoJelek.length && !kerhetoJelek[i].toString().equals(j.toString())) {
		i++;
	}
	return (i < kerhetoJelek.length);
}
private boolean isKerhetoSzin(SzinesSzin c) {
	return (c.isEgyszinu() && (c.getMask() & kerhetoSzinMask) != 0);
}
public void itemStateChanged(java.awt.event.ItemEvent e) {
  synchronized (parentController) {
	if (e.getStateChange () == ItemEvent.SELECTED) {
		Object srcWidget = e.getItemSelectable ();
		if (srcWidget == kertSzinChoice && kertSzinChoice.isEnabled()) {
			if (kertSzinChoice.getSelectedIndex() == 0) {
				kertSzin.clearSzin();
			} else {
				// megkeressük, melyik szín van kiválasztva:
				int mask = 1;
				int selIdx = kertSzinChoice.getSelectedIndex();
				do {
					if ((kerhetoSzinMask & mask) != 0)
						selIdx--;
					mask <<= 1;
				} while (selIdx != 0);
				mask >>= 1;
				kertSzin.setSzin(new SzinesSzin(mask));
			}
		} else if (srcWidget == kertJelChoice && kertJelChoice.isEnabled()) {
			if (kertJelChoice.getSelectedIndex() == 0) {
				kertJel.clearJelzes();
			} else {
				kertJel.setJelzes(getKerhetoJel(kertJelChoice.getSelectedIndex() - 1) /*(Jelzes)JelzesKero.KERHETO_JELZES[kertJelChoice.getSelectedIndex() - 1]*/);
			}
		} else if (srcWidget == kertIranyChoice && kertIranyChoice.isEnabled()) {
			// empty
		}
	}
  }
}
public void popLerakottLap() {
	if (!lerakottLapok.isEmpty()) {
		lerakottLapok.removeElementAt(0);
		lerakottScrollbar.setValues(0, 1, 0, lerakottLapok.size());
		if (!lerakottLapok.isEmpty()) {
			lerakottLap.setCard((Card)lerakottLapok.firstElement());
		} else {
			lerakottLap.clearCard();
		}
	} else {
		szr.msg.Logger.log(new szr.msg.ErrStrLog("GameTable error: popLerakottLap called for empty card stack."), parentController.getGameName(), parentController.getPlayerName());//$NON-NLS-1$
	}
}
public void purgeLerakottLapok() {
	lerakottScrollbar.setValues(0, 1, 0, 0);
	lerakottLapok.removeAllElements();
	lerakottLap.clearCard();
}
public void pushLerakottLap(Card newCard) {
	lerakottLapok.insertElementAt(newCard, 0);
	lerakottScrollbar.setValues(0, 1, 0, lerakottLapok.size());
	lerakottLap.setCard(newCard);
}
public void resetAllKert() {
	setKertSzin(null);
	setKertJel(null);
	setKertIrany(null);
}
public void setAktSzinJel(Szin aktSzin, Jelzes aktJelzes) {
	setTableState(new TableStateRec (aktSzin, aktJelzes, tableState.jelzeslap));
}
public void setJelzeslap(Card jelzeslap) {
	setTableState(new TableStateRec (tableState.aktSzin, tableState.aktJelzes, jelzeslap));
}
public void setKerhetoJelek(Jelzes[] newKerhetoJelek) {
	kerhetoJelek = newKerhetoJelek;
	kertJelChoice.removeAll();
	kertJelChoice.insert (resWidgetTexts.getString("(...)"), 0); //$NON-NLS-1$
	for (int i = 0; i < kerhetoJelek.length; ++i) {
		kertJelChoice.insert (kerhetoJelek[i].toString(), i + 1);
	}
}
public void setKerhetoSzinMask(int newKerhetoSzinMask) {
	kerhetoSzinMask = newKerhetoSzinMask;
	kertSzinChoice.removeAll();
	kertSzinChoice.insert (resWidgetTexts.getString("(...)"), 0); //$NON-NLS-1$

	int idx = 1;
	for (int i = 0; newKerhetoSzinMask != 0; newKerhetoSzinMask >>= 1, ++i) {
		if ((newKerhetoSzinMask & 1) == 1) {
			kertSzinChoice.insert (new SzinesSzin(1 << i).toString(), idx);
			idx++;
		}
	}
}
public void setKertIrany(Irany kertIrany) {
	if (kertIrany == null) {
		kertIranyChoice.select(0);
	} else if (kertIrany.isFelfele()) {
		kertIranyChoice.select(1);
	} else { // lefele
		kertIranyChoice.select(2);
	}
}
public void setKertJel(Jelzes newJelzes) {
	if (newJelzes == null) {
		kertJel.clearJelzes();
		kertJelChoice.select(0);
	} else if (newJelzes instanceof ExtremalJel) {	// lejárt az idõ, ilyenkor bármely kérhetõt lehet rakni
		kertJel.setJelzes(newJelzes);
		kertJelChoice.select(0);
	} else {
		if (!isKerhetoJel(newJelzes)) {
			// ha nem kérhetõ, akkor átállítjuk a kérhetõeket erre az egyre
			Jelzes[] kj = new Jelzes[1];
			kj[0] = newJelzes;
			setKerhetoJelek(kj);
		}
		kertJel.setJelzes(newJelzes);
		kertJelChoice.select(newJelzes.toString());
	}
}
public void setKertSzin(SzinesSzin newSzin) {
	if (newSzin == null) {
		kertSzin.clearSzin();
		kertSzinChoice.select(0);
	} else if (!newSzin.isEgyszinu()) {
		kertSzin.setSzin(newSzin);
		kertSzinChoice.select(0);		// többszínût (pl.kétszinût) nem lehet kérni
	} else {
		if (!isKerhetoSzin(newSzin)) {
			// ha nem kérhetõ, akkor átállítjuk a kérhetõeket erre az egyre
			int kc = newSzin.getMask();
			setKerhetoSzinMask(kc);
		}
		kertSzin.setSzin(newSzin);
		kertSzinChoice.select(newSzin.toString());
	}
}
public void setParentController(ClientController newParentController) {
	parentController = newParentController;
	aktSzinJel.setInfoSystem(parentController.getInfoSystem());
	jelzesSzinJel.setInfoSystem(parentController.getInfoSystem());
	kertSzin.setInfoSystem(parentController.getInfoSystem());
	kertJel.setInfoSystem(parentController.getInfoSystem());
}
public void setTableState(TableStateRec ts) {
	if (ts == null) {
		tableState = new TableStateRec(null, null, null);
	} else {
		tableState = ts;
	}
	updateJelzeslap();
	updateAktSzinJel();
}
public void showPakli() {
	pakli.setBackRotCard();
}
public Card topLerakottLap() {
	try {
		return (Card)lerakottLapok.elementAt(0);
	} catch (ArrayIndexOutOfBoundsException e) {
		return null;
	}
}
private void updateAktSzinJel() {
	try {
		// aktSzin
		if (tableState.aktSzin == null) {
			aktSzinJel.clearSzin();
		} else {
			aktSzinJel.setSzin(tableState.aktSzin);
		}

		// aktJelzes
		if (tableState.aktJelzes == null) {
			aktSzinJel.clearJelzes();
		} else {
			aktSzinJel.setJelzes(tableState.aktJelzes);
		}

		// kertJel
		if (tableState.aktJelzes instanceof JelzesKero) {
			setKertJel(((JelzesKero)tableState.aktJelzes).getKertJel());
		} else if (tableState.aktSzin instanceof JelzesKero) {
			setKertJel(((JelzesKero)tableState.aktSzin).getKertJel());
		} else {
			setKertJel(null);
		}


		// kertSzin
		if (tableState.aktJelzes instanceof SzinKero) {
			setKertSzin(((SzinKero)tableState.aktJelzes).getKertSzin());
		} else if (tableState.aktSzin instanceof SzinKero) {
			setKertSzin(((SzinKero)tableState.aktSzin).getKertSzin());
		} else {
			setKertSzin(null);
		}
	} catch (NullPointerException e) {
		szr.msg.Logger.log(new szr.msg.ErrExcLog(e), parentController.getGameName(), parentController.getPlayerName());
		aktSzinJel.clearSzin();
		aktSzinJel.clearJelzes();
		setKertJel(null);
		setKertSzin(null);
	}
}
private void updateJelzeslap() {
	try {
		jelzeslap.setCard(tableState.jelzeslap);
		jelzesSzinJel.setSzin(tableState.jelzeslap.getSzin());
		jelzesSzinJel.setJelzes(tableState.jelzeslap.getJelzes());
	} catch (NullPointerException e) {
		jelzeslap.clearCard();
		jelzesSzinJel.clearSzin();
		jelzesSzinJel.clearJelzes();
	}
}
}
