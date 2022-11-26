package szr.card;

/**
 * Felkiáltójel jelzésosztály
 * Creation date: (2002.03.06. 10:54:00)
 */
public class FelkialtoJel extends AzHatJel {
	private static java.util.ResourceBundle resChatTexts = java.util.ResourceBundle.getBundle("szr.shared.ChatTexts");  //$NON-NLS-1$
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	
public boolean doLPostEvent(szr.mainserv.GameController jv, int invokeCounter) {
	return doPreEvent(jv, invokeCounter);
}
public boolean doPreEvent(szr.mainserv.GameController jv, int invokeCounter) {
	switch (invokeCounter) {
		case 0:
			jv.sendSysChatMsg(szr.shared.SubstituteStr.doIt(resChatTexts.getString("%p_eldob_egy_lapot_a_pakli"), "%p", jv.getActivePlayer().getProperties().name));//$NON-NLS-2$ //$NON-NLS-1$
			jv.forceLapKeres(jv.getActivePlayer(), false);
			return false;

		case 1:
			Card c = jv.getActivePlayer().captureKertLap();
			if (c != null){
				jv.getPakli().pushCard(c);
			}
			return true;
			
		default:
			logInvalidInvokeCounter(invokeCounter);	
			return true;
	}
}
public boolean getDoublePicFlag() {
	return false;
}
public int getID() {
	return 28;
}
public boolean hasAImg() {
	return true;
}
public boolean hasLImg() {
	return true;
}
public String toString() {
	return resCardTexts.getString("Felkiáltójel"); //$NON-NLS-1$
}
}
