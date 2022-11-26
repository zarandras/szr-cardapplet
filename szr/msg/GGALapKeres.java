package szr.msg;

import szr.mainserv.*;
import szr.card.*;
/**
 * K�rtyalapv�laszt�s akci�oszt�ly
 * Creation date: (2002.02.06. 23:48:55)
 */
public class GGALapKeres extends GeneralGameAction {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private final int kertLapIdx;
	private final int myGACount;	// h�nyadik GameAction volt legut�bb
public GGALapKeres(int iKertLapIdx, int iMyGACount) {
	super();
	kertLapIdx = iKertLapIdx;
	myGACount = iMyGACount;
}
public void doServer(szr.mainserv.ServerMsgHandler smh) {
	PlayerData pData = smh.getPlayerData();
	if (pData != null) {
		if (pData.getParentController().expectsGA(myGACount)) {
			if (pData.getParentController().kertLapIdxOK(pData, kertLapIdx)) {
				pData.getParentController().getGameLAut().contNextState();
			} else {
				smh.send (new PEReject(resInfoTexts.getString("�rv�nytelen_a_lapkiv�laszt"))); //$NON-NLS-1$
			}
		} else {
			smh.send (new PEReject(resInfoTexts.getString("�rv�nytelen_a_lapkiv�laszt"))); //$NON-NLS-1$
		}
	}
}
public String toString() {
	return super.toString() + " cardIdx: " + kertLapIdx;//$NON-NLS-1$
}
}
