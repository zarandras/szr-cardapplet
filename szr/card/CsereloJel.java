package szr.card;

import java.util.Vector;
import szr.mainserv.*;
import szr.shared.*;
/**
 * Cserélõ jelzésosztály
 * Creation date: (2002.03.06. 21:53:29)
 */
public class CsereloJel extends AzHatJel {
	private static java.util.ResourceBundle resChatTexts = java.util.ResourceBundle.getBundle("szr.shared.ChatTexts");  //$NON-NLS-1$
	private static java.util.ResourceBundle resCardTexts = java.util.ResourceBundle.getBundle("szr.shared.CardTexts");  //$NON-NLS-1$
	
public boolean doLPostEvent(szr.mainserv.GameController jv, int invokeCounter) {
	if (invokeCounter == 0) {
		// kicseréljük a kért irányban lévõ és az aktuális játékos lapjait:
		Irany dir = jv.getKertIrany();
		if (dir == null) {
			dir = Irany.Lefele;
		}
		PlayerData pd1 = jv.getActivePlayer();
		PlayerData pd2 = jv.getNextPlayer(dir);
		Vector v = pd1.captureAllCards();
		pd1.addCards(pd2.captureAllCards());
		pd2.addCards(v);
		jv.updateAllCardCount();
		jv.sendSysChatMsg(szr.shared.SubstituteStr.doIt(szr.shared.SubstituteStr.doIt(resChatTexts.getString("%p1_és_%p2_kicserélték_lap"), "%p2", pd2.getProperties().name), "%p1", pd1.getProperties().name));//$NON-NLS-3$//$NON-NLS-2$ //$NON-NLS-1$
		if (jv.getIrany().megegyezik(dir)) {
			jv.setIrany(dir.visszafele());
		}
	} else {
		logInvalidInvokeCounter(invokeCounter);
	}
	return true;
}
public boolean doLPreEvent(szr.mainserv.GameController jv, int invokeCounter) {
	if (invokeCounter == 0) {
		if (jv.getPlayerCount() > 2) {
			jv.forceIranyKeres();
			return false;
		} else
			return true;
	} else {
		if (invokeCounter > 1)
			logInvalidInvokeCounter(invokeCounter);
		return true;
	}
}
public boolean doPostEvent(szr.mainserv.GameController jv, int invokeCounter) {
	if (invokeCounter == 0) {
		boolean nincsKeres = doLPreEvent(jv, 0);
		if (nincsKeres)
			return doLPostEvent(jv, invokeCounter);
		else
			return false;
	} else
		return doLPostEvent(jv, invokeCounter - 1);
}
public int getID() {
	return 23;
}
public boolean hasAImg() {
	return true;
}
public boolean hasLImg() {
	return false;
}
public String toString() {
	return resCardTexts.getString("Cserélõ"); //$NON-NLS-1$
}
}
