package szr.msg;

import szr.shared.*;
import szr.mainserv.*;
/**
 * �j j�t�k meghirdet�se akci�oszt�ly
 * Creation date: (2002.02.05. 20:52:49)
 */
public class SCAJatekotHirdet extends ServerControlAction {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private final GameRec jatek;
	private final PlayerRec jatekos;
	private final java.lang.String jelszo;
public SCAJatekotHirdet(GameRec iJatek, PlayerRec iJatekos, String iJelszo) {
	super();

	jatek = iJatek;
	jatekos = iJatekos;
	jelszo = iJelszo;
}
public void doServer(szr.mainserv.ServerMsgHandler smh) {
	if (!smh.getParentGameServer().checkPassword (jatekos.name, jatekos.email, jelszo)) {
		smh.send (new PEReject (resInfoTexts.getString("A_be�rt_jelsz�_nem_megfele"))); //$NON-NLS-1$
		return;
	}
	if (!smh.getParentGameServer().addNewGame(jatek, jatekos, smh)) {
		smh.send (new PEReject (resInfoTexts.getString("A_j�t�k_nem_hirdethet�_meg"))); //$NON-NLS-1$
		return;
	}
}
public String toString() {
	return super.toString() + " " + jatek.toString() + "; " + jatekos.toString();//$NON-NLS-2$//$NON-NLS-1$
}
}
