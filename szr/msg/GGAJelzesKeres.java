package szr.msg;

import szr.mainserv.*;
import szr.card.*;
/**
 * Jelz�sk�r�s akci�oszt�ly
 * Creation date: (2002.02.06. 23:48:55)
 */
public class GGAJelzesKeres extends GeneralGameAction {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private final Jelzes kertJelzes;
	private final int myGACount;	// h�nyadik GameAction volt legut�bb
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
			 smh.send (new PEReject(resInfoTexts.getString("�rv�nytelen_a_jelz�sk�r�s!"))); //$NON-NLS-1$
		}
	}
}
public String toString() {
	return super.toString() + " " + kertJelzes.toString();//$NON-NLS-1$
}
}
