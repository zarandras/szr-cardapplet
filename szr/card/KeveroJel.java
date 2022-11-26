package szr.card;

import java.util.Vector;
import szr.mainserv.*;
/**
 * Keverõ jelzésosztály
 * Creation date: (2002.03.06. 21:53:29)
 */
public class KeveroJel extends AzHatJel {
	private static java.util.ResourceBundle resChatTexts = java.util.ResourceBundle.getBundle("szr.shared.ChatTexts");  //$NON-NLS-1$
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	
public boolean doLPostEvent(szr.mainserv.GameController jv, int invokeCounter) {
	return doPostEvent(jv, invokeCounter);
}
public boolean doPostEvent(szr.mainserv.GameController jv, int invokeCounter) {
	if (invokeCounter == 0) {
		Vector allCards = new Vector();
		// elvesszük a jelzéslapot:
		allCards.addElement(jv.getJelzeslap());

		// elvesszük a játékosok lapjait:
		Vector v = null;
		java.util.Enumeration e = null;
		int i = jv.indexOfActivePlayer();
		do {
			v = jv.getPlayer(i).captureAllCards();
			e = v.elements();
			while (e.hasMoreElements()) {
				allCards.addElement(e.nextElement());
			}
			i = jv.getNextPlayerIdx(i);
		} while (i != jv.indexOfActivePlayer());

		// kirakunk egy jelzéslapot:
		jv.setJelzeslap(PlayerData.captureRandomCard(allCards));

		// kiosztjuk a kártyákat:
		i = jv.indexOfActivePlayer();
		PlayerData pd = jv.getActivePlayer();
		do {
			v = new Vector();
			for (int j = 0; j < pd.getProperties().cardCount; ++j) {
				v.addElement(PlayerData.captureRandomCard(allCards));
			}
			pd.addCards(v);
			i = jv.getNextPlayerIdx(i);
			pd = jv.getPlayer(i);
		} while (i != jv.indexOfActivePlayer());
		jv.sendSysChatMsg(szr.shared.SubstituteStr.doIt(resChatTexts.getString("%p_megkeverte_a_játékosok_"), "%p", jv.getActivePlayer().getProperties().name));//$NON-NLS-2$ //$NON-NLS-1$
	} else {
		logInvalidInvokeCounter(invokeCounter);
	}
	return true;
}
public int getID() {
	return 22;
}
public boolean hasAImg() {
	return false;
}
public boolean hasLImg() {
	return false;
}
public String toString() {
	return resCardTexts.getString("Keverõ"); //$NON-NLS-1$
}
}
