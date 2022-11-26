package szr.msg;

import szr.shared.*;
/**
 * Csevegõ üzenet akcióosztály
 * Creation date: (2002.02.06. 23:48:55)
 */
public class MGAChat extends MetaGameAction {
	private static java.util.ResourceBundle resChatTexts = java.util.ResourceBundle.getBundle("szr.shared.ChatTexts");  //$NON-NLS-1$
	private static java.util.ResourceBundle resInfoTexts = java.util.ResourceBundle.getBundle("szr.shared.InfoTexts");  //$NON-NLS-1$
	private final java.lang.String message;
public MGAChat(String iMessage) {
	super();
	message = iMessage;
}
public void doServer(szr.mainserv.ServerMsgHandler smh) {
	if (smh.getPlayerData() == null) {
		smh.send (new PEReject (resInfoTexts.getString("Nincs_kivel_csevegni_n,_me"))); //$NON-NLS-1$
	} else {
		szr.mainserv.PlayerData pData = smh.getPlayerData();
		pData.getParentController().sendToAll(
			new GEChat(SubstituteStr.doIt(SubstituteStr.doIt(SubstituteStr.doIt(resChatTexts.getString("PlayerChatMsg"), //$NON-NLS-1$
				"%smiley", pData.getProperties().smiley),//$NON-NLS-1$
				"%pname", pData.getProperties().name),//$NON-NLS-1$
				"%msg", message)));//$NON-NLS-1$
	}
}
public String toString() {
	return super.toString() + " " + message.replace('\n', ' ');//$NON-NLS-1$
}
}
