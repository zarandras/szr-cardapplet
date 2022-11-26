package szr.card;

import java.util.Vector;
import szr.mainserv.*;
/**
 * Kiosztó jelzésosztály
 * Creation date: (2002.03.06. 21:53:29)
 */
public class KiosztoJel extends AzHatJel {
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	private static java.util.ResourceBundle resChatTexts = java.util.ResourceBundle.getBundle("szr.shared.ChatTexts");  //$NON-NLS-1$
	
public boolean doLPostEvent(szr.mainserv.GameController jv, int invokeCounter) {
	return doPostEvent(jv, invokeCounter);
}
public boolean doPostEvent(szr.mainserv.GameController jv, int invokeCounter) {
	if (invokeCounter == 0) {
		PlayerData pd = jv.getActivePlayer();
		Vector kiosztando = pd.getCards();
		pd.removeAllCards();

		int actIdx = jv.indexOfActivePlayer();
		int idx = jv.getNextPlayerIdx(actIdx);
		while (!kiosztando.isEmpty()) {
			if (idx != actIdx) {
				jv.getPlayer(idx).addCard((Card)kiosztando.elementAt(0));
				kiosztando.removeElementAt(0);
			}
			idx = jv.getNextPlayerIdx(idx);
		}

		Vector newCards = new Vector();
		for (int i = 0; i < pd.getProperties().cardCount; ++i) {
			newCards.addElement(jv.getPakli().popCard());
		}
		pd.addCards(newCards);
		jv.updateAllCardCount();
		jv.sendSysChatMsg(szr.shared.SubstituteStr.doIt(resChatTexts.getString("%p_kiosztotta_lapjait_és_k"), "%p", jv.getActivePlayer().getProperties().name));//$NON-NLS-2$ //$NON-NLS-1$
	} else {
		logInvalidInvokeCounter(invokeCounter);
	}
	return true;
}
public int getID() {
	return 21;
}
public boolean hasAImg() {
	return true;
}
public boolean hasLImg() {
	return false;
}
public String toString() {
	return resCardTexts.getString("Kiosztó"); //$NON-NLS-1$
}
}
