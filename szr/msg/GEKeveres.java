package szr.msg;

import szr.maincli.*;
/**
 * Paklikeverés eseményosztály
 * Creation date: (2002.02.08. 16:58:53)
 */
public class GEKeveres extends GameEvent {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	
public GEKeveres() {
	super();
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	cmh.getParentController().getGameTable().showPakli();
	cmh.getParentController().getInfoSystem().setInfoText(resInfoTexts.getString("Keverés...")); //$NON-NLS-1$
	cmh.getParentController().setCurrGACount(0);
}
public String toString() {
	return super.toString();
}
}
