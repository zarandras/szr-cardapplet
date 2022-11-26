package szr.msg;

import szr.maincli.*;
/**
 * GameAction-feldolgozás kezdete eseményosztály
 * Creation date: (2002.02.08. 16:58:53)
 */
public class GEActionAccepted extends GameEvent {
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private final java.lang.String playerName;
	private final java.lang.String actionInfoText;
public GEActionAccepted(String iPlayerName, String iActionInfoText) {
	super();

	playerName = iPlayerName;
	actionInfoText = iActionInfoText;
}
public void doClient(szr.maincli.ClientMsgHandler cmh) {
	szr.maincli.ClientController cc = cmh.getParentController();
	cc.getCardsInHand().disableActionButtons();
	cc.getGameTable().disableKertChoices();
	if (cc.isActionSentFlag()) {
		cc.clearActionSentFlag();
		if (!cc.getPlayerName().equals(playerName)) {
			cc.rejectedAction(szr.shared.SubstituteStr.doIt(resInfoTexts.getString("Elkéstél!_%pstr_megelõzött"), "%pstr", playerName), RejectDlgHandler.PSR_MEGELOZOTT);//$NON-NLS-2$ //$NON-NLS-1$
		}
	}
	cc.disableActionControls(actionInfoText);
}
public String toString() {
	return super.toString() + " player: " + playerName + ", action: " + actionInfoText.replace('\n',' ');//$NON-NLS-2$//$NON-NLS-1$
}
}
