package szr.msg;

import szr.mainserv.*;
import szr.shared.*;
/**
 * Iránykérés akcióosztály
 * Creation date: (2002.02.06. 23:48:55)
 */
public class GGAIranyKeres extends GeneralGameAction {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private final Irany kertIrany;
	private final int myGACount;	// hányadik GameAction volt legutóbb
public GGAIranyKeres(Irany iKertIrany, int iMyGACount) {
	super();
	kertIrany = iKertIrany;
	myGACount = iMyGACount;
}
public void doServer(szr.mainserv.ServerMsgHandler smh) {
	PlayerData pData = smh.getPlayerData();
	if (pData != null) {
		if (pData.getParentController().kertIranyOK(pData, kertIrany, myGACount)) {
			pData.getParentController().getGameLAut().contNextState();
		} else {
			 smh.send (new PEReject(resInfoTexts.getString("Érvénytelen_az_iránykérés!"))); //$NON-NLS-1$
		}
	}
}
public String toString() {
	return super.toString() + " " + kertIrany.toString();//$NON-NLS-1$
}
}
