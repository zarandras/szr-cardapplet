package szr.msg;

import szr.maincli.*;
import java.util.*;
/**
 * Üdvözlõ eseményosztály (kiválasztható játékjellemzõk megküldése)
 * Creation date: (2002.02.06. 13:49:29)
 */
public class PEWelcome extends PersonalEvent {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private final Vector pakliVect;
	private final int minOsztottLapok;
	private final int maxOsztottLapok;
public PEWelcome(Vector iPakliVect, int iMinOsztottLapok, int iMaxOsztottLapok) {
	super();
	pakliVect = iPakliVect;
	minOsztottLapok = iMinOsztottLapok;
	maxOsztottLapok = iMaxOsztottLapok;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	ClientController cc = cmh.getParentController();
	cc.getGameControlPanel().fillPakliJellChoice(pakliVect);
	cc.getGameControlPanel().fillOsztasJellChoice(minOsztottLapok, maxOsztottLapok);
	cc.getInfoSystem().setInfoText(resInfoTexts.getString("Add_meg_a_játékos-adataida")); //$NON-NLS-1$
	cc.getGameControlPanel().resetWidgetState();
	cc.getPlayerControlPanel().setEnabled(true);
}
public String toString() {
	return super.toString();
}
}
