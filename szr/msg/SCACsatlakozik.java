package szr.msg;

import szr.shared.*;
import szr.mainserv.*;
/**
 * J�t�khoz csatlakoz�s akci�oszt�ly
 * Creation date: (2002.02.05. 20:52:49)
 */
public class SCACsatlakozik extends ServerControlAction {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private final String jatekNev;
	private final PlayerRec jatekos;
	private final java.lang.String jelszo;
public SCACsatlakozik(String iJatekNev, PlayerRec iJatekos, String iJelszo) {
	super();

	jatekNev = iJatekNev;
	jatekos = iJatekos;
	jelszo = iJelszo;
}
public void doServer(szr.mainserv.ServerMsgHandler smh) {
	if (!smh.getParentGameServer().checkPassword (jatekos.name, jatekos.email, jelszo)) {
		smh.send (new PEReject (resInfoTexts.getString("A_be�rt_jelsz�_nem_megfele"))); //$NON-NLS-1$
		return;
	}
	GameController gc = smh.getParentGameServer().getGame(jatekNev);
	if (gc == null) {
		smh.send (new PEReject (resInfoTexts.getString("Ilyen_nev�_j�t�k_nincs_meg"))); //$NON-NLS-1$
		return;
	}
	gc.addPlayer (jatekos, smh);
}
public String toString() {
	return super.toString() + " " + jatekNev + "; " + jatekos.toString();//$NON-NLS-2$//$NON-NLS-1$
}
}
