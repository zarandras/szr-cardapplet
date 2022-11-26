package szr.msg;

import szr.mainserv.*;
import szr.card.*;
/**
 * Színkérés akcióosztály
 * Creation date: (2002.02.06. 23:48:55)
 */
public class GGASzinKeres extends GeneralGameAction {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private final SzinesSzin kertSzin;
	private final int myGACount;	// hányadik GameAction volt legutóbb
public GGASzinKeres(SzinesSzin iKertSzin, int iMyGACount) {
	super();
	kertSzin = iKertSzin;
	myGACount = iMyGACount;
}
public void doServer(szr.mainserv.ServerMsgHandler smh) {
	PlayerData pData = smh.getPlayerData();
	if (pData != null) {
		if (pData.getParentController().kertSzinOK(pData, kertSzin, myGACount)) {
			pData.getParentController().getGameLAut().contNextState();
		} else {
			 smh.send (new PEReject(resInfoTexts.getString("Érvénytelen_a_színkérés!_C"))); //$NON-NLS-1$
		}
	}
}
public String toString() {
	return super.toString() + " " + kertSzin.toString();//$NON-NLS-1$
}
}
