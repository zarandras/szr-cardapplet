package szr.maincli;

import szr.shared.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 * A kliens Játékoslista osztálya (a játékiránnyal és a smiley-val)
 * Creation date: (2002.01.27. 13:14:38)
 */
public class PlayerList implements TextListener, ItemListener {
	private static java.util.ResourceBundle resWidgetTexts = java.util.ResourceBundle.getBundle("szr.shared.WidgetTexts");  //$NON-NLS-1$
	private ClientController parentController = null;
	private final List pList;	// a lista képernyõelem
	private final Label felLeLabel;
	private final TextField smileyTextField;
	private final Vector pVect;	// a játékosok vektora
	private Irany irany = null;
public PlayerList(List iPList, Label iFelLeLabel, TextField iSmileyTextField) {
	super ();

	pList = iPList;
	felLeLabel = iFelLeLabel;
	smileyTextField = iSmileyTextField;

	pList.addItemListener(this);
	smileyTextField.addTextListener(this);
	
	pVect = new Vector();
	irany = new Irany(Irany.Lefele);
}
public void addPlayer(PlayerRec jr) {
	pVect.addElement (jr);
	pList.add (jr.toString ());
	updateSelection(pList.getItemCount() - 1);
}
public void adjustPlayer(PlayerRec jatekos) {
	int i = findPlayer(jatekos.name);
	try {
		pVect.setElementAt(jatekos, i);
		pList.replaceItem (jatekos.toString(), i);
		updateSelection(i);
	} catch (ArrayIndexOutOfBoundsException e) {
		szr.msg.Logger.log(new szr.msg.ErrExcLog(e), parentController.getGameName(), parentController.getPlayerName());
	}
}
public void delAllPlayers() {
	pList.removeAll();
	pVect.removeAllElements();
}
public void delPlayer(String jatekosNev) {
	int i = findPlayer(jatekosNev);
	try {
		pVect.removeElementAt(i);
		pList.deselect(i);
		pList.remove(i);
	} catch (ArrayIndexOutOfBoundsException e) {
		szr.msg.Logger.log(new szr.msg.ErrExcLog(e), parentController.getGameName(), parentController.getPlayerName());
	}
}
public void fillPlayerList(Vector jatekosVect) {
	delAllPlayers();
	for (int i = 0; i < jatekosVect.size (); ++i) {
		addPlayer ((PlayerRec) jatekosVect.elementAt (i));
	}
}
private int findPlayer(String jatekosNev) {
	int i = 0;
	while (i < pVect.size ()) {
		if (((PlayerRec) pVect.elementAt (i)).name.equals (jatekosNev))
			break;
		i++;
	}
	return i;
}
public String getMySmiley() {
  	String s = smileyTextField.getText();
  	if (s.length() > 16) {
	  	s = s.substring(0, 16);
  	}
	return s;
}
public ClientController getParentController() {
	return parentController;
}
private PlayerRec getPlayer(int i) {
	try {
		return (PlayerRec)pVect.elementAt(i);
	} catch (ArrayIndexOutOfBoundsException e) {
		szr.msg.Logger.log(new szr.msg.ErrExcLog(e), parentController.getGameName(), parentController.getPlayerName());
		return null;
	}
}
public boolean hasPlayer(String playerName) {
	int i = findPlayer(playerName);
	return (i >= 0 && i < pVect.size());
}
public boolean isEmpty() {
	return pVect.isEmpty();
}
public boolean isIranyFel() {
	return irany.isFelfele();
}
public boolean isIranyLe() {
	return irany.isLefele();
}
public void itemStateChanged(java.awt.event.ItemEvent e) {
	if (e.getSource() == pList) {	// user is disallowed to change selection
		synchronized (parentController) {
			for (int i = 0; i < pVect.size(); ++i) {
				updateSelection(i);
			}
		}
	}
}
public void setIrany(Irany newIrany) {
	irany = newIrany;
	felLeLabel.setText(irany.toString());
}
public void setParentController(ClientController newParentController) {
	parentController = newParentController;
}
public void textValueChanged(TextEvent e) {
  synchronized (parentController) {
	if (parentController.isJoined() && e.getSource () == smileyTextField && smileyTextField.isEnabled()) {
		parentController.getMsgHandler().send(new szr.msg.MGASmiley(getMySmiley()));
	}
  }
}
/**
 * A kijelölés változtatása az aktuális játékosra
 */
private void updateSelection(int playerIdx) {
	try {
		if (getPlayer(playerIdx).active) {
			pList.select(playerIdx);
		} else {
			pList.deselect(playerIdx);
		}
	} catch (NullPointerException e) {
		szr.msg.Logger.log(new szr.msg.ErrExcLog(e), parentController.getGameName(), parentController.getPlayerName());
		pList.deselect(playerIdx);
	}
}
}
