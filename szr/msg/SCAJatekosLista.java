package szr.msg;

import szr.mainserv.*;
/**
 * Játékoslista kérés akcióosztály
 * Creation date: (2002.02.06. 17:23:57)
 */
public class SCAJatekosLista extends ServerControlAction {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private final String jatekNev;
public SCAJatekosLista(String iJatekNev) {
	super();
	jatekNev = iJatekNev;
}
public void doServer(szr.mainserv.ServerMsgHandler smh) {
	GameController gc = smh.getParentGameServer().getGame(jatekNev);
	if (gc == null) {
		smh.send (new PEReject (resInfoTexts.getString("Ilyen_nevû_játék_nincs_meg"))); //$NON-NLS-1$
		return;
	}
	smh.send (new PEJatekosLista (gc.getPlayerRecs()));
}
public String toString() {
	return super.toString() + " " + jatekNev;//$NON-NLS-1$
}
}
