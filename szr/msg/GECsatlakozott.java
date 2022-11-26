package szr.msg;

import szr.shared.*;
import szr.maincli.*;
/**
 * Játékos csatlakozása eseményosztály
 * Creation date: (2002.02.06. 13:49:29)
 */
public class GECsatlakozott extends GameEvent {
	private static java.util.ResourceBundle resChatTexts = java.util.ResourceBundle.getBundle("szr.shared.ChatTexts");  //$NON-NLS-1$
	private final PlayerRec jatekos;
public GECsatlakozott(PlayerRec iJatekos) {
	super();
	jatekos = iJatekos;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	ClientController controller = cmh.getParentController ();
	controller.getPlayerList().addPlayer (jatekos);
}
public String toString() {
	return super.toString() + " " + jatekos.toString();//$NON-NLS-1$
}
}
