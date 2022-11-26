package szr.msg;

import szr.mainserv.*;
import szr.card.*;
/**
 * Jelzéskérés akcióosztály
 * Creation date: (2002.02.06. 23:48:55)
 */
public class GGAJelzesKeres extends GeneralGameAction {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private final Jelzes kertJelzes;
	private final int myGACount;	// hányadik GameAction volt legutóbb
public GGAJelzesKeres(Jelzes iKertJelzes, int iMyGACount) {
	super();
	kertJelzes = iKertJelzes;
	myGACount = iMyGACount;
}
public void doServer(szr.mainserv.ServerMsgHandler smh) {
	PlayerData pData = smh.getPlayerData();
	if (pData != null) {
		if (pData.getParentController().kertJelOK(pData, kertJelzes, myGACount)) {
			pData.getParentController().getGameLAut().contNextState();
		} else {
			 smh.send (new PEReject(resInfoTexts.getString("Érvénytelen_a_jelzéskérés!"))); //$NON-NLS-1$
		}
	}
}
public String toString() {
	return super.toString() + " " + kertJelzes.toString();//$NON-NLS-1$
}
}
