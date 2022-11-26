package szr.msg;

import szr.maincli.*;
/**
 * Játékos kiszállása eseményosztály
 * Creation date: (2002.02.06. 13:49:29)
 */
public class GEKiszallt extends GameEvent {
	private static java.util.ResourceBundle resChatTexts = java.util.ResourceBundle.getBundle("szr.shared.ChatTexts");  //$NON-NLS-1$
	private final java.lang.String jatekosNev;
public GEKiszallt(String iJatekosNev) {
	super();

	jatekosNev = iJatekosNev;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	ClientController controller = cmh.getParentController ();
	controller.getPlayerList().delPlayer (jatekosNev);
}
public String toString() {
	return super.toString() + " " + jatekosNev;//$NON-NLS-1$
}
}
