package szr.card;

import java.util.Vector;
import szr.mainserv.*;
/**
 * Újrahasznosító jelzésosztály
 * Creation date: (2002.03.06. 21:53:29)
 */
public class RecyclJel extends AzHatJel {
	private static java.util.ResourceBundle resChatTexts = java.util.ResourceBundle.getBundle("szr.shared.ChatTexts");  //$NON-NLS-1$
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	
public boolean doLPostEvent(szr.mainserv.GameController jv, int invokeCounter) {
	return doPostEvent(jv, invokeCounter);
}
public boolean doPostEvent(szr.mainserv.GameController jv, int invokeCounter) {
	if (invokeCounter == 0) {
		PlayerData pd = jv.getActivePlayer();
		Vector droppedCards = pd.getCards();
		Card recyclJelCard = jv.popLerakottLap();
		pd.removeAllCards();

		Card c = null;
		boolean paklibol = false;
		for (int i = 0; i < pd.getProperties().cardCount; ++i) {
			if (!paklibol) {
				c = jv.popLerakottLap();
				if (c == null) {
					paklibol = true;
				}
			}
			if (paklibol) {	// not 'else if'
				c = jv.getPakli().popCard();
			}
			pd.addCard(c);
		}
		while (!droppedCards.isEmpty()) {
			jv.pushLerakottLap((Card)droppedCards.elementAt(0));
			droppedCards.removeElementAt(0);
		}
		jv.pushLerakottLap(recyclJelCard);
		jv.sendSysChatMsg(szr.shared.SubstituteStr.doIt(resChatTexts.getString("%p_újrahasznosított."), "%p", jv.getActivePlayer().getProperties().name));//$NON-NLS-2$ //$NON-NLS-1$
		if (paklibol)
			jv.sendSysChatMsg(resChatTexts.getString("(Mivel_még_nem_volt_elég_l")); //$NON-NLS-1$
	} else {
		logInvalidInvokeCounter(invokeCounter);
	}
	return true;
}
public int getID() {
	return 20;
}
public boolean hasAImg() {
	return false;
}
public boolean hasLImg() {
	return false;
}
public String toString() {
	return resCardTexts.getString("Újrahasznosító"); //$NON-NLS-1$
}
}
