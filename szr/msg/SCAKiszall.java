package szr.msg;

import szr.mainserv.*;
/**
 * Játékból való kiszállás akcióosztály
 * Creation date: (2002.02.06. 17:06:17)
 */
public class SCAKiszall extends ServerControlAction {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
public SCAKiszall() {
	super();
}
public void doServer(szr.mainserv.ServerMsgHandler smh) {
	PlayerData pd = smh.getPlayerData();
	if (pd == null) {
		smh.send (new PEReject (resInfoTexts.getString("Nem_tudsz_honnan_kiszállni"))); //$NON-NLS-1$
		return;
	}
	pd.getParentController().delPlayer(pd, false);
	pd.removeParentController();
	smh.clearPlayerData();
}
public String toString() {
	return super.toString();
}
}
