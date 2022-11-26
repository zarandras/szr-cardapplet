package szr.msg;

import szr.mainserv.*;
/**
 * J�t�kkezd�s akci�oszt�ly
 * Creation date: (2002.02.06. 17:06:17)
 */
public class SCAKezd extends ServerControlAction {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
public SCAKezd() {
	super();
}
public void doServer(szr.mainserv.ServerMsgHandler smh) {
	PlayerData pd = smh.getPlayerData();
	if (pd == null) {
		smh.send (new PEReject (resInfoTexts.getString("Nem_csatlakozt�l_egyik_j�t"))); //$NON-NLS-1$
		return;
	}
	if (!pd.getParentController().startGame()) {
		if (pd.getParentController().getPlayerCount() == 1)
			smh.send (new PEReject (resInfoTexts.getString("Egyed�l_nem_lehet_j�tszani"))); //$NON-NLS-1$
		else
			smh.send (new PEReject (resInfoTexts.getString("A_j�t�k_nem_kezdhet�_el,_m"))); //$NON-NLS-1$
		return;
	}
}
public String toString() {
	return super.toString();
}
}
